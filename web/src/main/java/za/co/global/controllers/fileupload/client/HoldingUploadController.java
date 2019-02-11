//package za.co.global.controllers.fileupload.client;
//
//import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.DataFormatter;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//import za.co.global.domain.client.Client;
//import za.co.global.domain.fileupload.FileDetails;
//import za.co.global.domain.fileupload.client.notused.Holding;
//import za.co.global.domain.fileupload.client.notused.HoldingCategory;
//import za.co.global.domain.fileupload.client.notused.Instrument;
//import za.co.global.domain.product.Product;
//import za.co.global.domain.report.ReportStatus;
//import za.co.global.persistence.client.ClientRepository;
//import za.co.global.persistence.fileupload.FileDetailsRepository;
//import za.co.global.persistence.fileupload.HoldingRepository;
//import za.co.global.persistence.product.ProductRepository;
//import za.co.global.services.helper.FileUtil;
//
//import javax.transaction.Transactional;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@Controller
//public class HoldingUploadController  {
//
//    private static final String FILE_TYPE = "FPM";
//    public static final String VIEW_FILE = "fileupload/uploadFile";
//    public static final String ATTRIBUTE_SAVE_ERROR = "saveError";
//    public static final String BOOK_VALUE_BASE_CURRENT = "Book Value Base Current";
//    public static final String PRIOR_MARKET_VALUE_BASE = "Prior Market Value (Base)";
//    public static final String CURRENT_MARKET_VALUE_BASE = "Current Market Value (Base)";
//    public static final String PERCENT_OF_MARKET_VALUE = "% of Market Value";
//    public static final String NOMINAL = "Nominal";
//    public static final String BASE_PRICE = "Base Price";
//    public static final String HOLDINGS_PRICE = "Holdings Price";
//
//    @Value("${file.upload.folder}")
//    protected String fileUploadFolder;
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private FileDetailsRepository fileDetailsRepository;
//
//    @Autowired
//    private HoldingRepository holdingRepository;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(HoldingUploadController.class);
//
//    private static String[] categories = {"CASH", "A-UNITCLASS", "D-UNITCLASS", "B-UNITCLASS", "PRICETRADED",
//            "YIELDTRADED", "E-UNITCLASS", "FEE"};
//
//    private static String[] categoriesTotal = {"CASH TOTAL", "A-UNITCLASS TOTAL", "D-UNITCLASS TOTAL",
//            "B-UNITCLASS TOTAL", "PRICETRADED TOTAL", "YIELDTRADED TOTAL", "E-UNITCLASS TOTAL", "FEE TOTAL"};
//
//    private static String[] holdingBaseValues = {"Portfolio Code:", "Portfolio Name:", "Currency:"};
//    private static List<String> holdingBaseValuesList = Arrays.asList(holdingBaseValues);
//
//    private List<Client> clients;
//    private List<Product> products;
//
//    @GetMapping("/holding_upload")
//    public ModelAndView showUpload() {
//        clients = clientRepository.findAll();
//        products = productRepository.findAll();
//        ModelAndView modelAndView = new ModelAndView(VIEW_FILE);
//        modelAndView.addObject("clients", clients);
//        modelAndView.addObject("products", products);
//        return modelAndView;
//    }
//
//
//    @PostMapping("/holding_upload")
//    @Transactional
//    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file, String productId, String clientId) {
//        if (file.isEmpty()) {
//            return new ModelAndView(VIEW_FILE, ATTRIBUTE_SAVE_ERROR, "Please select a file and try again");
//        }
//
//        try {
//            // read and write the file to the selected location-
//            byte[] bytes = file.getBytes();
//
//            Client client = clientRepository.findOne(Long.parseLong(clientId));
//            Product product = productRepository.findOne(Long.parseLong(productId));
//
//            //store the uploaded file in specified directory and also persist in database
//            File uploadedFile = storeFileDetails(file, client, product, bytes);
//
//
//            FileDetails fileDetails = saveFileDetails(client, product, null, uploadedFile);
//
//            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
//            if ("xls".equals(extension) || "xlsx".equals(extension)) {
//                Holding holding = readAndStoreFundManagerHoldingSheetData(uploadedFile);
//                saveHolding(client, holding);
//            } else if ("zip".equals(extension)) {
//                String unzipFolderName = uploadedFile.getParent() + File.separator + FilenameUtils.removeExtension(uploadedFile.getName());
//                List<File> extractedFiles = FileUtil.unZipIt(uploadedFile, unzipFolderName);
//                for (File extractedFile : extractedFiles) {
//                    extension = FilenameUtils.getExtension(extractedFile.getName());
//                    if ("xls".equals(extension) || "xlsx".equals(extension)) {
//                        saveFileDetails(client, product, fileDetails, extractedFile);
//                        Holding holding = readAndStoreFundManagerHoldingSheetData(extractedFile);
//                        saveHolding(client, holding);
//                    }
//                }
//            } else {
//                return new ModelAndView(VIEW_FILE, ATTRIBUTE_SAVE_ERROR, "Please upload excel files");
//            }
//
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
//            return new ModelAndView(VIEW_FILE, ATTRIBUTE_SAVE_ERROR, e.getMessage());
//        }
//        return new ModelAndView(VIEW_FILE, "saveMessage", "File Uploaded successfully... " + file.getOriginalFilename());
//    }
//
//    private void saveHolding(Client client, Holding holding) {
//        if(holding == null){
//            throw new IllegalStateException("Something wrong with data");
//        }
//        List<Holding> holdings = holdingRepository.findByPortfolioCodeAndClientAndReportDataIsNullOrPortfolioCodeAndClientAndReportData_ReportStatus(holding.getPortfolioCode(), client,
//                holding.getPortfolioCode(), client, ReportStatus.REGISTERED);
//        if(holdings.size() > 1) {
//            throw new IllegalStateException("Found duplicate holdings with same Portfolio:"+holding.getPortfolioCode()+", client:"+client.getClientName());
//        }
//        if(holdings.size() == 1) {
//            Holding holdingToSave = holdings.get(0);
//            holdingToSave.getHoldingCategories().clear();
//            holdingToSave.getHoldingCategories().addAll(holding.getHoldingCategories());
//            holdingToSave.setNetBaseCurrentBookValue(holding.getNetBaseCurrentBookValue());
//            holdingToSave.setNetBaseCurrentMarketValue(holding.getNetBaseCurrentMarketValue());
//            holdingToSave.setNetBasePriorMarketValue(holding.getNetBasePriorMarketValue());
//            holdingToSave.setNetPercentOfMarketValue(holding.getNetPercentOfMarketValue());
//            holdingToSave.setPortfolioCode(holding.getPortfolioCode());
//            holdingToSave.setPortfolioName(holding.getPortfolioName());
//            holdingToSave.setCurrency(holding.getCurrency());
//            holdingToSave.setUpdatedDate(new Date());
//            holdingRepository.save(holdingToSave);
//        } else {
//            holding.setClient(client);
//            holding.setUpdatedDate(new Date());
//            holdingRepository.save(holding);
//        }
//    }
//
////    public static void main(String[] args) {
////        File file = new File("C:\\Users\\SHARANYAREDDY\\Desktop\\TSR\\GIRSA\\2.FPM\\FPM.xlsx");
////        try {
////            readAndStoreFundManagerHoldingSheetData(file);
////        } catch (IOException e) {
////            e.printStackTrace();
////        } catch (InvalidFormatException e) {
////            e.printStackTrace();
////        }
////    }
//
//    private Holding readAndStoreFundManagerHoldingSheetData(File uploadedFile) throws IOException {
//        FileInputStream fis = new FileInputStream(uploadedFile);
//        Holding holding;
//        try (XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
//            DataFormatter dataFormatter = new DataFormatter();
//            holding = new Holding();
//            Map<String, Integer> headersMap = new HashMap<>();
//            List<String> categoryList = new ArrayList<>();
//            List<String> categoryTotalList = new ArrayList<>();
//
//            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
//                XSSFSheet sheet = workbook.getSheetAt(i);
//                if ("Holdings".equals(sheet.getSheetName())) {
//                    Iterator<Row> rowIterator = sheet.rowIterator();
//
//                    while (rowIterator.hasNext()) {
//                        XSSFRow row = (XSSFRow) rowIterator.next();
//                        //If it is empty row skip it
//                        if (checkIfRowIsEmpty(row)) {
//                            continue;
//                        }
//
//                        //Get first row
//                        String firstCellValue = dataFormatter.formatCellValue(row.getCell(0));
//                        firstCellValue = !StringUtils.isEmpty(firstCellValue) ? firstCellValue.trim() : firstCellValue;
//
//                 /*
//                   * This used to store portfolio data
//                 */
//                       // if ((holding.getPortfolioCode() == null || holding.getPortfolioName() == null || holding.getCurrency() == null)) {
//                            if (!StringUtils.isEmpty(firstCellValue) && holdingBaseValuesList.contains(firstCellValue)) {
//                                String secondCellValue = dataFormatter.formatCellValue(row.getCell(1));
//                                switch (firstCellValue) {
//                                    case "Portfolio Code:":
//                                        holding.setPortfolioCode(secondCellValue);
//                                        for (String category : categories) {
//                                            categoryList.add(holding.getPortfolioCode() + "-" + category);
//                                        }
//                                        for (String aCategoriesTotal : categoriesTotal) {
//                                            categoryTotalList.add(holding.getPortfolioCode() + "-" + aCategoriesTotal);
//                                        }
//                                        break;
//                                    case "Portfolio Name:":
//                                        holding.setPortfolioName(secondCellValue);
//                                        break;
//                                    case "Currency:":
//                                        holding.setCurrency(secondCellValue);
//                                        break;
//                                    default:
//                                }
//                                continue;
//                            }
////                        }
//
//                /*
//                This will store all actual instrument data
//                 */
//                        if (categoryList.contains(firstCellValue) && headersMap.size() > 0) {
//                            HoldingCategory holdingCategory = getInstrumentData(headersMap, firstCellValue, rowIterator, categoryList,
//                                    categoryTotalList);
//                            holding.getHoldingCategories().add(holdingCategory);
//                        }
//                /*
//                This will store last row of file which has net values
//                 */
//                        else if (headersMap.size() > 0 && "TOTAL NET ASSETS".equals(firstCellValue)) {
//                            Integer index = headersMap.get(BOOK_VALUE_BASE_CURRENT);
//                            holding.setNetBaseCurrentBookValue(getCellValue(row, index));
//
//                            index = headersMap.get(PRIOR_MARKET_VALUE_BASE);
//                            holding.setNetBasePriorMarketValue(getCellValue(row, index));
//
//                            index = headersMap.get(CURRENT_MARKET_VALUE_BASE);
//                            holding.setNetBaseCurrentMarketValue(getCellValue(row, index));
//
//                            index = headersMap.get(PERCENT_OF_MARKET_VALUE);
//                            holding.setNetPercentOfMarketValue(getCellValue(row, index));
//
//                            break;
//                        }
//                /*
//                This will read header data and put it in the map
//                 */
//                        else if (headersMap.size() == 0) {
//                            //Header data
//                            Iterator<Cell> cellIterator = row.cellIterator();
//                            while (cellIterator.hasNext()) {
//                                XSSFCell cell = (XSSFCell) cellIterator.next();
//                                String cellValue = dataFormatter.formatCellValue(cell);
//                                headersMap.put(cellValue, cell.getColumnIndex());
//                            }
//                        } //else- if header data reading
//                    } //while - row iterator
//                } //if -Holding sheet checking
//            } //for - Sheet iterator
//        }
//        return holding;
//    }
//
//    private boolean checkIfRowIsEmpty(XSSFRow row) {
//        if (row == null || row.getLastCellNum() <= 0) {
//            return true;
//        }
//        XSSFCell cell = row.getCell((int)row.getFirstCellNum());
//        return cell == null || "".equals(cell.getRichStringCellValue().getString());
//    }
//
//    private File storeFileDetails(MultipartFile file, Client client, Product product, byte[] bytes) throws IOException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
//        String folderWithDate = dateFormat.format(new Date());
//
//
//
//        String folderName = String.format("%s%s%s%s%s%s%s%s%s", fileUploadFolder, File.separator, FILE_TYPE, File.separator,
//                folderWithDate, File.separator, client.getClientName(), File.separator, product.getProductName());
//
//        String filename = folderName + File.separator + file.getOriginalFilename();
//
//        return FileUtil.createFile(filename, bytes);
//
//    }
//
//    private FileDetails saveFileDetails(Client client, Product product, FileDetails parent, File file) {
//        FileDetails subFileDetails = new FileDetails();
//        subFileDetails.setClient(client);
//        subFileDetails.setProduct(product);
//        subFileDetails.setFilePath(file.getAbsolutePath());
//        subFileDetails.setFileExtension(FilenameUtils.getExtension(file.getName()));
//        subFileDetails.setReceivedDate(new Date());
//        subFileDetails.setParentFileDetails(parent);
//        return fileDetailsRepository.save(subFileDetails);
//    }
//
//    private HoldingCategory getInstrumentData(Map<String, Integer> headersMap, String category, Iterator<Row> rowIterator, List<String> categoryList,
//                                              List<String> categoryTotalList) {
//        HoldingCategory holdingCategory = new HoldingCategory();
//        DataFormatter dataFormatter = new DataFormatter();
//        holdingCategory.setCategory(category);
//
//        while (rowIterator.hasNext()) {
//            XSSFRow row = (XSSFRow)rowIterator.next();
//            String value= dataFormatter.formatCellValue(row.getCell(0)).trim();
//            //If the row first column having the 'category + Total' string then the category data will be ended
//            if ((category + " TOTAL").equals(value)) {
//
//                Integer index = headersMap.get(BOOK_VALUE_BASE_CURRENT);
//                holdingCategory.setTotalBaseCurrentBookValue(getCellValue(row, index));
//
//                index = headersMap.get(PRIOR_MARKET_VALUE_BASE);
//                holdingCategory.setTotalBasePriorMarketValue(getCellValue(row, index));
//
//                index = headersMap.get(CURRENT_MARKET_VALUE_BASE);
//                holdingCategory.setTotalBaseCurrentMarketValue(getCellValue(row, index));
//
//                index = headersMap.get(PERCENT_OF_MARKET_VALUE);
//                holdingCategory.setTotalPercentOfMarketValue(getCellValue(row, index));
//
//                break;
//            } else {
//                Instrument instrument = getInstrumentDetail(headersMap, row, categoryList, categoryTotalList);
//                if(instrument != null ) {
//                    holdingCategory.getInstruments().add(instrument);
//                }
//            }
//        }
//        return holdingCategory;
//    }
//
//    private static BigDecimal getCellValue(Row row, Integer index) {
//        BigDecimal dataValue = BigDecimal.ZERO;
//        if(index != null && row.getCell(index) != null && row.getCell(index).getCellType() == 0) {
//            dataValue = BigDecimal.valueOf(row.getCell(index).getNumericCellValue()).setScale(3, BigDecimal.ROUND_FLOOR);
//        }
//        return dataValue;
//    }
//
//    private static Instrument getInstrumentDetail(Map<String, Integer> headersMap, Row row, List<String> categoryList,
//                                                  List<String> categoryTotalList) {
//        if(row.getPhysicalNumberOfCells() < headersMap.size()) {
//            return null;
//        }
//        Instrument instrument = new Instrument();
//
//        DataFormatter dataFormatter = new DataFormatter();
//
//        if(categoryList.contains(dataFormatter.formatCellValue(row.getCell(0))) ||
//                categoryTotalList.contains(dataFormatter.formatCellValue(row.getCell(0)))) {
//            return null;
//        }
//
//        for(Map.Entry<String, Integer> headersMapEntry : headersMap.entrySet()) {
//
//            String header = headersMapEntry.getKey().trim();
//            if (header.contains(NOMINAL)) {
//                header = NOMINAL;
//            }
//            if (header.contains(BASE_PRICE)) {
//                header = BASE_PRICE;
//            }
//            if (header.contains(HOLDINGS_PRICE)) {
//                header = HOLDINGS_PRICE;
//            }
//
//            Integer index = headersMapEntry.getValue();
//            if (header != null) {
//                switch (header) {
//                    case "Instrument Code":
//                        instrument.setInstrumentCode(dataFormatter.formatCellValue(row.getCell(index)));
//                        break;
//                    case "Instrument Description":
//                        instrument.setInstrumentDescription(dataFormatter.formatCellValue(row.getCell(index)));
//                        break;
//                    case "Issue Currency":
//                        instrument.setIssueCurrency(dataFormatter.formatCellValue(row.getCell(index)));
//                        break;
//                    case NOMINAL:
//                        BigDecimal value = BigDecimal.ZERO;
//                        if(row.getCell(index).getCellType() == 0) {
//                            value = BigDecimal.valueOf(row.getCell(index).getNumericCellValue());
//                        }
//                        instrument.setNominalValue(value);
//                        break;
//                    case BASE_PRICE:
//                        value = BigDecimal.ZERO;
//                        if(row.getCell(index).getCellType() == 0) {
//                            value = BigDecimal.valueOf(row.getCell(index).getNumericCellValue());
//                        }
//                        instrument.setBasePrice(value);
//                        break;
//                    case HOLDINGS_PRICE:
//                        value = BigDecimal.ZERO;
//                        if(row.getCell(index).getCellType() == 0) {
//                            value = BigDecimal.valueOf(row.getCell(index).getNumericCellValue());
//                        }
//                        instrument.setHoldingPrice(value);
//                        break;
//                    case "Price % Change (Base)":
//                        value = BigDecimal.ZERO;
//                        if(row.getCell(index).getCellType() == 0) {
//                            value = BigDecimal.valueOf(row.getCell(index).getNumericCellValue());
//                        }
//                        instrument.setPercentOfChangeBaseChange(value);
//                        break;
//                    case "Book Value Current":
//                        value = BigDecimal.ZERO;
//                        if(row.getCell(index).getCellType() == 0) {
//                            value = BigDecimal.valueOf(row.getCell(index).getNumericCellValue());
//                        }
//                        instrument.setCurrentBookValue(value);
//                        break;
//                    case BOOK_VALUE_BASE_CURRENT:
//                        value = BigDecimal.ZERO;
//                        if(row.getCell(index).getCellType() == 0) {
//                            value = BigDecimal.valueOf(row.getCell(index).getNumericCellValue());
//                        }
//                        instrument.setBaseCurrentBookValue(value);
//                        break;
//                    case PRIOR_MARKET_VALUE_BASE:
//                        value = BigDecimal.ZERO;
//                        if(row.getCell(index).getCellType() == 0) {
//                            value = BigDecimal.valueOf(row.getCell(index).getNumericCellValue());
//                        }
//                        instrument.setBasePriorMarketValue(value);
//                        break;
//                    case CURRENT_MARKET_VALUE_BASE:
//                        value = BigDecimal.ZERO;
//                        if(row.getCell(index).getCellType() == 0) {
//                            value = BigDecimal.valueOf(row.getCell(index).getNumericCellValue());
//                        }
//                        instrument.setBaseCurrentMarketValue(value);
//                        break;
//                    case "Market Value (Base Change)":
//                        value = BigDecimal.ZERO;
//                        if(row.getCell(index).getCellType() == 0) {
//                            value = BigDecimal.valueOf(row.getCell(index).getNumericCellValue());
//                        }
//                        instrument.setBaseChangeMarketValue(value);
//                        break;
//                    case "Market Value % Change":
//                        value = BigDecimal.ZERO;
//                        if(row.getCell(index).getCellType() == 0) {
//                            value = BigDecimal.valueOf(row.getCell(index).getNumericCellValue());
//                        }
//                        instrument.setPercentOfChangeMarketValue(value);
//                        break;
//                    case PERCENT_OF_MARKET_VALUE:
//                        value = BigDecimal.ZERO;
//                        if(row.getCell(index).getCellType() == 0) {
//                            value = BigDecimal.valueOf(row.getCell(index).getNumericCellValue());
//                        }
//                        instrument.setPercentOfMarketValue(value);
//                        break;
//                    default:
//                        LOGGER.warn("No value");
//                }
//            }
//        }
//        return instrument;
//    }
//
//    public List<Client> getClients() {
//        return clients;
//    }
//
//    public void setClients(List<Client> clients) {
//        this.clients = clients;
//    }
//
//    public List<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(List<Product> products) {
//        this.products = products;
//    }
//}
