package za.co.global.services.report;

import org.springframework.stereotype.Service;
import za.co.global.domain.fileupload.client.InstitutionalDetails;
import za.co.global.domain.fileupload.client.NumberOfAccounts;
import za.co.global.domain.fileupload.client.fpm.Holding;
import za.co.global.domain.fileupload.mapping.DailyPricing;
import za.co.global.domain.fileupload.mapping.InstrumentCode;
import za.co.global.domain.fileupload.system.BarraAssetInfo;
import za.co.global.domain.report.ReportDataCollectionBean;
import za.co.global.services.Validator;

@Service
public class QstatsReportValidator implements Validator<ReportDataCollectionBean> {

    @Override
    public String validate(ReportDataCollectionBean reportDataCollectionBean) {
        BarraAssetInfo barraAssetInfo = reportDataCollectionBean.getBarraAssetInfo();
        BarraAssetInfo netAsset = reportDataCollectionBean.getNetAsset();
        Holding holding = reportDataCollectionBean.getInstrument().getHoldingCategory().getHolding();
        InstitutionalDetails institutionalDetails = reportDataCollectionBean.getInstitutionalDetails();
        NumberOfAccounts numberOfAccounts = reportDataCollectionBean.getNumberOfAccounts();
        DailyPricing dailyPricing = reportDataCollectionBean.getDailyPricing();

        InstrumentCode instrumentCode = reportDataCollectionBean.getInstrumentCode();

        if(barraAssetInfo == null) {
            return "There is no mapping to barra asset to the instrument code: " + instrumentCode.getBarraCode();
        }
        if(netAsset == null || netAsset.getEffExposure() == null) {
            return "There is no value for net eff exposure in barra";
        }
        if(holding.getNetBaseCurrentMarketValue() == null) {
            return "There is no net value for current market base value, portfolio code:"+ holding.getPortfolioCode()
                    +", instrument code:" + barraAssetInfo.getAssetId();
        }
        if(netAsset.getEffExposure().compareTo(holding.getNetBaseCurrentMarketValue()) != 0) {
            return "Net eff exposure from barra and net base current market values are not equal- portfolio code:"
                    + holding.getPortfolioCode() +", instrument code:" + barraAssetInfo.getAssetId();
        }

        if(institutionalDetails == null || institutionalDetails.getTotal() == null) {
            return "There is no institutional split matching to fund code:"+ holding.getPortfolioCode()
                    + ", FPM fund code:"+ reportDataCollectionBean.getPsgFundMapping().getPsgFundCode();
        }
        if(numberOfAccounts == null || numberOfAccounts.getTotal() == null) {
            return "Number of accounts are not mapped for fund code:"+ holding.getPortfolioCode()
                    + ", FPM fund code:"+ reportDataCollectionBean.getPsgFundMapping().getPsgFundCode();
        }

        if(reportDataCollectionBean.getReg28InstrumentType() == null) {
            return "There is no Reg28 mapping to asset id:"+ barraAssetInfo.getAssetId()+", Reg28_InstrType:"+barraAssetInfo.getReg28InstrType();
        }

        if(reportDataCollectionBean.getIssuerMapping() == null || reportDataCollectionBean.getIssuerMapping().getIssuerCode() == null) {
            return "There is no issuer code mapped to GIR issuer:"+ barraAssetInfo.getGirIssuer();
        }

        if(reportDataCollectionBean.getMaturityDate() == null) { //TODO - check maturity date with asset id or instrument code.
            return "There is no Maturity date mapping to asset id:'"+ barraAssetInfo.getAssetId()+"', Instrument code:"+ instrumentCode.getManagerCode();
        }

        if(dailyPricing == null) {
            return "There is no daily pricing entry to the issuer: "+ barraAssetInfo.getGirIssuer() + ", bond code: "+barraAssetInfo.getAssetId();
        }
        return null;
    }
}
