package za.co.global.controllers.report;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.client.DailyPricing;
import za.co.global.domain.fileupload.client.InstrumentData;
import za.co.global.domain.fileupload.mapping.ClientFundMapping;
import za.co.global.domain.fileupload.mapping.InstrumentCode;
import za.co.global.domain.fileupload.mapping.IssuerMapping;
import za.co.global.domain.fileupload.mapping.Reg28InstrumentType;
import za.co.global.domain.fileupload.system.BarraAssetInfo;
import za.co.global.domain.report.ReportData;
import za.co.global.domain.report.ReportDataCollectionBean;
import za.co.global.persistence.client.ClientRepository;
import za.co.global.persistence.fileupload.client.DailyPricingRepository;
import za.co.global.persistence.fileupload.client.InstrumentDataRepository;
import za.co.global.persistence.fileupload.mapping.ClientFundMappingRepository;
import za.co.global.persistence.fileupload.mapping.InstrumentCodeRepository;
import za.co.global.persistence.fileupload.mapping.IssuerMappingsRepository;
import za.co.global.persistence.fileupload.mapping.Reg28InstrumentTypeRepository;
import za.co.global.persistence.fileupload.system.BarraAssetInfoRepository;
import za.co.global.persistence.report.ReportDataRepository;
import za.co.global.services.Validator;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

public abstract class AbstractQstatsReportController {

    @Autowired
    protected ClientFundMappingRepository clientFundMappingRepository;

    @Autowired
    protected InstrumentDataRepository instrumentDataRepository;
//
//    @Autowired
//    protected InstitutionalDetailsRepository institutionalDetailsRepository;
//
//    @Autowired
//    protected NumberOfAccountsRepository numberOfAccountsRepository;

    @Autowired
    protected InstrumentCodeRepository instrumentCodeRepository;

    @Autowired
    protected BarraAssetInfoRepository barraAssetInfoRepository;

    @Autowired
    protected ClientRepository clientRepository;

    @Autowired
    protected ReportDataRepository reportDataRepository;
//
//    @Autowired
//    protected SecurityListingRepository securityListingRepository;

    @Autowired
    protected Validator<ReportDataCollectionBean> validator;


    @Autowired
    protected Reg28InstrumentTypeRepository reg28InstrumentTypeRepository;

    @Autowired
    protected IssuerMappingsRepository issuerMappingsRepository;
//
//    @Autowired
//    protected HoldingRepository holdingRepository;

    protected Map<String, Date> maturityDateMap = new HashMap<>();

    @Autowired
    private DailyPricingRepository dailyPricingRepository;

    protected Date getMaturityDate(BarraAssetInfo barraAssetInfo) {
        Date maturityDate = null;
        if(barraAssetInfo != null) {
            return barraAssetInfo.getMaturityDate();
        }
        return maturityDate;
    }

//    protected Date getMaturityDate(BarraAssetInfo barraAssetInfo, String instrumentCode) {
//        Date maturityDate = null;
//        if(barraAssetInfo != null) {
//            if (maturityDateMap.get(barraAssetInfo.getAssetId()) != null) {
//                maturityDate = maturityDateMap.get(barraAssetInfo.getAssetId());
//            } else {
//                maturityDate = getMaturityDateFromSecurityListing(barraAssetInfo.getMaturityDate(), instrumentCode);
//                maturityDateMap.put(barraAssetInfo.getAssetId(), maturityDate);
//            }
//        }
//        return maturityDate;
//    }
//
//    private Date getMaturityDateFromSecurityListing(Date barraMaturityDate, String instrumentCode) {
//        if(barraMaturityDate != null) {
//            return barraMaturityDate;
//        }
//        SecurityListing securityListing = securityListingRepository.findBySecurityCode(instrumentCode);
//        if(securityListing != null) {
//            String couponPaymentDates = securityListing.getCouponPaymentDates() != null ?
//                    securityListing.getCouponPaymentDates().replace(" ", "") : null;
//            if(couponPaymentDates != null) {
//                String[] dates = couponPaymentDates.split(",");
//                for (String dateInString : dates) {
//                    Date date = parseDate(dateInString);
//                    if (date != null && date.after(new Date())) {
//                        return date;
//                    }
//                }
//            }
//            Date maturityDate = securityListing.getMaturityDate();
//            if (maturityDate != null) {
//                while (maturityDate.before(new Date())) {
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(maturityDate);
//                    cal.add(Calendar.YEAR, 1);
//                    maturityDate = cal.getTime();
//                }
//                return maturityDate;
//            }
//        }
//        return null;
//    }

    private Date parseDate(String dateInString) {
        String datePattern = "ddMMMyyyy";
        try {
            if(dateInString.trim().length() == 5) {
                dateInString = datePattern+ Year.now().getValue();
            }
            DateFormat dateFormat = new SimpleDateFormat(datePattern);
            return dateFormat.parse(dateInString);
        } catch (ParseException e) {
            getLogger().error("Date parse error to format:{}, value: {}", datePattern, dateInString);
        }

        //TODO - other date format
        return null;
    }

    protected List<InstrumentData> getInstrumentData(Client client, ReportData reportData) {
        List<InstrumentData> instrumentDataList = instrumentDataRepository.findByClientAndReportDataIsNull(client);
        if(reportData != null) {
            List<InstrumentData> existingInstruments = instrumentDataRepository.findByClientAndReportData(client, reportData);
            existingInstruments.forEach(instrumentData -> instrumentDataList.add(instrumentData));
        }
        return instrumentDataList;
    }

    protected IssuerMapping getIssuerMapping(BarraAssetInfo barraAssetInfo) {
        String girIssuer = Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getGirIssuer).orElse(null);
        IssuerMapping issuerMapping = null;
        if(girIssuer != null) {
            List<IssuerMapping> issuerMappings =issuerMappingsRepository.findByBarraGIRIssuerNameIgnoreCase(girIssuer);
            if(!issuerMappings.isEmpty()) {
                issuerMapping = issuerMappings.get(0);
            }
        }
        return issuerMapping;
    }

    protected BarraAssetInfo getBarraAssetInfo(InstrumentCode instrumentCode) {
        BarraAssetInfo barraAssetInfo = null;
        if(instrumentCode != null && instrumentCode.getBarraCode() != null) {
            barraAssetInfo = barraAssetInfoRepository.findByAssetId(instrumentCode.getBarraCode());
        }
        return barraAssetInfo;
    }

    protected DailyPricing getDailyPricing(String assetId) {
        return dailyPricingRepository.findByBondCode(assetId);
    }

    protected ReportDataCollectionBean getReportCollectionBean(InstrumentData instrumentData, BarraAssetInfo netAsset,
                                                               ClientFundMapping clientFundMapping, Date reportDate, BigDecimal netCurrentMarketValue) {

        InstrumentCode instrumentCode = instrumentCodeRepository.findByManagerCode(instrumentData.getInstrumentCode());

        BarraAssetInfo barraAssetInfo = getBarraAssetInfo(instrumentCode);

        String reg28InstrType = barraAssetInfo.getReg28InstrType();
        Reg28InstrumentType reg28InstrumentType = null;
        if(reg28InstrType != null) {
            reg28InstrumentType = reg28InstrumentTypeRepository.findByReg28InstrType(reg28InstrType);
        }

        IssuerMapping issuerMapping = getIssuerMapping(barraAssetInfo);

        DailyPricing dailyPricing = getDailyPricing(instrumentData.getInstrumentCode());

        Date maturityDate = getMaturityDate(barraAssetInfo);

        ReportDataCollectionBean reportDataCollectionBean = new ReportDataCollectionBean.Builder()
                .setInstrumentData(instrumentData)
                .setBarraAssetInfo(barraAssetInfo)
                .setNetCurrentMarketValue(netCurrentMarketValue)
                .setInstrumentCode(instrumentCode)
                .setNetAsset(netAsset)
                .setMaturityDate(maturityDate)
                .setClientFundMapping(clientFundMapping)
                .setReg28InstrumentType(reg28InstrumentType)
                .setIssuerMapping(issuerMapping)
                .setDailyPricing(dailyPricing)
                .setSettlementDate(reportDate)
                .build();

        return reportDataCollectionBean;
    }

    protected abstract Logger getLogger();

}
