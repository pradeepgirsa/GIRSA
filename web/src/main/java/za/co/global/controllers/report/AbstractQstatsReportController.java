package za.co.global.controllers.report;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.client.SecurityListing;
import za.co.global.domain.fileupload.client.fpm.Holding;
import za.co.global.domain.fileupload.system.BarraAssetInfo;
import za.co.global.domain.report.HoldingValidationBean;
import za.co.global.domain.report.ReportData;
import za.co.global.persistence.client.ClientRepository;
import za.co.global.persistence.fileupload.HoldingRepository;
import za.co.global.persistence.fileupload.client.InstitutionalDetailsRepository;
import za.co.global.persistence.fileupload.client.NumberOfAccountsRepository;
import za.co.global.persistence.fileupload.client.SecurityListingRepository;
import za.co.global.persistence.fileupload.mapping.InstrumentCodeRepository;
import za.co.global.persistence.fileupload.mapping.IssuerMappingsRepository;
import za.co.global.persistence.fileupload.mapping.PSGFundMappingRepository;
import za.co.global.persistence.fileupload.mapping.Reg28InstrumentTypeRepository;
import za.co.global.persistence.fileupload.system.BarraAssetInfoRepository;
import za.co.global.persistence.report.ReportDataRepository;
import za.co.global.services.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class AbstractQstatsReportController {

    @Autowired
    protected PSGFundMappingRepository psgFundMappingRepository;

    @Autowired
    protected InstitutionalDetailsRepository institutionalDetailsRepository;

    @Autowired
    protected NumberOfAccountsRepository numberOfAccountsRepository;

    @Autowired
    protected InstrumentCodeRepository instrumentCodeRepository;

    @Autowired
    protected BarraAssetInfoRepository barraAssetInfoRepository;

    @Autowired
    protected ClientRepository clientRepository;

    @Autowired
    protected ReportDataRepository reportDataRepository;

    @Autowired
    protected SecurityListingRepository securityListingRepository;

    @Autowired
    protected Validator<HoldingValidationBean> validator;


    @Autowired
    protected Reg28InstrumentTypeRepository reg28InstrumentTypeRepository;

    @Autowired
    protected IssuerMappingsRepository issuerMappingsRepository;

    @Autowired
    protected HoldingRepository holdingRepository;

    protected Map<String, Date> maturityDateMap;


    protected Date getMaturityDate(BarraAssetInfo barraAssetInfo) {
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

    protected List<Holding> getHoldings(Client client, ReportData reportData) {
        List<Holding> holdings;
        if(reportData != null) {
            holdings = holdingRepository.findByClientAndReportDataIsNullOrReportData(client, reportData);
        } else {
            reportData = new ReportData();
            reportData.setCreatedDate(new Date());
            reportData.setClient(client);
            holdings = holdingRepository.findByClientAndReportDataIsNull(client);
        }
        return holdings;
    }

    protected abstract Logger getLogger();

}
