package za.co.global.controllers.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.client.InstitutionalDetails;
import za.co.global.domain.fileupload.client.NumberOfAccounts;
import za.co.global.domain.fileupload.client.notused.Holding;
import za.co.global.domain.fileupload.client.notused.HoldingCategory;
import za.co.global.domain.fileupload.client.notused.Instrument;
import za.co.global.domain.fileupload.mapping.ClientFundMapping;
import za.co.global.domain.fileupload.system.BarraAssetInfo;
import za.co.global.domain.report.ReportData;
import za.co.global.domain.report.ReportDataCollectionBean;
import za.co.global.domain.report.ReportStatus;

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

        Client client = clientRepository.findOne(Long.parseLong(clientId));
        ReportData reportData = reportDataRepository.findByReportStatusAndClient(ReportStatus.REGISTERED, client);
        List<Holding> holdings = getInstrumentData(client, reportData);

//        BarraAssetInfo netAsset = assetInfoRepository.findByAssetId("897"); //TODO - verify
        List<BarraAssetInfo> netAssets = barraAssetInfoRepository.findByNetIndicatorIsTrue(); //TODO - verify
        BarraAssetInfo netAsset = netAssets.isEmpty() ? null : netAssets.get(0);
        for(Holding holding: holdings) {
            ClientFundMapping clientFundMapping = clientFundMappingRepository.findByManagerFundCode(holding.getPortfolioCode());
            NumberOfAccounts numberofAccounts = numberOfAccountsRepository.findByFundCode(clientFundMapping.getClientFundCode());
            InstitutionalDetails institutionalDetails = institutionalDetailsRepository.findByClientFundCode(clientFundMapping.getClientFundCode());
            for (HoldingCategory holdingCategory : holding.getHoldingCategories()) {
                for (Instrument instrument : holdingCategory.getInstruments()) {
                    ReportDataCollectionBean reportDataCollectionBean = getReportCollectionBean(instrument, institutionalDetails, netAsset, clientFundMapping,
                            numberofAccounts, null);

                    String error = validator.validate(reportDataCollectionBean);
                    if (error != null)
                        return modelAndView.addObject("saveError", error);
                }
            }
        }
        return modelAndView.addObject("saveMessage", "report.generation.dryRun.success");
    }



    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

}
