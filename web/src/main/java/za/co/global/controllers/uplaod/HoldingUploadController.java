package za.co.global.controllers.uplaod;

import com.gizbel.excel.enums.ExcelFactoryType;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.FileDetails;
import za.co.global.domain.fileupload.barra.DSU5_GIRREP4;
import za.co.global.domain.fileupload.client.fpm.Holding;
import za.co.global.domain.fileupload.client.fpm.HoldingCategory;
import za.co.global.domain.fileupload.client.fpm.Instrument;
import za.co.global.domain.product.Product;
import za.co.global.persistence.client.ClientRepository;
import za.co.global.persistence.product.ProductRepository;
import za.co.global.persistence.upload.FileDetailsRepository;
import za.co.global.services.helper.FileUtil;
import za.co.global.services.upload.GirsaExcelParser;
import za.co.global.services.upload.SheetAndObjectResolver;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Controller
public class HoldingUploadController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileDetailsRepository fileDetailsRepository;

    private List<Client> clients;
    private List<Product> products;

    @Value("${file.upload.folder}")
    private String fileUploadFolder;



    @PostMapping("/holding_upload")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file, String productId, String clientId) {
        if (file.isEmpty()) {
            return new ModelAndView("upload/status", "message", "Please select a file and try again");
        }

        try {
            // read and write the file to the selected location-
            byte[] bytes = file.getBytes();

            //store the uploaded file in specified directory and also persist in database
            File uploadedFile = storeFileDetails(file, productId, clientId, bytes);

            readAndStoreFundManagerHoldingSheetData(uploadedFile);
        } catch (IOException ioe) {

        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }


        return new ModelAndView("upload/status", "message", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    private void readAndStoreFundManagerHoldingSheetData(File uploadedFile) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(uploadedFile);
        DataFormatter dataFormatter = new DataFormatter();
        Holding holding = new Holding();
        Map<Integer, String> headersMap = new HashMap<>();

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if ("Holdings".equals(sheet.getSheetName())) {
                Iterator<Row> rowIterator = sheet.rowIterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    System.out.println(row.getPhysicalNumberOfCells());
                     /*
                       * This used to store portfolio data
                     */
                    if (row.getPhysicalNumberOfCells() == 2) {
                        String value = dataFormatter.formatCellValue(row.getCell(0)).trim();
                        if ("Portfolio Code:".equals(value)) {
                            holding.setPortfolioCode(dataFormatter.formatCellValue(row.getCell(1)));
                        } else if ("Portfolio Name:".equals(value)) {
                            holding.setPortfolioName(dataFormatter.formatCellValue(row.getCell(1)));
                        } else if ("Currency:".equals(value)) {
                            holding.setPortfolioName(dataFormatter.formatCellValue(row.getCell(1)));
                        }

                    }
                     /*
                        * This is used to read based on categorised
                     */
                    else if (row.getPhysicalNumberOfCells() == 1 && headersMap.size() > 0) {
                        /*
                        * This reads all the rows in between category and category total
                         */
                        HoldingCategory holdingCategory = getInstrumentData(headersMap, sheet, row);
                        holding.getHoldingCategories().add(holdingCategory);

                    }
                    /*
                        * This is used to read header row data and the 'total net assets' row data
                     */
                    else {
                        if(row.getPhysicalNumberOfCells() > 0) {
                            String value = dataFormatter.formatCellValue(row.getCell(0)).trim();

                            /*
                            * This reads last row the sheet for 'TOTAL NET ASSETS'
                             */
                            if ("TOTAL NET ASSETS".equals(value)) {
                                    Iterator<Cell> cellIterator = row.cellIterator();
                                    while (cellIterator.hasNext()) {
                                        Cell cell = cellIterator.next();
                                        String cellValue = dataFormatter.formatCellValue(cell);

                                        String header = headersMap.get(cell.getColumnIndex());
                                        if(header != null && StringUtils.isEmpty(cellValue)) {
                                            switch (header) {
                                                case "Book Value Base Current":
                                                    holding.setNetBaseCurrentBookValue(new BigDecimal(cellValue));
                                                    break;
                                                case "Prior Market Value (Base)":
                                                    holding.setNetBasePriorMarketValue(new BigDecimal(cellValue));
                                                    break;
                                                case "Current Market Value (Base)":
                                                    holding.setNetBaseCurrentMarketValue(new BigDecimal(cellValue));
                                                    break;
                                                case "% of Market Value":
                                                    holding.setNetPercentOfMarketValue(new BigDecimal(cellValue));
                                                    break;
                                            }
                                        }
                                    }

                                    break; //Need to exit from reading the sheet

                            } else {
                                //Header data
                                Iterator<Cell> cellIterator = row.cellIterator();
                                while (cellIterator.hasNext()) {
                                    Cell cell = cellIterator.next();
                                    String cellValue = dataFormatter.formatCellValue(cell);
                                    headersMap.put(cell.getColumnIndex(), cellValue);
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private File storeFileDetails(@RequestParam("file") MultipartFile file, String productId, String clientId, byte[] bytes) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        String folderWithDate = dateFormat.format(new Date());

        Client client = clientRepository.findOne(Long.parseLong(clientId));
        Product product = productRepository.findOne(Long.parseLong(productId));

        String folderName = String.format("%s%s%s%s%s%s%s", fileUploadFolder, File.separator, folderWithDate, File.separator,
                client.getClientName(), File.separator, product.getProductName());

        String filename = folderName + File.separator + file.getOriginalFilename();

        File uploadedFile = FileUtil.createFile(filename, bytes);

        saveFileDetails(client, product, null, uploadedFile);

        return uploadedFile;
    }

    private HoldingCategory getInstrumentData(Map<Integer, String> headersMap, Sheet sheet, Row row) {
        HoldingCategory holdingCategory = new HoldingCategory();
        DataFormatter dataFormatter = new DataFormatter();
        String category = dataFormatter.formatCellValue(row.getCell(0)).trim();
        holdingCategory.setCategory(category);

        Iterator<Row> rowIterator1 = sheet.rowIterator();
        while (rowIterator1.hasNext()) {
            String value1 = dataFormatter.formatCellValue(row.getCell(0)).trim();
            //If the row first column having the categor with Total sring then the category data will be ended
            if ((category + " TOTAL").equals(value1)) {
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String cellValue = dataFormatter.formatCellValue(cell);

                    String header = headersMap.get(cell.getColumnIndex());
                    if(header != null && StringUtils.isEmpty(cellValue)) {
                        switch (header) {
                            case "Book Value Base Current":
                                holdingCategory.setTotalBaseCurrentBookValue(new BigDecimal(cellValue));
                                break;
                            case "Prior Market Value (Base)":
                                holdingCategory.setTotalBasePriorMarketValue(new BigDecimal(cellValue));
                                break;
                            case "Current Market Value (Base)":
                                holdingCategory.setTotalBaseCurrentMarketValue(new BigDecimal(cellValue));
                                break;
                            case "% of Market Value":
                                holdingCategory.setTotalPercentOfMarketValue(new BigDecimal(cellValue));
                                break;
                        }
                    }
                }

                break;
            } else {
                Instrument instrument = getInstrumentDetail(headersMap, row);
                holdingCategory.getInstruments().add(instrument);
            }


        }
        return holdingCategory;
    }

    private Instrument getInstrumentDetail(Map<Integer, String> headersMap, Row row) {
        Instrument instrument = new Instrument();


        DataFormatter dataFormatter = new DataFormatter();

        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String cellValue = dataFormatter.formatCellValue(cell);

            String header = headersMap.get(cell.getColumnIndex());
            if(header.contains("Nominal")) {
                header = "Nominal";
            }
            if(header.contains("Base Price")) {
                header = "Base Price";
            }
            if(header.contains("Holdings Price")) {
                header = "Holdings Price";
            }
            if(header != null) {
                switch (header) {
                    case "Instrument Code":
                        instrument.setInstrumentCode(cellValue);
                        break;
                    case "Instrument Description":
                        instrument.setInstrumentDescription(cellValue);
                        break;
                    case "Issue Currency":
                        instrument.setIssueCurrency(cellValue);
                        break;
                    case "Nominal":
                        instrument.setNominalValue(new BigDecimal(cellValue));
                        break;
                    case "Base Price":
                        instrument.setBasePrice(new BigDecimal(cellValue));
                        break;
                    case "Holdings Price":
                        instrument.setHoldingPrice(new BigDecimal(cellValue));
                        break;
                    case "Price % Change (Base)":
                        instrument.setPercentOfChangeBaseChange(new BigDecimal(cellValue));
                        break;
                    case "Book Value Current":
                        instrument.setCurrentBookValue(new BigDecimal(cellValue));
                        break;
                    case "Book Value Base Current":
                        instrument.setBaseCurrentBookValue(new BigDecimal(cellValue));
                        break;
                    case "Prior Market Value (Base)":
                        instrument.setBasePriorMarketValue(new BigDecimal(cellValue));
                        break;
                    case "Current Market Value (Base)":
                        instrument.setBaseCurrentMarketValue(new BigDecimal(cellValue));
                        break;
                    case "Market Value (Base Change)":
                        instrument.setBaseChangeMarketValue(new BigDecimal(cellValue));
                        break;
                    case "Market Value % Change":
                        instrument.setPercentOfChangeMarketValue(new BigDecimal(cellValue));
                        break;
                    case "% of Market Value":
                        instrument.setPercentOfMarketValue(new BigDecimal(cellValue));
                        break;
                }
            }

        }
        return instrument;
    }

    private FileDetails saveFileDetails(Client client, Product product, FileDetails parent, File file) {
        FileDetails subFileDetails = new FileDetails();
        subFileDetails.setClient(client);
        subFileDetails.setProduct(product);
        subFileDetails.setFilePath(file.getAbsolutePath());
        subFileDetails.setFileExtension(FilenameUtils.getExtension(file.getName()));
        subFileDetails.setReceivedDate(new Date());
        subFileDetails.setParentFileDetails(parent);
        return fileDetailsRepository.save(subFileDetails);
    }

    public List<File> unZipIt(File zipFile, String outputFolder) {

        byte[] buffer = new byte[1024];
        List<File> files = new ArrayList<>();

        try {

            //create output directory is not exists
//            File folder = new File(OUTPUT_FOLDER);
//            if(!folder.exists()){
//                folder.mkdir();
//            }

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);

//                System.out.println("file unzip : "+ newFile.getAbsoluteFile());
                files.add(newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return files;
    }

    private void readFileAndStoreInDB(File file) throws Exception {
        GirsaExcelParser parser = new GirsaExcelParser(ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION);
        Map<String, List<Object>> result = parser.parse(file); //Whatever excel file you want
        Set<Map.Entry<String, List<Object>>> entries = result.entrySet();
        for (Map.Entry<String, List<Object>> map : entries) {
            String key = map.getKey();
            System.out.println("---" + key);
            List<Object> value = map.getValue();
            for (Object obj : value) {
                Class clazz = SheetAndObjectResolver.getClazzFromSheetName(key);
                if (DSU5_GIRREP4.class.getCanonicalName().equals(clazz.getCanonicalName())) {
                    DSU5_GIRREP4 ex = (DSU5_GIRREP4) obj;
                    entityManager.persist(ex);
                }
            }
        }
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
