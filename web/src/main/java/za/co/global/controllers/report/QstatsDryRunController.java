package za.co.global.controllers.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.client.InstrumentData;
import za.co.global.domain.fileupload.mapping.ClientFundMapping;
import za.co.global.domain.fileupload.system.BarraAssetInfo;
import za.co.global.domain.report.ReportData;
import za.co.global.domain.report.ReportDataCollectionBean;
import za.co.global.domain.report.ReportStatus;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class QstatsDryRunController extends AbstractQstatsReportController {

    private static final String VIEW_FILE = "report/dryRun/asisaQueueStatsDryRun";

    private static final Logger LOGGER = LoggerFactory.getLogger(QstatsDryRunController.class);

    @GetMapping("/dry_run_qstats")
    public ModelAndView displayScreen() {
        List<Client> clients = clientRepository.findAll();
        ModelAndView modelAndView = new ModelAndView(VIEW_FILE);
        modelAndView.addObject("clients", clients);
        return modelAndView;
    }

    @PostMapping("/dry_run_qstats")
    public ModelAndView generateReport(String clientId) {
        ModelAndView modelAndView = new ModelAndView(VIEW_FILE);
        try {

            Client client = clientRepository.findOne(Long.parseLong(clientId));
            ReportData reportData = reportDataRepository.findByReportStatusAndClient(ReportStatus.REGISTERED, client);
            List<InstrumentData> instrumentDataList = getInstrumentData(client, reportData);

            List<BarraAssetInfo> netAssets = barraAssetInfoRepository.findByNetIndicatorIsTrue();
            BarraAssetInfo netAsset = netAssets.isEmpty() ? null : netAssets.get(0);

            BigDecimal netCurrentMarketValue = BigDecimal.ZERO;
            if (!CollectionUtils.isEmpty(instrumentDataList)) {
                netCurrentMarketValue = instrumentDataList.stream().map(InstrumentData::getCurrentMarketValue)
                        .reduce(BigDecimal::add).get();

                String errorString = "";
                for (InstrumentData instrumentData : instrumentDataList) {
                    ClientFundMapping clientFundMapping = clientFundMappingRepository.findByClientFundCode(instrumentData.getPortfolioCode());
                    ReportDataCollectionBean reportDataCollectionBean = getReportCollectionBean(instrumentData, netAsset, clientFundMapping, null, netCurrentMarketValue);
                    String error = validator.validate(reportDataCollectionBean);
                    if (error != null) {
                        if (StringUtils.isEmpty(errorString)) {
                            errorString = error;
                        } else {
                            errorString = errorString + ";  " + error;
                        }
                    }
                }
                if (!StringUtils.isEmpty(errorString)) {
                    return modelAndView.addObject("errorMessage", errorString);
                }
            } else {
                return modelAndView.addObject("errorMessage", "No instrument data to generate report");
            }
            return modelAndView.addObject("successMessage", "Dry run completed successfully, no errors");
        } catch (Exception e) {
            LOGGER.error("Error on report dry run", e);
            return modelAndView.addObject("errorMessage", e.getMessage());
        }
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

}
