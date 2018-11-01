package za.co.global.services.report;

import org.springframework.stereotype.Service;
import za.co.global.domain.fileupload.client.InstitutionalDetails;
import za.co.global.domain.fileupload.client.NumberOfAccounts;
import za.co.global.domain.fileupload.client.fpm.Holding;
import za.co.global.domain.fileupload.system.BarraAssetInfo;
import za.co.global.domain.report.HoldingValidationBean;
import za.co.global.services.Validator;

@Service
public class QstatsReportValidator implements Validator<HoldingValidationBean> {

    @Override
    public String validate(HoldingValidationBean holdingValidationBean) {
        BarraAssetInfo barraAssetInfo = holdingValidationBean.getBarraAssetInfo();
        BarraAssetInfo netAsset = holdingValidationBean.getNetAsset();
        Holding holding = holdingValidationBean.getHolding();
        InstitutionalDetails institutionalDetails = holdingValidationBean.getInstitutionalDetails();
        NumberOfAccounts numberOfAccounts = holdingValidationBean.getNumberOfAccounts();

        if(barraAssetInfo == null) {
            return "There is no mapping to barra asset to the instrument code: " + holdingValidationBean.getInstrumentCode().getBarraCode();
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

        if(institutionalDetails == null || institutionalDetails.getSplit() == null) {
            return "There is no institutional split matching to fund code:"+ holding.getPortfolioCode()
                    + ", FPM fund code:"+ holdingValidationBean.getPsgFundMapping().getPsgFundCode();
        }
        if(numberOfAccounts == null || numberOfAccounts.getTotal() == null) {
            return "Number of accounts are not mapped for fund code:"+ holding.getPortfolioCode()
                    + ", FPM fund code:"+ holdingValidationBean.getPsgFundMapping().getPsgFundCode();
        }

        if(holdingValidationBean.getReg28InstrumentType() == null) {
            return "There is no Reg28 mapping to asset id:"+ barraAssetInfo.getAssetId()+", Reg28_InstrType:"+barraAssetInfo.getReg28InstrType();
        }

        if(holdingValidationBean.getIssuerMapping() == null || holdingValidationBean.getIssuerMapping().getIssuerCode() == null) {
            return "There is no issuer code mapped to GIR issuer:"+ barraAssetInfo.getGirIssuer();
        }

        if(holdingValidationBean.getMaturityDate() == null) { //TODO - check maturity date with asset id or instrument code.
            return "There is no Maturity date mapping to asset id:"+ barraAssetInfo.getAssetId();
        }
        return null;
    }
}
