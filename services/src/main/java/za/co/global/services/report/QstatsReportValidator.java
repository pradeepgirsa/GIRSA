package za.co.global.services.report;

import org.springframework.stereotype.Service;
import za.co.global.domain.fileupload.client.DailyPricing;
import za.co.global.domain.fileupload.client.InstrumentData;
import za.co.global.domain.fileupload.mapping.ClientFundMapping;
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
        ClientFundMapping fundMapping = reportDataCollectionBean.getClientFundMapping();

        if(fundMapping == null) {
            return "There is no fund mapped to portfolio code: " + instrumentData.getInstrumentCode()
                    +", fix fund mappings";
        }
        if(barraAssetInfo == null) {
            return "There is no Barra asset mapped to portfolio code: " + instrumentData.getPortfolioCode()
                    + " and instrument code: "+instrumentData.getInstrumentCode();
        }
        if(instrumentCode == null) {
            return "There is no matching barra asset for instrument code: " + instrumentData.getInstrumentCode()
                    +", fix instrument codes or barra asset";
        }
        if(netAsset == null || netAsset.getEffExposure() == null) {
            return "There is no value for net eff exposure in barra";
        }

        if(netCurrentMarketValue == null) {
            return "There is no net value for current market base value, portfolio code:"+ instrumentData.getPortfolioCode()
                    +", instrument code:" + instrumentData.getInstrumentCode();
        }
        //TODO - if difference is 1 value then ignore the condition
        if(netAsset.getEffExposure().compareTo(netCurrentMarketValue) != 0) {
            return "Net eff exposure from barra and net base current market values are not equal- portfolio code:"
                    + instrumentData.getPortfolioCode() +", instrument code:" + instrumentData.getInstrumentCode();
        }

        if(reportDataCollectionBean.getReg28InstrumentType() == null) {
            if(!(barraAssetInfo.getReg28InstrType() == null && barraAssetInfo.getInstSubType().equalsIgnoreCase("Composite") )) {
                return "There is no Reg28 mapping to Barra asset id: " + barraAssetInfo.getAssetId() + ", Reg28_InstrType: " + barraAssetInfo.getReg28InstrType();
            }
        }

        if(reportDataCollectionBean.getIssuerMapping() != null) {
            if(reportDataCollectionBean.getIssuerMapping().getIssuerCode() == null) {
                return "There is no issuer code mapped to the GIR issuer:" + barraAssetInfo.getGirIssuer();
            }
        }

//        if(reportDataCollectionBean.getMaturityDate() == null) { //TODO - check maturity date with asset id or instrument code.
//            return "There is no Maturity date mapping to Barra asset id:'"+ barraAssetInfo.getAssetId()+"', Instrument code:"+ instrumentCode.getManagerCode();
//        }

//        if(dailyPricing == null) {
//            return "There is no daily pricing entry to the GIR issuer:"+ barraAssetInfo.getGirIssuer() + ", Barra asset id:"+barraAssetInfo.getAssetId();
//        }
        if(fundMapping == null || fundMapping.getClientFundCode() == null) {
            return "There is no client fund mapping to Portfolio code:"+instrumentData.getPortfolioCode();
        }
        return null;
    }
}
