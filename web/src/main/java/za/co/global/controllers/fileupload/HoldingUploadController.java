package za.co.global.controllers.fileupload;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.FileDetails;
import za.co.global.domain.fileupload.client.fpm.Holding;
import za.co.global.domain.fileupload.client.fpm.HoldingCategory;
import za.co.global.domain.fileupload.client.fpm.Instrument;
import za.co.global.domain.product.Product;
import za.co.global.persistence.client.ClientRepository;
import za.co.global.persistence.fileupload.FileDetailsRepository;
import za.co.global.persistence.product.ProductRepository;
import za.co.global.services.helper.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class HoldingUploadController  {

    private final static String FILE_TYPE = "FPM";

    @Value("${file.upload.folder}")
    protected String fileUploadFolder;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileDetailsRepository fileDetailsRepository;

    private static String[] categories = {"16001-CASH", "16001-A-UNITCLASS", "16001-D-UNITCLASS", "16001-B-UNITCLASS", "16001-PRICETRADED",
            "16001-YIELDTRADED", "16001-E-UNITCLASS", "16001-FEE"};

    private static String[] categoriesTotal = {"16001-CASH TOTAL", "16001-A-UNITCLASS TOTAL", "16001-D-UNITCLASS TOTAL",
            "16001-B-UNITCLASS TOTAL", "16001-PRICETRADED TOTAL", "16001-YIELDTRADED TOTAL", "16001-E-UNITCLASS TOTAL", "16001-FEE TOTAL"};
    private static List<String> categoryList = Arrays.asList(categories);
    private static List<String> categoryTotalList = Arrays.asList(categoriesTotal);

    private static String[] holdingBaseValues = {"Portfolio Code:", "Portfolio Name:", "Currency:"};
    private static List<String> holdingBaseValuesList = Arrays.asList(holdingBaseValues);

    private List<Client> clients;
    private List<Product> products;

    @GetMapping("/holding_upload")
    public ModelAndView showUpload() {
        clients = clientRepository.findAll();
        products = productRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("upload/uploadFile");
        modelAndView.addObject("clients", clients);
        modelAndView.addObject("products", products);
        return modelAndView;
    }


    @PostMapping("/holding_upload")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file, String productId, String clientId) {
        if (file.isEmpty()) {
            return new ModelAndView("upload/status", "message", "Please select a file and try again");
        }

        try {
            // read and write the file to the selected location-
            byte[] bytes = file.getBytes();

            Client client = clientRepository.findOne(Long.parseLong(clientId));
            Product product = productRepository.findOne(Long.parseLong(productId));

            //store the uploaded file in specified directory and also persist in database
            File uploadedFile = storeFileDetails(file, client, product, bytes);


            FileDetails fileDetails = saveFileDetails(client, product, null, uploadedFile);

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if ("xls".equals(extension) || "xlsx".equals(extension)) {
                readAndStoreFundManagerHoldingSheetData(uploadedFile);
            } else if ("zip".equals(extension)) {
                String unzipFolderName = uploadedFile.getParent() + File.separator + FilenameUtils.removeExtension(uploadedFile.getName());
                List<File> extractedFiles = FileUtil.unZipIt(uploadedFile, unzipFolderName);
                for (File extractedFile : extractedFiles) {
                    extension = FilenameUtils.getExtension(extractedFile.getName());
                    if ("xls".equals(extension) || "xlsx".equals(extension)) {
                        saveFileDetails(client, product, fileDetails, extractedFile);
                        readAndStoreFundManagerHoldingSheetData(extractedFile);
                    }
                }
            } else {
                System.out.println("some other format");
            }

        } catch (IOException e) {
            return new ModelAndView("upload/uploadFile", "message", e.getMessage());
        } catch (InvalidFormatException e) {
            e.printStackTrace();

            return new ModelAndView("upload/uploadFile", "message", e.getMessage());
        } catch (Exception e) {
            return new ModelAndView("upload/uploadFile", "message", e.getMessage());
        }


        return new ModelAndView("upload/status", "message", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

//    public static void main(String[] args) {
//        File file = new File("C:\\Users\\SHARANYAREDDY\\Desktop\\TSR\\GIRSA\\2.FPM\\FPM.xlsx");
//        try {
//            readAndStoreFundManagerHoldingSheetData(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidFormatException e) {
//            e.printStackTrace();
//        }
//    }

    private void readAndStoreFundManagerHoldingSheetData(File uploadedFile) throws IOException, InvalidFormatException {
        FileInputStream fis = new FileInputStream(uploadedFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        DataFormatter dataFormatter = new DataFormatter();
        Holding holding = new Holding();
        Map<String, Integer> headersMap = new HashMap<>();

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            XSSFSheet sheet = workbook.getSheetAt(i);
            if ("Holdings".equals(sheet.getSheetName())) {
                Iterator<Row> rowIterator = sheet.rowIterator();

                while (rowIterator.hasNext()) {
                    XSSFRow row = (XSSFRow) rowIterator.next();
                    //If it is empty row skip it
                    if (checkIfRowIsEmpty(row)) {
                        continue;
                    }

                    //Get first row
                    String firstCellValue = dataFormatter.formatCellValue(row.getCell(0));
                    firstCellValue = !StringUtils.isEmpty(firstCellValue) ? firstCellValue.trim() : firstCellValue;

                     /*
                       * This used to store portfolio data
                     */
                    if (holding.getPortfolioCode() == null || holding.getPortfolioName() == null || holding.getCurrency() == null) {
                        if (!StringUtils.isEmpty(firstCellValue) && holdingBaseValuesList.contains(firstCellValue)) {
                            String secondCellValue = dataFormatter.formatCellValue(row.getCell(1));
                            switch (firstCellValue) {
                                case "Portfolio Code:":
                                    holding.setPortfolioCode(secondCellValue);
                                    break;
                                case "Portfolio Name:":
                                    holding.setPortfolioName(secondCellValue);
                                    break;
                                case "Currency:":
                                    holding.setCurrency(secondCellValue);
                                    break;
                            }
                            continue;
                        }
                    }

                    /*
                    This will store all actual instrument data
                     */
                    if (categoryList.contains(firstCellValue) && headersMap.size() > 0) {
                        HoldingCategory holdingCategory = getInstrumentData(headersMap, firstCellValue, rowIterator);
                        holding.getHoldingCategories().add(holdingCategory);
                    }
                    /*
                    This will store last row of file which has net values
                     */
                    else if (headersMap.size() > 0 && "TOTAL NET ASSETS".equals(firstCellValue)) {
                        Integer index = headersMap.get("Book Value Base Current");
                        holding.setNetBaseCurrentBookValue(getCellValue(row, index));

                        index = headersMap.get("Prior Market Value (Base)");
                        holding.setNetBasePriorMarketValue(getCellValue(row, index));

                        index = headersMap.get("Current Market Value (Base)");
                        holding.setNetBaseCurrentMarketValue(getCellValue(row, index));

                        index = headersMap.get("% of Market Value");
                        holding.setNetPercentOfMarketValue(getCellValue(row, index));

                        break;
                    }
                    /*
                    This will read header data and put it in the map
                     */
                    else if (headersMap.size() == 0) {
                        //Header data
                        Iterator<Cell> cellIterator = row.cellIterator();
                        while (cellIterator.hasNext()) {
                            XSSFCell cell = (XSSFCell) cellIterator.next();
                            String cellValue = dataFormatter.formatCellValue(cell);
                            headersMap.put(cellValue, cell.getColumnIndex());
                        }
                    } //else- if header data reading
                } //while - row iterator
            } //if -Holding sheet checking
        } //for - Sheet iterator
    }

    private boolean checkIfRowIsEmpty(XSSFRow row) {
        if (row == null || row.getLastCellNum() <= 0) {
            return true;
        }
        XSSFCell cell = row.getCell((int)row.getFirstCellNum());
        if (cell == null || "".equals(cell.getRichStringCellValue().getString())) {
            return true;
        }
        return false;
    }

    private File storeFileDetails(MultipartFile file, Client client, Product product, byte[] bytes) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        String folderWithDate = dateFormat.format(new Date());



        String folderName = String.format("%s%s%s%s%s%s%s", fileUploadFolder, File.separator, FILE_TYPE, File.separator,
                folderWithDate, File.separator, client.getClientName(), File.separator, product.getProductName());

        String filename = folderName + File.separator + file.getOriginalFilename();

        File uploadedFile = FileUtil.createFile(filename, bytes);

        return uploadedFile;
    }

    protected FileDetails saveFileDetails(Client client, Product product, FileDetails parent, File file) {
        FileDetails subFileDetails = new FileDetails();
        subFileDetails.setClient(client);
        subFileDetails.setProduct(product);
        subFileDetails.setFilePath(file.getAbsolutePath());
        subFileDetails.setFileExtension(FilenameUtils.getExtension(file.getName()));
        subFileDetails.setReceivedDate(new Date());
        subFileDetails.setParentFileDetails(parent);
        return fileDetailsRepository.save(subFileDetails);
    }

    private HoldingCategory getInstrumentData(Map<String, Integer> headersMap, String category, Iterator<Row> rowIterator) {
        HoldingCategory holdingCategory = new HoldingCategory();
        DataFormatter dataFormatter = new DataFormatter();
        holdingCategory.setCategory(category);

        while (rowIterator.hasNext()) {
            XSSFRow row = (XSSFRow)rowIterator.next();
            String value= dataFormatter.formatCellValue(row.getCell(0)).trim();
            //If the row first column having the 'category + Total' string then the category data will be ended
            if ((category + " TOTAL").equals(value)) {

                Integer index = headersMap.get("Book Value Base Current");
                holdingCategory.setTotalBaseCurrentBookValue(getCellValue(row, index));

                index = headersMap.get("Prior Market Value (Base)");
                holdingCategory.setTotalBasePriorMarketValue(getCellValue(row, index));

                index = headersMap.get("Current Market Value (Base)");
                holdingCategory.setTotalBaseCurrentMarketValue(getCellValue(row, index));

                index = headersMap.get("% of Market Value");
                holdingCategory.setTotalPercentOfMarketValue(getCellValue(row, index));

                break;
            } else {
                Instrument instrument = getInstrumentDetail(headersMap, row);
                if(instrument != null ) {
                    holdingCategory.getInstruments().add(instrument);
                }
            }
        }
        return holdingCategory;
    }

    private static BigDecimal getCellValue(Row row, Integer index) {
        BigDecimal dataValue = BigDecimal.ZERO;
        if(index != null && row.getCell(index) != null && row.getCell(index).getCellType() == 0) {
            dataValue = new BigDecimal(row.getCell(index).getNumericCellValue());
        }
        return dataValue;
    }

    private static Instrument getInstrumentDetail(Map<String, Integer> headersMap, Row row) {
        if(row.getPhysicalNumberOfCells() < headersMap.size()) {
            return null;
        }
        Instrument instrument = new Instrument();

        DataFormatter dataFormatter = new DataFormatter();

        if(categoryList.contains(dataFormatter.formatCellValue(row.getCell(0))) ||
                categoryTotalList.contains(dataFormatter.formatCellValue(row.getCell(0)))) {
            return null;
        }

        for(Map.Entry<String, Integer> headersMapEntry : headersMap.entrySet()) {

            String header = headersMapEntry.getKey().trim();
            if (header.contains("Nominal")) {
                header = "Nominal";
            }
            if (header.contains("Base Price")) {
                header = "Base Price";
            }
            if (header.contains("Holdings Price")) {
                header = "Holdings Price";
            }

            Integer index = headersMapEntry.getValue();
            if (header != null) {
                switch (header) {
                    case "Instrument Code":
                        instrument.setInstrumentCode(dataFormatter.formatCellValue(row.getCell(index)));
                        break;
                    case "Instrument Description":
                        instrument.setInstrumentDescription(dataFormatter.formatCellValue(row.getCell(index)));
                        break;
                    case "Issue Currency":
                        instrument.setIssueCurrency(dataFormatter.formatCellValue(row.getCell(index)));
                        break;
                    case "Nominal":
                        BigDecimal value = BigDecimal.ZERO;
                        if(row.getCell(index).getCellType() == 0) {
                            value = new BigDecimal(row.getCell(index).getNumericCellValue());
                        }
                        instrument.setNominalValue(value);
                        break;
                    case "Base Price":
                        value = BigDecimal.ZERO;
                        if(row.getCell(index).getCellType() == 0) {
                            value = new BigDecimal(row.getCell(index).getNumericCellValue());
                        }
                        instrument.setBasePrice(value);
                        break;
                    case "Holdings Price":
                        value = BigDecimal.ZERO;
                        if(row.getCell(index).getCellType() == 0) {
                            value = new BigDecimal(row.getCell(index).getNumericCellValue());
                        }
                        instrument.setHoldingPrice(value);
                        break;
                    case "Price % Change (Base)":
                        value = BigDecimal.ZERO;
                        if(row.getCell(index).getCellType() == 0) {
                            value = new BigDecimal(row.getCell(index).getNumericCellValue());
                        }
                        instrument.setPercentOfChangeBaseChange(value);
                        break;
                    case "Book Value Current":
                        value = BigDecimal.ZERO;
                        if(row.getCell(index).getCellType() == 0) {
                            value = new BigDecimal(row.getCell(index).getNumericCellValue());
                        }
                        instrument.setCurrentBookValue(value);
                        break;
                    case "Book Value Base Current":
                        value = BigDecimal.ZERO;
                        if(row.getCell(index).getCellType() == 0) {
                            value = new BigDecimal(row.getCell(index).getNumericCellValue());
                        }
                        instrument.setBaseCurrentBookValue(value);
                        break;
                    case "Prior Market Value (Base)":
                        value = BigDecimal.ZERO;
                        if(row.getCell(index).getCellType() == 0) {
                            value = new BigDecimal(row.getCell(index).getNumericCellValue());
                        }
                        instrument.setBasePriorMarketValue(value);
                        break;
                    case "Current Market Value (Base)":
                        value = BigDecimal.ZERO;
                        if(row.getCell(index).getCellType() == 0) {
                            value = new BigDecimal(row.getCell(index).getNumericCellValue());
                        }
                        instrument.setBaseCurrentMarketValue(value);
                        break;
                    case "Market Value (Base Change)":
                        value = BigDecimal.ZERO;
                        if(row.getCell(index).getCellType() == 0) {
                            value = new BigDecimal(row.getCell(index).getNumericCellValue());
                        }
                        instrument.setBaseChangeMarketValue(value);
                        break;
                    case "Market Value % Change":
                        value = BigDecimal.ZERO;
                        if(row.getCell(index).getCellType() == 0) {
                            value = new BigDecimal(row.getCell(index).getNumericCellValue());
                        }
                        instrument.setPercentOfChangeMarketValue(value);
                        break;
                    case "% of Market Value":
                        value = BigDecimal.ZERO;
                        if(row.getCell(index).getCellType() == 0) {
                            value = new BigDecimal(row.getCell(index).getNumericCellValue());
                        }
                        instrument.setPercentOfMarketValue(value);
                        break;
                }
            }
        }
        return instrument;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
