package za.co.global.controllers.report;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import za.co.global.domain.fileupload.client.SecurityListing;
import za.co.global.domain.fileupload.client.fpm.Holding;
import za.co.global.domain.fileupload.client.fpm.HoldingCategory;
import za.co.global.domain.fileupload.client.fpm.Instrument;
import za.co.global.domain.fileupload.mapping.*;
import za.co.global.domain.fileupload.system.BarraAssetInfo;
import za.co.global.domain.report.HoldingValidationBean;
import za.co.global.domain.report.QStatsBean;
import za.co.global.domain.report.ReportData;
import za.co.global.domain.report.ReportStatus;
import za.co.global.persistence.client.ClientRepository;
import za.co.global.persistence.fileupload.HoldingRepository;
import za.co.global.persistence.fileupload.client.InstitutionalDetailsRepository;
import za.co.global.persistence.fileupload.client.NumberOfAccountsRepository;
import za.co.global.persistence.fileupload.client.SecurityListingRepository;
import za.co.global.persistence.fileupload.mapping.*;
import za.co.global.persistence.fileupload.system.BarraAssetInfoRepository;
import za.co.global.persistence.report.ReportDataRepository;
import za.co.global.services.Validator;
import za.co.global.services.report.ReportCreationService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class GenerateQstats {

    public static final String VIEW_FILE = "report/asisaQueueStats";
    @Value("${file.upload.folder}")
    protected String fileUploadFolder;



    @Autowired
    private HoldingRepository holdingRepository;

    @Autowired
    private PSGFundMappingRepository psgFundMappingRepository;

    @Autowired
    private InstitutionalDetailsRepository institutionalDetailsRepository;

    @Autowired
    private NumberOfAccountsRepository numberOfAccountsRepository;

    @Autowired
    private InstrumentCodeRepository instrumentCodeRepository;

    @Autowired
    private BarraAssetInfoRepository barraAssetInfoRepository;

    @Autowired
    private IssuerMappingsRepository issuerMappingsRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ReportDataRepository reportDataRepository;

    @Autowired
    private SecurityListingRepository securityListingRepository;

    @Autowired
    private Validator<HoldingValidationBean> validator;

    @Autowired
    private ReportCreationService reportCreationService;

    @Autowired
    private Reg28InstrumentTypeRepository reg28InstrumentTypeRepository;

    @Autowired
    private DerivativeTypesRepository derivativeTypesRepository;

    @Autowired
    private IndicesRepository indicesRepository;

    private Map<String, Date> maturityDateMap;

    private static final Logger LOGGER = LoggerFactory.getLogger("GenerateQstats");

    @GetMapping("/generate_qstats")
    public ModelAndView displayScreen() {
        List<Client> clients = clientRepository.findAll();
        ModelAndView modelAndView = new ModelAndView(VIEW_FILE);
        modelAndView.addObject("clients", clients);
        return modelAndView;
    }

    @PostMapping("/generate_qstats")
    public ModelAndView generateReport(@RequestParam("reportDate") Date reportDate, String clientId) {
        ModelAndView modelAndView = new ModelAndView(VIEW_FILE);

        Client client = clientRepository.findOne(Long.parseLong(clientId));
        ReportData reportData = reportDataRepository.findByReportStatusAndClient(ReportStatus.REGISTERED, client);
        List<Holding> holdings;
        if(reportData != null) {
            holdings = holdingRepository.findByClientAndReportDataIsNullOrReportData(client, reportData);
        } else {
            reportData = new ReportData();
            reportData.setCreatedDate(new Date());
            reportData.setClient(client);
            holdings = holdingRepository.findByClientAndReportDataIsNull(client);
        }

        List<QStatsBean> qStatsBeans = new ArrayList<>();
//        BarraAssetInfo netAsset = assetInfoRepository.findByAssetId("897"); //TODO - verify
        BarraAssetInfo netAsset = barraAssetInfoRepository.findByNetIndicatorIsTrue(); //TODO - verify
        for(Holding holding: holdings) {
            PSGFundMapping psgFundMapping = psgFundMappingRepository.findByManagerFundCode(holding.getPortfolioCode());
            NumberOfAccounts numberofAccounts = numberOfAccountsRepository.findByFundCode(psgFundMapping.getPsgFundCode());
            InstitutionalDetails institutionalDetails = institutionalDetailsRepository.findByFundCode(psgFundMapping.getPsgFundCode());
            for(HoldingCategory holdingCategory : holding.getHoldingCategories()) {
                for(Instrument instrument : holdingCategory.getInstruments()) {
                    InstrumentCode instrumentCode = instrumentCodeRepository.findByManagerCode(instrument.getInstrumentCode());

                    BarraAssetInfo barraAssetInfo = null;
                    if(instrumentCode != null && instrumentCode.getBarraCode() != null) {
                        barraAssetInfo = barraAssetInfoRepository.findByAssetId(instrumentCode.getBarraCode());
                    }

                    String reg28InstrType = barraAssetInfo.getReg28InstrType();
                    Reg28InstrumentType reg28InstrumentType = reg28InstrumentTypeRepository.findByReg28InstrType(reg28InstrType);

                    String issuerCode = Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getGirIssuer).orElse(null);
                    IssuerMapping issuerMapping = issuerMappingsRepository.findByBarraCode(issuerCode); //TODO - verify from which field of issuer mapping


                    Date maturityDate = getMaturityDate(barraAssetInfo);

                    HoldingValidationBean holdingValidationBean = new HoldingValidationBean.Builder()
                            .setHolding(holding)
                            .setBarraAssetInfo(barraAssetInfo)
                            .setInstitutionalDetails(institutionalDetails)
                            .setInstrumentCode(instrumentCode)
                            .setNetAsset(netAsset)
                            .setMaturityDate(maturityDate)
                            .setPsgFundMapping(psgFundMapping)
                            .setNumberOfAccounts(numberofAccounts)
                            .setReg28InstrumentType(reg28InstrumentType)
                            .build();


                    String error = validator.validate(holdingValidationBean);
                    if (error != null)
                        return modelAndView.addObject("saveError", "report.generation.error");


                    QStatsBean qStatsBean = new QStatsBean();
                    qStatsBean.setAciFundCode(psgFundMapping.getPsgFundCode());
                    String fundName = !StringUtils.isEmpty(psgFundMapping.getManagerFundName()) ?
                            psgFundMapping.getManagerFundName() : holding.getPortfolioName();
                    qStatsBean.setFundName(fundName);
                    qStatsBean.setMancoCode(client.getMancoCode());
                    qStatsBean.setCreatedDate(new Date()); //report generated date
                    qStatsBean.setQuarter(reportDate); //Selected report date
                    qStatsBean.setMvTotal(netAsset.getEffExposure()); //Validated this with total current market base value for equality


                    qStatsBean.setInstitutionalTotal(institutionalDetails.getSplit());
                    qStatsBean.setNoOfAccounts(numberofAccounts.getTotal());

                    String aciAssetClass = getACIAssetClass(barraAssetInfo, reg28InstrType, reg28InstrumentType);
                    qStatsBean.setAciAssetclass(aciAssetClass);

                    qStatsBean.setInstrCode(instrument.getInstrumentCode());


                    qStatsBean.setMaturityDate(maturityDate);

                    //  qStatsBean.setWeightedAvgDuration(null); //TODO - do calculation at cell creation and remove this field
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




                    // qStatsBean.setWeightedAvgMaturity(weightedAvgMaturity); //TODO - do calculation at cell creation and remove this field





                    BigDecimal perOfPort = qStatsBean.getMarketValue().divide(qStatsBean.getMvTotal(), 3, BigDecimal.ROUND_FLOOR);
                    qStatsBean.setPerOfPort(perOfPort);


                    //TODO - verify the indices type(sheet name ) based on portofoli code or psg fund code
                    Indices indices = indicesRepository.findBySecurityAndType(instrument.getInstrumentCode(), psgFundMapping.getPsgFundCode());

                    if("DE".equalsIgnoreCase(qStatsBean.getAciAssetclass())) {
                        qStatsBean.setWeighting(indices.getMarketCap()); //TODO - verify the correct field
                    }

                    qStatsBean.setEqtIndexLink(Boolean.FALSE); //TODO - create EqtIndexLink tables

                    qStatsBean.setAfrican(isAfrican(barraAssetInfo));

                    qStatsBean.setMarketCap(getMarketCapitalisation(barraAssetInfo));
                    qStatsBean.setSharesInIssue(getSharesOutStanding(barraAssetInfo));

                    qStatsBean.setAddClassification(getAdditionalClassification(reg28InstrumentType));

                    qStatsBean.setTtmInc(BigDecimal.ZERO); //TODO - after transaction listing table implementation


                    qStatsBean.setIssuerCode(issuerMapping.getIssuerCode());













                    qStatsBean.setInstrRateST("InstraRateST"); //TODO - check
                    qStatsBean.setInstrRateLT("InstraRateLT"); //TODO - check
                    qStatsBean.setIssuerRateST("IssuerRateST"); //TODO - check
                    qStatsBean.setIssuerRateLT("IssuerRateLT"); //TODO - check

                    String girIssuer = Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getGirIssuer).orElse(null);

                    qStatsBean.setRateAgency(girIssuer); //TODO - check
                    qStatsBean.setCompConDeb(Boolean.FALSE);

                   // IssuerMapping issuerMappings = issuerMappingsRepository.findByIssuerCode(issuer);
                    BigDecimal marketCap = Optional.ofNullable(issuerMapping).map(IssuerMapping::getMarketCapitalisation).orElse(BigDecimal.ZERO);
                    BigDecimal capReserves = Optional.ofNullable(issuerMapping).map(IssuerMapping::getCapitalReserves).orElse(BigDecimal.ZERO);
                    qStatsBean.setMarketCap(marketCap);
                    qStatsBean.setPerIssuedCap(BigDecimal.ZERO);
                    qStatsBean.setCapitalReserves(capReserves);
                    qStatsBean.setAsiSADefined1("NA");
                    qStatsBean.setAsiSADefined2("NA");
                    qStatsBean.setAsiSADefined3("NA");
                    qStatsBean.setAsiSADefined4("NA");
                    qStatsBean.setAsiSADefined5("NA");

                    qStatsBeans.add(qStatsBean);


                }
            }

            holding.setUpdatedDate(new Date());
            holding.setReportData(reportData);


        }

        reportData.getHoldings().clear();
        reportData.getHoldings().addAll(holdings);
        reportData.setReportDate(reportDate);

        reportDataRepository.save(reportData);

        try {
            String filePath = fileUploadFolder + File.separator + "result.xlsx";
            reportCreationService.createExcelFile(qStatsBeans, filePath);
            modelAndView.addObject("saveMessage", "Qstats file created successfully, file: "+filePath);
        } catch (GirsaException e) {
            e.printStackTrace();
           modelAndView.addObject("saveError", e.getMessage());
        }
        return modelAndView;
    }

    private BigDecimal getCouponRate(BarraAssetInfo barraAssetInfo) {
        return Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getCoupon).orElse(null);
    }

    private String getAdditionalClassification(Reg28InstrumentType reg28InstrumentType) {
        String additionalClssification;
        if(StringUtils.isEmpty(reg28InstrumentType.getAddClassificationThree())) {
            additionalClssification = reg28InstrumentType.getAddClassificationThree();
        } else if(StringUtils.isEmpty(reg28InstrumentType.getAddClassificationTwo())) {
                additionalClssification = reg28InstrumentType.getAddClassificationTwo();
        } else {
            additionalClssification = reg28InstrumentType.getAddClassificationOne();
        }
        return additionalClssification;
    }

    private BigDecimal getSharesOutStanding(BarraAssetInfo barraAssetInfo) {
        return Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getSharesOutstanding).orElse(null);
    }

    private BigDecimal getMarketCapitalisation(BarraAssetInfo barraAssetInfo) {
        return Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getMarketCapitalization).orElse(null);
    }

    private boolean isAfrican(BarraAssetInfo barraAssetInfo) {
        boolean isAfrican = false;
        if(barraAssetInfo.getAfricaValues() !=null && barraAssetInfo.getAfricaValues().compareTo(BigDecimal.ZERO) != 0) {
            isAfrican = true;
        }
        return isAfrican;
    }

    private String getACIAssetClass(BarraAssetInfo barraAssetInfo, String reg28InstrType, Reg28InstrumentType reg28InstrumentType) {
        String aciAssetClass = null;
        if("SETTLEMENT".equalsIgnoreCase(barraAssetInfo.getAssetName()) &&
                "1.2(a)".equalsIgnoreCase(reg28InstrType)){
            aciAssetClass = "FS";
        } else if("Fund".equalsIgnoreCase(barraAssetInfo.getInstType())
                || "Composite".equalsIgnoreCase(barraAssetInfo.getInstType())) {
           if(reg28InstrumentType != null && "LOCAL".equalsIgnoreCase(reg28InstrumentType.getAsisaDefined2())) {
                aciAssetClass = "LUT";
            } else {
                aciAssetClass = "FUT";
            }
        } else {
            DerivativeType derivativeType = derivativeTypesRepository.findByType(barraAssetInfo.getInstType());
            if(derivativeType != null && "LOCAL".equalsIgnoreCase(reg28InstrumentType.getAsisaDefined2())) {
                aciAssetClass = derivativeType.getLocalClassification();
            } else if(derivativeType != null && "FOREIGN".equalsIgnoreCase(reg28InstrumentType.getAsisaDefined2())) {
                aciAssetClass = derivativeType.getForeignClassification();
            }
            if(StringUtils.isEmpty(aciAssetClass)) {
                aciAssetClass = reg28InstrumentType.getAciAssetClass();
            }
        }
        return aciAssetClass;
    }

    private Date getMaturityDate(BarraAssetInfo barraAssetInfo) {
        Date maturityDate = null;
        if(barraAssetInfo != null) {
            if (maturityDateMap.get(barraAssetInfo.getAssetId()) != null) {
                maturityDate = maturityDateMap.get(barraAssetInfo.getAssetId());
            } else {
                maturityDate = getMaturityDateFromSecurityListing(barraAssetInfo.getMaturityDate(), barraAssetInfo.getAssetId());
                maturityDateMap.put(barraAssetInfo.getAssetId(), maturityDate);
            }
        }
        return maturityDate;
    }

    private Date getMaturityDateFromSecurityListing(Date barraMaturityDate, String instrumentCode) {
        if(barraMaturityDate != null) {
            return barraMaturityDate;
        }
        SecurityListing securityListing = securityListingRepository.findBySecurityCode(instrumentCode);
        String couponPaymentDates = securityListing.getCouponPaymentDates() != null ?
                securityListing.getCouponPaymentDates().replace(" ", "") : null;
        String[] dates = couponPaymentDates.split(",");
        for(String dateInString : dates) {
            Date date = parseDate(dateInString);
            if(date != null && date.after(new Date())) {
                return date;
            }
        }
        if(securityListing.getMaturityDate() != null && securityListing.getMaturityDate().after(new Date())) {
            return securityListing.getMaturityDate();
        }
        return null;
    }




    private Date parseDate(String dateInString) {
        String datePattern = "ddMMMyyyy";
        try {
            DateFormat dateFormat = new SimpleDateFormat(datePattern);
            return dateFormat.parse(dateInString);
        } catch (ParseException e) {
            LOGGER.error("Date parse error to format:{}, value: {}", datePattern, dateInString);
        }
        return null;
    }

    public static void main(String[] args) {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream("C:\\Users\\SHARANYAREDDY\\Desktop\\TSR\\GIRSA\\abc.xlsx")) {
            // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();

            // Create a Sheet
            Sheet sheet = workbook.createSheet("Qstats");

            int rowNumber=0;
            Row headerRow = sheet.createRow(rowNumber);



            Date quarterDate = new Date(118, 0, 1);
            Date sqlPricingRedemptionDate = new Date(118, 2, 1);
          Date sqlResetMaturityDate = new java.sql.Date(118, 3, 1);

            BigDecimal couponRate = new BigDecimal(2.1);
            BigDecimal currentYield = new BigDecimal(1.1);
            BigDecimal effWeight = new BigDecimal(5.3d);

            String settlementCell = CellReference.convertNumToColString(0)+(rowNumber+1);
            String maturityCell = CellReference.convertNumToColString(1)+(rowNumber+1);
            String couponCell = CellReference.convertNumToColString(2)+(rowNumber+1);
            String yieldCell = CellReference.convertNumToColString(3)+(rowNumber+1);
            String formula=String.format("MDURATION(%s,%s,%s,%s,2)", settlementCell, maturityCell, couponCell, yieldCell);
            //String formula=String.format("SUM(%s;%s)", couponCell, yieldCell);

            CellStyle percentageStyle = workbook.createCellStyle();
            percentageStyle.setDataFormat(workbook.createDataFormat().getFormat("0.000%"));

            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("yyyy/mm/dd"));


            Cell cell1 = headerRow.createCell(0);
            cell1.setCellValue(quarterDate);
            cell1.setCellStyle(dateCellStyle);

            Cell cell2 = headerRow.createCell(1);
            cell2.setCellValue(sqlPricingRedemptionDate);
            cell2.setCellStyle(dateCellStyle);

            Cell cell3 = headerRow.createCell(2);
            cell3.setCellValue(couponRate.toString().replace(".", ","));
            cell3.setCellStyle(percentageStyle);
            cell3.setCellType(Cell.CELL_TYPE_NUMERIC);

            Cell cell4 = headerRow.createCell(3);
            cell4.setCellValue(currentYield.toString().replace(".", ","));
            cell4.setCellStyle(percentageStyle);
            cell3.setCellType(Cell.CELL_TYPE_NUMERIC);

            Cell cell5 = headerRow.createCell(4);
            cell5.setCellFormula(formula);
            cell5.setCellType(Cell.CELL_TYPE_FORMULA);


            workbook.write(fileOut);

        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


