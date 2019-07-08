package za.co.global.controllers.report;

import liquibase.util.csv.CSVWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.domain.client.Client;
import za.co.global.domain.exception.GirsaException;
import za.co.global.domain.fileupload.client.DailyPricing;
import za.co.global.domain.fileupload.client.Indices;
import za.co.global.domain.fileupload.client.InstrumentData;
import za.co.global.domain.fileupload.mapping.*;
import za.co.global.domain.fileupload.system.BarraAssetInfo;
import za.co.global.domain.report.QStatsBean;
import za.co.global.domain.report.ReportData;
import za.co.global.domain.report.ReportDataCollectionBean;
import za.co.global.domain.report.ReportStatus;
import za.co.global.persistence.fileupload.client.IndicesRepository;
import za.co.global.persistence.fileupload.mapping.AdditionalClassificationRepository;
import za.co.global.persistence.fileupload.mapping.DerivativeTypesRepository;
import za.co.global.services.report.ReportCreationService;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

//import za.co.global.persistence.fileupload.client.TransactionListingRepository;

@Controller
public class GenerateQstatsController extends AbstractQstatsReportController {

    private static final String VIEW_FILE = "report/asisaQueueStats";

    @Value("${file.upload.folder}")
    protected String fileUploadFolder;

    @Value("${report.generation.error}")
    protected String reportError;

    @Autowired
    private ReportCreationService reportCreationService;

    @Autowired
    private DerivativeTypesRepository derivativeTypesRepository;

    @Autowired
    private IndicesRepository indicesRepository;

    //@Autowired
    //private TransactionListingRepository transactionListingRepository;

    @Autowired
    private AdditionalClassificationRepository additionalClassificationRepository;


    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateQstatsController.class);

    @GetMapping("/generate_qstats")
    public ModelAndView displayScreen() {
        List<Client> clients = clientRepository.findAll();
        ModelAndView modelAndView = new ModelAndView(VIEW_FILE);
        modelAndView.addObject("clients", clients);
        return modelAndView;
    }

    @PostMapping("/generate_qstats")
    public ModelAndView generateReport(@RequestParam("reportDate") String reportDateInString, String clientId) {

        ModelAndView modelAndView = new ModelAndView(VIEW_FILE);
        try {

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date reportDate = dateFormat.parse(reportDateInString);

            Client client = clientRepository.findOne(Long.parseLong(clientId));
            List<ReportData> reportDatas = reportDataRepository.findByReportStatusAndClient(ReportStatus.REGISTERED, client);
            ReportData reportData = reportDatas.isEmpty() ? null : reportDatas.get(0);
            List<InstrumentData> instrumentDataList = getInstrumentData(client, reportData);

            if (reportData == null) {
                reportData = new ReportData();
                reportData.setClient(client);
                reportData.setCreatedDate(new Date());
            }
            reportData.setReportDate(reportDate);


            List<QStatsBean> qStatsBeans = new ArrayList<>();

            List<BarraAssetInfo> netAssets = barraAssetInfoRepository.findByNetIndicatorIsTrue();
            //BarraAssetInfo netAsset = netAssets.isEmpty() ? null : netAssets.get(0);
            Map<String, BarraAssetInfo> netAssetMap = new HashMap<>();
            for(BarraAssetInfo barraAssetInfo : netAssets) {
                netAssetMap.put(barraAssetInfo.getFundName(), barraAssetInfo);
            }

            BigDecimal netCurrentMarketValue;
            if (!CollectionUtils.isEmpty(instrumentDataList)) {

                 /*Adding this map to verify the equivalence netEffExposure and netCurrent market
        values for fund level not for every instrument  */

                for (InstrumentData instrumentData : instrumentDataList) {
                    ClientFundMapping clientFundMapping = clientFundMappingRepository.findByManagerFundCode(instrumentData.getPortfolioCode());
                    ReportDataCollectionBean reportDataCollectionBean = getReportCollectionBean(instrumentData, netAssetMap, clientFundMapping, reportDate, null, null);
                    if(reportDataCollectionBean.getBarraAssetInfo() != null) {
                        validate(reportDataCollectionBean);
                        qStatsBeans.add(getQStatsBean(reportDate, client, reportDataCollectionBean));
                        if (instrumentData.getReportData() == null) {
                            reportData.getInstrumentDataList().add(instrumentData);
                            instrumentData.setReportData(reportData);
                        }
                    }
                }
                reportDataRepository.save(reportData);

                String filePath = createExcelFile(qStatsBeans, client, reportDateInString);
                modelAndView.addObject("successMessage", "Qstats file created successfully, file: " + filePath);
            } else {
                modelAndView.addObject("errorMessage", "No instrument data to generate report");
            }
        } catch (Exception e) {
            LOGGER.error("Error generating report file", e);
            modelAndView.addObject("errorMessage", "Error: "+e.getMessage());
        }
        return modelAndView;
    }

    private void validate(ReportDataCollectionBean reportDataCollectionBean) throws GirsaException {
        String error = validator.validate(reportDataCollectionBean);
        if (error != null) {
            LOGGER.error("Error on validation", error);
            throw new GirsaException(reportError);
        }
    }

    private String createExcelFile(List<QStatsBean> qStatsBeans, Client client, String reportDateInString) throws GirsaException {
        try {
            String filename = String.format("QStats_%s_%s", client.getClientName(), reportDateInString);
//            String filePath = fileUploadFolder + File.separator + "Reports" + File.separator + client.getClientName() +"result.xlsx";
            String filePath = fileUploadFolder + File.separator + filename + ".xlsx";
            reportCreationService.createExcelFile(qStatsBeans, filePath);


            String csvFilePath = fileUploadFolder + File.separator + filename + ".csv";
            createCSVFileFromExcel(filePath, csvFilePath);

            //TODO - store it in file detials
            return filePath;
        } catch (GirsaException e) {
            throw new GirsaException("Error while creating report file", e);
        } catch (IOException | InvalidFormatException e) {
            LOGGER.error("Error while converting xlsx to csv file", e);
            throw new GirsaException("Error while creating report file", e);
        }
    }

    public void createCSVFileFromExcel(String excelFilePath, String csvFilePath) throws IOException, InvalidFormatException {
        try (InputStream inputStream = new FileInputStream(new File(excelFilePath));
             FileWriter my_csv = new FileWriter(csvFilePath);
             CSVWriter my_csv_output = new CSVWriter(my_csv)) {
            XSSFWorkbook my_xls_workbook = new XSSFWorkbook(inputStream);
            // Read worksheet into HSSFSheet
            XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
            // To iterate over the rows
            Iterator<Row> rowIterator = my_worksheet.iterator();
            // OpenCSV writer object to create CSV file

            //Loop through rows.
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                int i = 0;//String array
                //change this depending on the length of your sheet
                String[] csvdata = new String[row.getPhysicalNumberOfCells()];
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next(); //Fetch CELL
                    switch (cell.getCellType()) { //Identify CELL type
                        //you need to add more code here based on
                        //your requirement / transformations
                        case Cell.CELL_TYPE_STRING:
                            csvdata[i] = cell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            csvdata[i] = "";
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            csvdata[i] = "" + cell.getNumericCellValue();
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            csvdata[i] = "" + cell.getBooleanCellValue();
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            csvdata[i] = "" + cell.getNumericCellValue();
                            break;
                        default:
                                csvdata[i] = cell.getStringCellValue();
                    }
                    i = i + 1;
                }
                my_csv_output.writeNext(csvdata);
            }

        }
    }

    private QStatsBean getQStatsBean(Date reportDate, Client client, ReportDataCollectionBean reportDataCollectionBean) {
        BarraAssetInfo barraAssetInfo = reportDataCollectionBean.getBarraAssetInfo();
        String reg28InstrType = barraAssetInfo.getReg28InstrType();
        Reg28InstrumentType reg28InstrumentType = reportDataCollectionBean.getReg28InstrumentType();
        IssuerMapping issuerMapping = reportDataCollectionBean.getIssuerMapping();
        Date maturityDate = reportDataCollectionBean.getMaturityDate();
        //TODO - check correct fields to map
        DailyPricing dailyPricing = reportDataCollectionBean.getDailyPricing();
        ClientFundMapping clientFundMapping = reportDataCollectionBean.getClientFundMapping();
        BarraAssetInfo netAsset = reportDataCollectionBean.getNetAsset();
        InstrumentData instrument = reportDataCollectionBean.getInstrumentData();

        QStatsBean qStatsBean = new QStatsBean();
        qStatsBean.setAciFundCode(clientFundMapping.getManagerFundCode()); //TODO - verify clinet fund code or manager fund code
        String fundName = !StringUtils.isEmpty(clientFundMapping.getManagerFundName()) ?
                clientFundMapping.getManagerFundName() : instrument.getPortfolioName();
        qStatsBean.setFundName(fundName);
        qStatsBean.setMancoCode(client.getMancoCode());
        qStatsBean.setCreatedDate(new Date()); //report generated date
        qStatsBean.setQuarter(reportDate); //Selected report date
        qStatsBean.setMvTotal(netAsset.getEffExposure()); //Validated this with total current market base value for equality


        qStatsBean.setInstitutionalTotal(instrument.getInstitutionTotal());
        qStatsBean.setNoOfAccounts(instrument.getNoOfAccounts());

        String aciAssetClass = getACIAssetClass(barraAssetInfo, reg28InstrType, reg28InstrumentType);
        qStatsBean.setAciAssetclass(aciAssetClass);

        qStatsBean.setInstrCode(instrument.getInstrumentCode());


        qStatsBean.setMaturityDate(maturityDate);

        qStatsBean.setModifiedDuration(barraAssetInfo.getModifiedDuration());
        qStatsBean.setEffWeight(barraAssetInfo.getEffWeight());
        qStatsBean.setPricingRedemptionDate(barraAssetInfo.getPricingRedemptionDate());
        qStatsBean.setCurrentYield(barraAssetInfo.getCurrentYield());

        qStatsBean.setCouponRate(getCouponRate(barraAssetInfo));
        qStatsBean.setHolding(instrument.getNominalUnits());
        qStatsBean.setBookValue(instrument.getCurrentBookValue());
        qStatsBean.setCurrencyCode(instrument.getInstrumentCurrency());
        qStatsBean.setSecurityName(instrument.getInstrumentDescription());
        qStatsBean.setMarketValue(instrument.getCurrentMarketValue());

        BigDecimal perOfPort = qStatsBean.getMarketValue() != null ?
                qStatsBean.getMarketValue().divide(qStatsBean.getMvTotal(), 8, BigDecimal.ROUND_HALF_UP) : null;
        qStatsBean.setPerOfPort(perOfPort);

        if ("DE".equalsIgnoreCase(qStatsBean.getAciAssetclass())) {
            String type = StringUtils.isEmpty(clientFundMapping.getComments()) ? StringUtils.EMPTY : clientFundMapping.getComments();
            Indices indices = indicesRepository.findBySecurityAndType(instrument.getInstrumentCode(), type);
            if (indices != null) {
                qStatsBean.setWeighting(indices.getIndexPercentage());
            }
        }

        qStatsBean.setEqtIndexLink(Boolean.FALSE); //TODO - create EqtIndexLink tables

        qStatsBean.setAfrican(isAfrican(barraAssetInfo));

        qStatsBean.setMarketCap(getMarketCapitalisation(barraAssetInfo));
        qStatsBean.setSharesInIssue(getSharesOutStanding(barraAssetInfo));

        qStatsBean.setAddClassification(getAdditionalClassification(barraAssetInfo, reg28InstrumentType, qStatsBean.getAciAssetclass()));

        Date tradeDate = instrument.getTradeDate();
        if(maturityDate != null && tradeDate != null) {
            qStatsBean.setTtmInc(new BigDecimal(TimeUnit.DAYS.convert((maturityDate.getTime() - tradeDate.getTime()), TimeUnit.MILLISECONDS)));
        }


        if(issuerMapping != null) {
            qStatsBean.setIssuerCode(issuerMapping.getIssuerCode());
        }

        qStatsBean.setResetMaturityDate(barraAssetInfo.getPricingRedemptionDate());

        BigDecimal ttmCur = maturityDate != null ? BigDecimal.valueOf(TimeUnit.DAYS.convert((maturityDate.getTime() - reportDate.getTime()), TimeUnit.MILLISECONDS)) :
                null;
        qStatsBean.setTtmCur(ttmCur);

        qStatsBean.setInstrRateST(null);
        qStatsBean.setInstrRateLT(null);


        String rating = null;
        String ratingAgency = null;
        if (dailyPricing != null) {
            if (!StringUtils.isEmpty(dailyPricing.getMoodys())) {
                rating = dailyPricing.getMoodys();
                ratingAgency = "Moody's";
            }  else if (!StringUtils.isEmpty(dailyPricing.getStandardAndPoor())) {
                rating = dailyPricing.getStandardAndPoor();
                ratingAgency = "Standard & Poor's";
            } else if (!StringUtils.isEmpty(dailyPricing.getFitch())) {
                rating = dailyPricing.getFitch();
                ratingAgency = "Fitch Group";
            } else if (!StringUtils.isEmpty(dailyPricing.getGlobal())) {
                rating = dailyPricing.getGlobal();
                ratingAgency = "Global";
            }
        }
        qStatsBean.setIssuerRateST(rating);
        qStatsBean.setIssuerRateLT(rating);


        qStatsBean.setRateAgency(ratingAgency);
        qStatsBean.setCompConDeb(Boolean.FALSE);

        BigDecimal marketCap = Optional.ofNullable(issuerMapping).map(IssuerMapping::getMarketCapitalisation).orElse(null);
        qStatsBean.setMarketCapIssuer(marketCap);

        BigDecimal capReserves = Optional.ofNullable(issuerMapping).map(IssuerMapping::getCapitalReserves).orElse(null);
        qStatsBean.setCapitalReserves(capReserves);

        if (qStatsBean.getMarketValue() != null && marketCap != null && marketCap.compareTo(BigDecimal.ZERO) != 0) {
            qStatsBean.setPerIssuedCap(qStatsBean.getMarketValue().divide(marketCap, 8, RoundingMode.HALF_UP));
        }

        qStatsBean.setAsiSADefined1(reg28InstrType);
        if(reg28InstrumentType != null) {
            qStatsBean.setAsiSADefined2(reg28InstrumentType.getAsisaDefined2());
        }
        qStatsBean.setAsiSADefined3(null);
        qStatsBean.setAsiSADefined4(null);
        qStatsBean.setAsiSADefined5(null);
        return qStatsBean;
    }


    private BigDecimal getCouponRate(BarraAssetInfo barraAssetInfo) {
        return Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getCoupon).orElse(null);
    }

    private String getAdditionalClassification(BarraAssetInfo barraAssetInfo, Reg28InstrumentType reg28InstrumentType, String aciAssetClass) {

        String addClassification = null;
        if ("DE".equals(aciAssetClass)) {
            AdditionalClassification additionalClassifications = additionalClassificationRepository.findByIndustryAndSectorAndSuperSectorAndSubSector(barraAssetInfo.getGirIndustryICB(),
                    barraAssetInfo.getGirSectorICB(), barraAssetInfo.getGirSupersectorICB(), barraAssetInfo.getGirSubsectorICB());
            if (additionalClassifications != null) {
                addClassification = additionalClassifications.getAlphaCode();
            }
        }
        if (StringUtils.isEmpty(addClassification) && reg28InstrumentType != null) {
            if (!StringUtils.isEmpty(reg28InstrumentType.getAddClassificationThree())) {
                addClassification = reg28InstrumentType.getAddClassificationThree();
            } else if (!StringUtils.isEmpty(reg28InstrumentType.getAddClassificationTwo())) {
                addClassification = reg28InstrumentType.getAddClassificationTwo();
            } else {
                addClassification = reg28InstrumentType.getAddClassificationOne();
            }
        }
        return addClassification;
    }

    private BigDecimal getSharesOutStanding(BarraAssetInfo barraAssetInfo) {
        return Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getSharesOutstanding).orElse(null);
    }

    private BigDecimal getMarketCapitalisation(BarraAssetInfo barraAssetInfo) {
        return Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getMarketCapitalization).orElse(null);
    }

    private boolean isAfrican(BarraAssetInfo barraAssetInfo) {
        boolean isAfrican = false;
        if (barraAssetInfo.getAfricaValues() != null && barraAssetInfo.getAfricaValues().compareTo(BigDecimal.ZERO) != 0) {
            isAfrican = true;
        }
        return isAfrican;
    }

    private String getACIAssetClass(BarraAssetInfo barraAssetInfo, String reg28InstrType, Reg28InstrumentType reg28InstrumentType) {
        String aciAssetClass = null;
        if(barraAssetInfo.getAssetName() != null && barraAssetInfo.getAssetName().trim().startsWith("DC_L")) {
            aciAssetClass = "DC";
        } else if(barraAssetInfo.getAssetName() != null && barraAssetInfo.getAssetName().trim().startsWith("DM_L")) {
            aciAssetClass = "DM";
        } else if(barraAssetInfo.getAssetName() != null && barraAssetInfo.getAssetName().trim().startsWith("DO_L")) {
            aciAssetClass = "DO";
        } else if ("SETTLEMENT".equalsIgnoreCase(barraAssetInfo.getAssetName()) && "1.2(a)".equalsIgnoreCase(reg28InstrType)) {
            aciAssetClass = "FS";
        } else if ("Fund".equalsIgnoreCase(barraAssetInfo.getInstType()) || "Composite".equalsIgnoreCase(barraAssetInfo.getInstType())) {
            if (reg28InstrumentType != null && "LOCAL".equalsIgnoreCase(reg28InstrumentType.getAsisaDefined2())) {
                aciAssetClass = "LUT";
            } else {
                aciAssetClass = "FUT";
            }
        } else {
            DerivativeType derivativeType = derivativeTypesRepository.findByType(barraAssetInfo.getInstType());
            if (derivativeType != null && "LOCAL".equalsIgnoreCase(reg28InstrumentType.getAsisaDefined2())) {
                aciAssetClass = derivativeType.getLocalClassification();
            } else if (derivativeType != null && "FOREIGN".equalsIgnoreCase(reg28InstrumentType.getAsisaDefined2())) {
                aciAssetClass = derivativeType.getForeignClassification();
            }
            if (StringUtils.isEmpty(aciAssetClass)) {
                aciAssetClass = reg28InstrumentType.getAciAssetClass();
            }
        }
        return aciAssetClass;
    }


//    public static void main(String[] args) {
//
////        DateFormat dateFormat = new SimpleDateFormat("ddMMM");
////        try {
////
////            System.out.println();
////            System.out.println(dateFormat.parse("28Sep"));
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
//
//        try (Workbook workbook = new XSSFWorkbook();
//             FileOutputStream fileOut = new FileOutputStream("/home/pradeep/tmp/girsa/abc.xlsx")) {
//            // new HSSFWorkbook() for generating `.xls` file
//
//        /* CreationHelper helps us create instances of various things like DataFormat,
//           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
//            CreationHelper createHelper = workbook.getCreationHelper();
//
//            // Create a Sheet
//            Sheet sheet = workbook.createSheet("Qstats");
//
//            int rowNumber=0;
//            Row headerRow = sheet.createRow(rowNumber);
//
//
//
//            Date quarterDate = new Date(118, 0, 1);
//            Date sqlPricingRedemptionDate = new Date(118, 2, 1);
//          Date sqlResetMaturityDate = new java.sql.Date(118, 3, 1);
//
//            BigDecimal couponRate = new BigDecimal(2.1);
//            BigDecimal currentYield = new BigDecimal(1.1);
//            BigDecimal effWeight = new BigDecimal(5.3d);
//
//            String settlementCell = CellReference.convertNumToColString(0)+(rowNumber+1);
//            String maturityCell = CellReference.convertNumToColString(6)+(rowNumber+1);
//            String couponCell = CellReference.convertNumToColString(2)+(rowNumber+1);
//            String yieldCell = CellReference.convertNumToColString(3)+(rowNumber+1);
//           // String formula=String.format("MDURATION(%s,%s,%s,%s,2)", settlementCell, maturityCell, couponCell, yieldCell);
//            //String formula=String.format("SUM(%s;%s)", couponCell, yieldCell);
//
//
//            CellStyle dateCellStyle = workbook.createCellStyle();
//            dateCellStyle.setDataFormat(
//                    createHelper.createDataFormat().getFormat("yyyy/mm/dd"));
//
//
//            Cell cell1 = headerRow.createCell(0);
//            cell1.setCellValue(quarterDate);
//            cell1.setCellStyle(dateCellStyle);
//
//            Cell cell2 = headerRow.createCell(6);
//            cell2.setCellValue(sqlPricingRedemptionDate);
//            cell2.setCellStyle(dateCellStyle);
//
//            Cell cell3 = headerRow.createCell(2);
//            cell3.setCellValue(couponRate.doubleValue());
//            cell3.setCellType(Cell.CELL_TYPE_NUMERIC);
//
//            Cell cell4 = headerRow.createCell(3);
//            cell4.setCellValue(currentYield.doubleValue());
//            cell3.setCellType(Cell.CELL_TYPE_NUMERIC);
//
//            String formula=String.format("MDURATION(%s,%s,"+couponRate.doubleValue()+","+currentYield.doubleValue()+",2)*"+effWeight.doubleValue()+"*"+365.25, settlementCell, maturityCell);
//
//
//            Cell cell5 = headerRow.createCell(4);
//            cell5.setCellFormula(formula);
//            cell5.setCellType(Cell.CELL_TYPE_FORMULA);
//
//
//            workbook.write(fileOut);
//
//        } catch (IOException ie) {
//            ie.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}


