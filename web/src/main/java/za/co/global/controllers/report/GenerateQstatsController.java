package za.co.global.controllers.report;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.domain.client.Client;
import za.co.global.domain.exception.GirsaException;
import za.co.global.domain.fileupload.client.InstitutionalDetails;
import za.co.global.domain.fileupload.client.NumberOfAccounts;
import za.co.global.domain.fileupload.client.fpm.Holding;
import za.co.global.domain.fileupload.client.fpm.HoldingCategory;
import za.co.global.domain.fileupload.client.fpm.Instrument;
import za.co.global.domain.fileupload.mapping.*;
import za.co.global.domain.fileupload.system.BarraAssetInfo;
import za.co.global.domain.report.QStatsBean;
import za.co.global.domain.report.ReportData;
import za.co.global.domain.report.ReportDataCollectionBean;
import za.co.global.domain.report.ReportStatus;
import za.co.global.persistence.fileupload.mapping.AdditionalClassificationRepository;
import za.co.global.persistence.fileupload.mapping.DerivativeTypesRepository;
import za.co.global.persistence.fileupload.mapping.IndicesRepository;
import za.co.global.persistence.fileupload.mapping.TransactionListingRepository;
import za.co.global.services.report.ReportCreationService;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private TransactionListingRepository transactionListingRepository;

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

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date reportDate = dateFormat.parse(reportDateInString);

            Client client = clientRepository.findOne(Long.parseLong(clientId));
            ReportData reportData = reportDataRepository.findByReportStatusAndClient(ReportStatus.REGISTERED, client);
            List<Holding> holdings = getHoldings(client, reportData);

            if(reportData == null) {
                reportData = new ReportData();
                reportData.setClient(client);
                reportData.setCreatedDate(new Date());
            }
            reportData.setReportDate(reportDate);


            List<QStatsBean> qStatsBeans = new ArrayList<>();
            List<BarraAssetInfo> netAssets = barraAssetInfoRepository.findByNetIndicatorIsTrue();
            BarraAssetInfo netAsset = netAssets.isEmpty() ? null : netAssets.get(0);
            for (Holding holding : holdings) {
                PSGFundMapping psgFundMapping = psgFundMappingRepository.findByManagerFundCode(holding.getPortfolioCode());
                NumberOfAccounts numberofAccounts = numberOfAccountsRepository.findByFundCode(psgFundMapping.getPsgFundCode());
                InstitutionalDetails institutionalDetails = institutionalDetailsRepository.findByClientFundCode(psgFundMapping.getPsgFundCode());
                for (HoldingCategory holdingCategory : holding.getHoldingCategories()) {
                    for (Instrument instrument : holdingCategory.getInstruments()) {

                        ReportDataCollectionBean reportDataCollectionBean = getReportCollectionBean(instrument, institutionalDetails, netAsset, psgFundMapping,
                                numberofAccounts, reportDate);

                        validate(reportDataCollectionBean);

                        qStatsBeans.add(getQStatsBean(reportDate, client, reportDataCollectionBean));
                    }
                }
                if (holding.getReportData() != null) {
                    reportData.getHoldings().add(holding);
                }
                holding.setReportData(reportData);

            }
            reportDataRepository.save(reportData);

            String filePath = createExcelFile(qStatsBeans, client);
            modelAndView.addObject("saveMessage", "Qstats file created successfully, file: " + filePath);
        } catch (GirsaException e) {
            LOGGER.error("Error while generating QStats report", e);
            modelAndView.addObject("saveError", e.getMessage());
        } catch (ParseException pe) {
            LOGGER.error("Error while generating QStats report", pe);
            modelAndView.addObject("saveError", pe.getMessage());
        }
        return modelAndView;
    }

    private void validate(ReportDataCollectionBean reportDataCollectionBean) throws GirsaException {
        String error = validator.validate(reportDataCollectionBean);
        if (error != null)
            throw new GirsaException(reportError);
    }

    private String createExcelFile(List<QStatsBean> qStatsBeans, Client client) throws GirsaException {
        try {
//            String filePath = fileUploadFolder + File.separator + "Reports" + File.separator + client.getClientName() +"result.xlsx";
            String filePath = fileUploadFolder + File.separator + "QStats_" + new Date() + ".xlsx";
            reportCreationService.createExcelFile(qStatsBeans, filePath);

            //TODO - store it in file detials
            return filePath;
        } catch (GirsaException e) {
            LOGGER.error("Error while creating report file", e);
            throw new GirsaException("Error while creating report file", e);
        }
    }

    private QStatsBean getQStatsBean(@RequestParam("reportDate") Date reportDate, Client client, ReportDataCollectionBean reportDataCollectionBean) {
        BarraAssetInfo barraAssetInfo = reportDataCollectionBean.getBarraAssetInfo();
        String reg28InstrType = barraAssetInfo.getReg28InstrType();
        Reg28InstrumentType reg28InstrumentType = reportDataCollectionBean.getReg28InstrumentType();
        IssuerMapping issuerMapping = reportDataCollectionBean.getIssuerMapping();
        Date maturityDate = reportDataCollectionBean.getMaturityDate();
        //TODO - check correct fields to map
        DailyPricing dailyPricing = reportDataCollectionBean.getDailyPricing();
        PSGFundMapping psgFundMapping = reportDataCollectionBean.getPsgFundMapping();
        BarraAssetInfo netAsset = reportDataCollectionBean.getNetAsset();
        Instrument instrument = reportDataCollectionBean.getInstrument();
        Holding holding = instrument.getHoldingCategory().getHolding();
        InstitutionalDetails institutionalDetails = reportDataCollectionBean.getInstitutionalDetails();
        NumberOfAccounts numberOfAccounts = reportDataCollectionBean.getNumberOfAccounts();


        QStatsBean qStatsBean = new QStatsBean();
        qStatsBean.setAciFundCode(psgFundMapping.getPsgFundCode());
        String fundName = !StringUtils.isEmpty(psgFundMapping.getManagerFundName()) ?
                psgFundMapping.getManagerFundName() : holding.getPortfolioName();
        qStatsBean.setFundName(fundName);
        qStatsBean.setMancoCode(client.getMancoCode());
        qStatsBean.setCreatedDate(new Date()); //report generated date
        qStatsBean.setQuarter(reportDate); //Selected report date
        qStatsBean.setMvTotal(netAsset.getEffExposure()); //Validated this with total current market base value for equality


        qStatsBean.setInstitutionalTotal(institutionalDetails.getTotal());
        qStatsBean.setNoOfAccounts(numberOfAccounts.getTotal());

        String aciAssetClass = getACIAssetClass(barraAssetInfo, reg28InstrType, reg28InstrumentType);
        qStatsBean.setAciAssetclass(aciAssetClass);

        qStatsBean.setInstrCode(instrument.getInstrumentCode());


        qStatsBean.setMaturityDate(maturityDate);

        qStatsBean.setModifiedDuration(barraAssetInfo.getModifiedDuration());
        qStatsBean.setEffWeight(barraAssetInfo.getEffWeight());
        qStatsBean.setPricingRedemptionDate(barraAssetInfo.getPricingRedemptionDate());
        qStatsBean.setCurrentYield(barraAssetInfo.getCurrentYield());

        qStatsBean.setCouponRate(getCouponRate(barraAssetInfo));
        qStatsBean.setHolding(instrument.getNominalValue());
        qStatsBean.setBookValue(instrument.getBaseCurrentBookValue());
        qStatsBean.setCurrencyCode(instrument.getIssueCurrency());
        qStatsBean.setSecurityName(instrument.getInstrumentDescription());
        qStatsBean.setMarketValue(instrument.getBaseCurrentMarketValue());

        BigDecimal perOfPort = qStatsBean.getMarketValue().divide(qStatsBean.getMvTotal(), 3, BigDecimal.ROUND_FLOOR);
        qStatsBean.setPerOfPort(perOfPort);


        //TODO - verify the indices type(sheet name ) based on portofoli code or psg fund code
        Indices indices = indicesRepository.findBySecurityAndType(instrument.getInstrumentCode(), psgFundMapping.getPsgFundCode());

        if ("DE".equalsIgnoreCase(qStatsBean.getAciAssetclass()) && indices != null)  {
            qStatsBean.setWeighting(indices.getIndexPercentage());
        }

        qStatsBean.setEqtIndexLink(Boolean.FALSE); //TODO - create EqtIndexLink tables

        qStatsBean.setAfrican(isAfrican(barraAssetInfo));

        qStatsBean.setMarketCap(getMarketCapitalisation(barraAssetInfo));
        qStatsBean.setSharesInIssue(getSharesOutStanding(barraAssetInfo));

        qStatsBean.setAddClassification(getAdditionalClassification(barraAssetInfo, reg28InstrumentType, qStatsBean.getAciAssetclass()));

        //TODO - verify as it is date or days
        Date tradeDate = getTradeDate(holding, instrument);
        qStatsBean.setTtmInc(BigDecimal.valueOf(TimeUnit.DAYS.convert((tradeDate.getTime() - new Date().getTime()), TimeUnit.MILLISECONDS)));


        qStatsBean.setIssuerCode(issuerMapping.getIssuerCode());

        qStatsBean.setResetMaturityDate(barraAssetInfo.getPricingRedemptionDate());

        //TODO - verify as it is date or days
        qStatsBean.setTtmCur(new BigDecimal(TimeUnit.DAYS.convert((reportDate.getTime() - new Date().getTime()), TimeUnit.MILLISECONDS)));

        qStatsBean.setInstrRateST(null);
        qStatsBean.setInstrRateLT(null);


        String rating = null;
        String ratingAgency = null;
        if (dailyPricing != null) {
            if (!StringUtils.isEmpty(dailyPricing.getMoodys())) {
                rating = dailyPricing.getMoodys();
                ratingAgency = "Moody's";
            } else if (!StringUtils.isEmpty(dailyPricing.getFitch())) {
                rating = dailyPricing.getFitch();
                ratingAgency = "Fitch Group";
            } else if (!StringUtils.isEmpty(dailyPricing.getStandardAndPoor())) {
                rating = dailyPricing.getStandardAndPoor();
                ratingAgency = "Standard & Poor's";
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
        BigDecimal capReserves = Optional.ofNullable(issuerMapping).map(IssuerMapping::getCapitalReserves).orElse(null);
        qStatsBean.setMarketCap(marketCap);
        if (qStatsBean.getMarketValue() != null && marketCap != null && marketCap.compareTo(BigDecimal.ZERO) != 0) {
            qStatsBean.setPerIssuedCap(qStatsBean.getMarketValue().divide(marketCap, 3, RoundingMode.HALF_UP));
        }
        qStatsBean.setCapitalReserves(capReserves);
        qStatsBean.setAsiSADefined1(reg28InstrType);
        qStatsBean.setAsiSADefined2(reg28InstrumentType.getAsisaDefined2());
        qStatsBean.setAsiSADefined3(null);
        qStatsBean.setAsiSADefined4(null);
        qStatsBean.setAsiSADefined5(null);
        return qStatsBean;
    }


    private Date getTradeDate(Holding holding, Instrument instrument) {
        List<TransactionListing> transactionListings = transactionListingRepository.findByClientPortfolioCodeAndInstrumentCode(holding.getPortfolioCode(),
                instrument.getInstrumentCode());

        return transactionListings.isEmpty() ? null : transactionListings.get(0).getTradeDate();
    }


    private BigDecimal getCouponRate(BarraAssetInfo barraAssetInfo) {
        return Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getCoupon).orElse(null);
    }

    private String getAdditionalClassification(BarraAssetInfo barraAssetInfo, Reg28InstrumentType reg28InstrumentType, String aciAssetClass) {

        String addClassification = null;
        if("DE".equals(aciAssetClass)) {
            List<AdditionalClassification> additionalClassifications = additionalClassificationRepository.findByIndustryAndSectorAndSuperSectorAndSubSector(barraAssetInfo.getGirIndustryICB(),
                    barraAssetInfo.getGirSectorICB(), barraAssetInfo.getGirSupersectorICB(), barraAssetInfo.getGirSubsectorICB());
            if(!additionalClassifications.isEmpty()) {
                addClassification = additionalClassifications.get(0).getAlphaCode();
            }
        }
        if(!StringUtils.isEmpty(addClassification)) {
            if (StringUtils.isEmpty(reg28InstrumentType.getAddClassificationThree())) {
                addClassification = reg28InstrumentType.getAddClassificationThree();
            } else if (StringUtils.isEmpty(reg28InstrumentType.getAddClassificationTwo())) {
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
        if ("SETTLEMENT".equalsIgnoreCase(barraAssetInfo.getAssetName()) && "1.2(a)".equalsIgnoreCase(reg28InstrType)) {
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


