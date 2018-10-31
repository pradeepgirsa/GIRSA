package za.co.global.services.report;

import org.springframework.stereotype.Service;
import za.co.global.domain.report.HoldingValidationBean;
import za.co.global.services.Validator;

@Service
public class QstatsReportValidator implements Validator<HoldingValidationBean> {

    @Override
    public String validate(HoldingValidationBean holdingValidationBean) {
        if(holdingValidationBean.getBarraAssetInfo() == null) {
            return "There is no mapping to barra asset to the instrument code: " + holdingValidationBean.getInstrumentCode().getBarraCode();
        }
        if(holdingValidationBean.getNetAsset() == null || holdingValidationBean.getNetAsset().getEffExposure() == null) {
            return "There is no value for net eff exposure in barra";
        }
        if(holdingValidationBean.getHolding().getNetBaseCurrentMarketValue() == null) {
            return "There is no net value for current market base value, portfolio code:"+holdingValidationBean.getHolding().getPortfolioCode()
                    +", instrument code:" + holdingValidationBean.getBarraAssetInfo().getAssetId();
        }
        if(holdingValidationBean.getNetAsset().getEffExposure().compareTo(holdingValidationBean.getHolding().getNetBaseCurrentMarketValue()) != 0) {
            return "Net eff exposure from barra and net base current market values are not equal- portfolio code:"
                    +holdingValidationBean.getHolding().getPortfolioCode() +", instrument code:" + holdingValidationBean.getBarraAssetInfo().getAssetId();
        }
        if(holdingValidationBean.getInstitutionalDetails() == null || holdingValidationBean.getInstitutionalDetails().getSplit() == null) {
            return "There is no institutional split matching to fund code:"+holdingValidationBean.getHolding().getPortfolioCode()
                    + ", FPM fund code:"+ holdingValidationBean.getPsgFundMapping().getPsgFundCode();
        }
        if(holdingValidationBean.getNumberOfAccounts() == null || holdingValidationBean.getNumberOfAccounts().getTotal() == null) {
            return "Number of accounts are not mapped for fund code:"+holdingValidationBean.getHolding().getPortfolioCode()
                    + ", FPM fund code:"+ holdingValidationBean.getPsgFundMapping().getPsgFundCode();
        }
        if(holdingValidationBean.getMaturityDate() == null) { //TODO - check maturity date with asset id or instrument code.
            return "There is no Maturity date mapping to asset id:"+holdingValidationBean.getBarraAssetInfo().getAssetId();
        }
        return null;
    }
}
