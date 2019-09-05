package za.co.global.services.report;

import org.springframework.stereotype.Service;
import za.co.global.domain.fileupload.client.InstrumentData;
import za.co.global.domain.fileupload.mapping.ClientFundMapping;
import za.co.global.domain.fileupload.mapping.InstrumentCode;
import za.co.global.domain.fileupload.system.BarraAssetInfo;
import za.co.global.domain.report.ReportDataCollectionBean;
import za.co.global.services.Validator;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class QstatsReportValidator implements Validator<ReportDataCollectionBean> {

    @Override
    public String validate(ReportDataCollectionBean reportDataCollectionBean) {
        BarraAssetInfo barraAssetInfo = reportDataCollectionBean.getBarraAssetInfo();
        BarraAssetInfo netAsset = reportDataCollectionBean.getNetAsset();
//        DailyPricing dailyPricing = reportDataCollectionBean.getDailyPricing();
        InstrumentCode instrumentCode = reportDataCollectionBean.getInstrumentCode();
        InstrumentData instrumentData = reportDataCollectionBean.getInstrumentData();
        BigDecimal netCurrentMarketValue = reportDataCollectionBean.getNetCurrentMarketValue();
        ClientFundMapping fundMapping = reportDataCollectionBean.getClientFundMapping();
        Map<String, Boolean> netAssetEffExposureVerifyMap = reportDataCollectionBean.getNetAssetEffExposureVerifyMap();

        if(fundMapping == null || fundMapping.getClientFundCode() == null) {
            return "There is no fund mapped to portfolio code: " + instrumentData.getPortfolioCode();
        }
//        if(barraAssetInfo == null) {
//            return "There is no Barra asset mapped to portfolio code: " + instrumentData.getPortfolioCode()
//                    + " and instrument code: "+instrumentData.getInstrumentCode();
//        }
        if(instrumentCode == null) {
            return "There is no matching barra asset for instrument code: " + instrumentData.getInstrumentCode();
        }

        //This need to be verified to fund only once that is why added this map as once tha values is true it is not going to check it again
        if(netAssetEffExposureVerifyMap != null) {
            if (netAssetEffExposureVerifyMap.get(barraAssetInfo.getFundName()) != null && !netAssetEffExposureVerifyMap.get(barraAssetInfo.getFundName())) {
                netAssetEffExposureVerifyMap.put(barraAssetInfo.getFundName(), Boolean.TRUE);
                if (barraAssetInfo != null && (netAsset == null || netAsset.getEffExposure() == null)) {
                    return "There is no net eff exposure value for barra fund:" + barraAssetInfo.getFundName();
                }
                if (netCurrentMarketValue != null) {
                    // return "There is no net current market base value for client, portfolio code:" + instrumentData.getPortfolioCode();

                    //TODO - if difference is 1 value then ignore the condition
                    if (netAsset.getEffExposure().compareTo(netCurrentMarketValue) != 0) {
                        double netEffExposure = netAsset.getEffExposure().abs().doubleValue();
                        double netCurrentMarketValueInDouble = netCurrentMarketValue.abs().doubleValue();
                        double dif = netEffExposure > netCurrentMarketValueInDouble ? netEffExposure - netCurrentMarketValueInDouble :
                                netCurrentMarketValueInDouble - netEffExposure;
                        if (dif > 5.00d) {
                            return "Net eff exposure from barra and net base current market value from client are not equal- portfolio code:"
                                    + instrumentData.getPortfolioCode() + ", barra fund name:" + barraAssetInfo.getFundName();
                        }
                    }
                }
            }
        }
        if(reportDataCollectionBean.getReg28InstrumentType() == null) {
            if(!(barraAssetInfo.getReg28InstrType() == null && (barraAssetInfo.getInstSubType().equalsIgnoreCase("Composite")
                    || barraAssetInfo.getInstType().equalsIgnoreCase("ETF") || barraAssetInfo.getInstType().equalsIgnoreCase("Exchange Traded Fund") ))) {
                return "There is no Reg28 mapping to barra fund:"+barraAssetInfo.getFundName()+", asset id: " + barraAssetInfo.getAssetId() + ", Reg28_InstrType: " + barraAssetInfo.getReg28InstrType();
            }
        }

//        if(reportDataCollectionBean.getIssuerMapping() != null) {
//            if(reportDataCollectionBean.getIssuerMapping().getIssuerCode() == null) {
//                return "There is no issuer code mapped to the barra fund: "+barraAssetInfo.getFundName()+", GIR issuer:" + barraAssetInfo.getGirIssuer();
//            }
//        }

//        if(reportDataCollectionBean.getMaturityDate() == null) { //TODO - check maturity date with asset id or instrument code.
//            return "There is no Maturity date mapping to Barra asset id:'"+ barraAssetInfo.getAssetId()+"', Instrument code:"+ instrumentCode.getManagerCode();
//        }

//        if(dailyPricing == null) {
//            return "There is no daily pricing entry to the GIR issuer:"+ barraAssetInfo.getGirIssuer() + ", Barra asset id:"+barraAssetInfo.getAssetId();
//        }
        return null;
    }
}
