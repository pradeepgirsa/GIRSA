package za.co.global.controllers.report;

import org.apache.commons.lang3.StringUtils;
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
import za.co.global.domain.fileupload.mapping.InstrumentCode;
import za.co.global.domain.fileupload.mapping.IssuerMappings;
import za.co.global.domain.fileupload.mapping.PSGFundMapping;
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
import za.co.global.persistence.fileupload.mapping.InstrumentCodeRepository;
import za.co.global.persistence.fileupload.mapping.IssuerMappingsRepository;
import za.co.global.persistence.fileupload.mapping.PSGFundMappingRepository;
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

    private Map<String, Date> maturityDateMap;

    private static final Logger LOGGER = LoggerFactory.getLogger("GenerateQstats");

    @GetMapping("/generate_qstats")
    public ModelAndView displayScreen() {
        return new ModelAndView("report/asisaQueueStats");
    }

    @PostMapping("/generate_qstats")
    public ModelAndView generateReport(@RequestParam("reportDate") Date reportDate, String clientId) {
        ModelAndView modelAndView = new ModelAndView("report/asisaQueueStats");

        Map<String, Date> maturityDateMap = new HashMap<>();

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
                            .build();


                    String error = validator.validate(holdingValidationBean);
                    if (error != null)
                        return modelAndView.addObject("saveError", "report.generation.error");


                    QStatsBean qStatsBean = new QStatsBean();
                    qStatsBean.setMaturityDate(maturityDateMap.get(barraAssetInfo.getAssetId()));
                    qStatsBean.setAciFundCode(psgFundMapping.getPsgFundCode());
                    String fundName = !StringUtils.isEmpty(psgFundMapping.getManagerFundName()) ?
                            psgFundMapping.getManagerFundName() : holding.getPortfolioName();
                    qStatsBean.setFundName(fundName);
                    qStatsBean.setMancoCode(client.getMancoCode());
                    qStatsBean.setCreatedDate(new Date()); //report generated date
                    qStatsBean.setQuarter(reportDate); //Selected report date
                    qStatsBean.setPricingRedemptionDate(barraAssetInfo.getPricingRedemptionDate());
                    qStatsBean.setMvTotal(netAsset.getEffExposure()); //Validated this with total current market base value for equality
                    qStatsBean.setInstitutionalTotal(institutionalDetails.getSplit());
                    qStatsBean.setNoOfAccounts(numberofAccounts.getTotal());
                  //  qStatsBean.setWeightedAvgDuration(null); //TODO - do calculation at cell creation and remove this field

                    qStatsBean.setModifiedDuration(barraAssetInfo.getModifiedDuration());
                    qStatsBean.setEffWeight(barraAssetInfo.getEffWeight());

                   // qStatsBean.setWeightedAvgMaturity(weightedAvgMaturity); //TODO - do calculation at cell creation and remove this field


                    qStatsBean.setEffWeight(barraAssetInfo.getEffWeight());
                    qStatsBean.setAciAssetclass("DE"); ////TODO - calculation
                    qStatsBean.setInstrCode(instrument.getInstrumentCode());
                    qStatsBean.setHolding(instrument.getHoldingPrice());
                    qStatsBean.setBookValue(instrument.getCurrentBookValue());
                    qStatsBean.setCurrencyCode(instrument.getIssueCurrency());
                    qStatsBean.setSecurityName(instrument.getInstrumentDescription());
                    qStatsBean.setMarketValue(instrument.getBaseCurrentMarketValue());

                    BigDecimal perOfPort = qStatsBean.getMarketValue().divide(qStatsBean.getMvTotal(), 3, BigDecimal.ROUND_FLOOR);
                    qStatsBean.setPerOfPort(perOfPort); //TODO - calculation

                    qStatsBean.setWeighting(BigDecimal.ZERO); //TODO - calculation
                    qStatsBean.setEqtIndexLink(Boolean.FALSE); //TODO - check
                    qStatsBean.setAfrican(Boolean.FALSE); //TODO - check
                    BigDecimal barraMarketCap = Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getMarketCapitalization).orElse(BigDecimal.ZERO);
                    BigDecimal sharesInIssue = Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getSharesOutstanding).orElse(BigDecimal.ZERO);
                    qStatsBean.setMarketCap(barraMarketCap);
                    qStatsBean.setSharesInIssue(sharesInIssue); //TODO - verify
                    qStatsBean.setAddClassification("Classfication"); //TODO - calculation
                    qStatsBean.setTtmInc(BigDecimal.ZERO); //TODO - calculation

                    String issuer = Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getIssuer).orElse(null);
                    BigDecimal coupon = Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getCoupon).orElse(BigDecimal.ZERO);
                    qStatsBean.setIssuerCode(issuer); //TODO - verify
                    qStatsBean.setCouponRate(coupon);
                    qStatsBean.setCurrentYield(barraAssetInfo.getCurrentYield());
                    qStatsBean.setInstrRateST("InstraRateST"); //TODO - check
                    qStatsBean.setInstrRateLT("InstraRateLT"); //TODO - check
                    qStatsBean.setIssuerRateST("IssuerRateST"); //TODO - check
                    qStatsBean.setIssuerRateLT("IssuerRateLT"); //TODO - check

                    String girIssuer = Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getGirIssuer).orElse(null);

                    qStatsBean.setRateAgency(girIssuer); //TODO - check
                    qStatsBean.setCompConDeb(Boolean.FALSE);

                    IssuerMappings issuerMappings = issuerMappingsRepository.findByIssuerCode(issuer);
                    BigDecimal marketCap = Optional.ofNullable(issuerMappings).map(IssuerMappings::getMarketCapitalisation).orElse(BigDecimal.ZERO);
                    BigDecimal capReserves = Optional.ofNullable(issuerMappings).map(IssuerMappings::getCapitalReserves).orElse(BigDecimal.ZERO);
                    qStatsBean.setMarketCap(marketCap); //TODO - verify
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

            java.sql.Date quarterDate = new java.sql.Date(118, 0, 1);
            java.sql.Date sqlPricingRedemptionDate = new java.sql.Date(118, 2, 1);
            java.sql.Date sqlResetMaturityDate = new java.sql.Date(118, 3, 1);
            BigDecimal couponRate = new BigDecimal(2.1d);
            BigDecimal currentYield = new BigDecimal(1.1d);
            BigDecimal effWeight = new BigDecimal(5.3d);

            String formula = "(=MDURATION("+quarterDate+";"+";"+sqlResetMaturityDate+";"
                    +couponRate+";"+currentYield+";"
                    +2+"))*"+effWeight+"*"+365.25;

            Row headerRow = sheet.createRow(0);
            Cell cell = headerRow.createCell(0);
            cell.setCellValue(formula);

            workbook.write(fileOut);

        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


