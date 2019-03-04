package za.co.global.services.report;

import org.springframework.stereotype.Service;
import za.co.global.domain.fileupload.client.DailyPricing;
import za.co.global.domain.fileupload.client.InstrumentData;
import za.co.global.domain.fileupload.mapping.InstrumentCode;
import za.co.global.domain.fileupload.system.BarraAssetInfo;
import za.co.global.domain.report.ReportDataCollectionBean;
import za.co.global.services.Validator;

import java.math.BigDecimal;

@Service
public class QstatsReportValidator implements Validator<ReportDataCollectionBean> {

    @Override
    public String validate(ReportDataCollectionBean reportDataCollectionBean) {
        BarraAssetInfo barraAssetInfo = reportDataCollectionBean.getBarraAssetInfo();
        BarraAssetInfo netAsset = reportDataCollectionBean.getNetAsset();
        DailyPricing dailyPricing = reportDataCollectionBean.getDailyPricing();
        InstrumentCode instrumentCode = reportDataCollectionBean.getInstrumentCode();
        InstrumentData instrumentData = reportDataCollectionBean.getInstrumentData();
        BigDecimal netCurrentMarketValue = reportDataCollectionBean.getNetCurrentMarketValue();

        if(instrumentCode == null) {
            return "There is no instrument mapping to barra asset, instrument code: " + instrumentData.getInstrumentCode();
        }

        if(barraAssetInfo == null) {
            return "There is no mapping to barra asset to the instrument code: " + instrumentCode.getBarraCode();
        }
        if(netAsset == null || netAsset.getEffExposure() == null) {
            return "There is no value for net eff exposure in barra";
        }
        if(netCurrentMarketValue == null) {
            return "There is no net value for current market base value, portfolio code:"+ instrumentData.getPortfolioCode()
                    +", instrument code:" + instrumentData.getInstrumentCode();
        }
        if(netAsset.getEffExposure().compareTo(netCurrentMarketValue) != 0) {
            return "Net eff exposure from barra and net base current market values are not equal- portfolio code:"
                    + instrumentData.getPortfolioCode() +", instrument code:" + instrumentData.getInstrumentCode();
        }

        if(reportDataCollectionBean.getReg28InstrumentType() == null) {
            return "There is no Reg28 mapping to asset id:"+ barraAssetInfo.getAssetId()+", Reg28_InstrType:"+barraAssetInfo.getReg28InstrType();
        }

        if(reportDataCollectionBean.getIssuerMapping() == null || reportDataCollectionBean.getIssuerMapping().getIssuerCode() == null) {
            return "There is no issuer code mapped to GIR issuer:"+ barraAssetInfo.getGirIssuer();
        }

        if(reportDataCollectionBean.getMaturityDate() == null) { //TODO - check maturity date with asset id or instrument code.
            return "There is no Maturity date mapping to barra asset id:'"+ barraAssetInfo.getAssetId()+"', Instrument code:"+ instrumentCode.getManagerCode();
        }

        if(dailyPricing == null) {
            return "There is no daily pricing entry to the issuer: "+ barraAssetInfo.getGirIssuer() + ", bond code: "+barraAssetInfo.getAssetId();
        }
        return null;
    }
}
