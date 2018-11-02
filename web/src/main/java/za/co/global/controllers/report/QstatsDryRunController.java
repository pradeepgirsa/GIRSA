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
import za.co.global.domain.fileupload.client.fpm.Holding;
import za.co.global.domain.fileupload.client.fpm.HoldingCategory;
import za.co.global.domain.fileupload.client.fpm.Instrument;
import za.co.global.domain.fileupload.mapping.InstrumentCode;
import za.co.global.domain.fileupload.mapping.IssuerMapping;
import za.co.global.domain.fileupload.mapping.PSGFundMapping;
import za.co.global.domain.fileupload.mapping.Reg28InstrumentType;
import za.co.global.domain.fileupload.system.BarraAssetInfo;
import za.co.global.domain.report.HoldingValidationBean;
import za.co.global.domain.report.QStatsBean;
import za.co.global.domain.report.ReportData;
import za.co.global.domain.report.ReportStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class QstatsDryRunController extends AbstractQstatsReportController {

    private static final String VIEW_FILE = "report/asisaQueueStats";

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
        List<Holding> holdings = getHoldings(client, reportData);

        List<QStatsBean> qStatsBeans = new ArrayList<>();
//        BarraAssetInfo netAsset = assetInfoRepository.findByAssetId("897"); //TODO - verify
        BarraAssetInfo netAsset = barraAssetInfoRepository.findByNetIndicatorIsTrue(); //TODO - verify
        for(Holding holding: holdings) {
            PSGFundMapping psgFundMapping = psgFundMappingRepository.findByManagerFundCode(holding.getPortfolioCode());
            NumberOfAccounts numberofAccounts = numberOfAccountsRepository.findByFundCode(psgFundMapping.getPsgFundCode());
            InstitutionalDetails institutionalDetails = institutionalDetailsRepository.findByFundCode(psgFundMapping.getPsgFundCode());
            for (HoldingCategory holdingCategory : holding.getHoldingCategories()) {
                for (Instrument instrument : holdingCategory.getInstruments()) {
                    InstrumentCode instrumentCode = instrumentCodeRepository.findByManagerCode(instrument.getInstrumentCode());

                    BarraAssetInfo barraAssetInfo = null;
                    if (instrumentCode != null && instrumentCode.getBarraCode() != null) {
                        barraAssetInfo = barraAssetInfoRepository.findByAssetId(instrumentCode.getBarraCode());
                    }

                    String reg28InstrType = barraAssetInfo.getReg28InstrType();
                    Reg28InstrumentType reg28InstrumentType = reg28InstrumentTypeRepository.findByReg28InstrType(reg28InstrType);

                    String issuerCode = Optional.ofNullable(barraAssetInfo).map(BarraAssetInfo::getGirIssuer).orElse(null);
                    IssuerMapping issuerMapping = issuerMappingsRepository.findByBarraCode(issuerCode); //TODO - verify from which field of issuer mapping


                    Date maturityDate = getMaturityDate(barraAssetInfo);

                    HoldingValidationBean holdingValidationBean = new HoldingValidationBean.Builder()
                            .setHolding(holding)
                            .setBarraAssetInfo(barraAssetInfo)
                            .setInstitutionalDetails(institutionalDetails)
                            .setInstrumentCode(instrumentCode)
                            .setNetAsset(netAsset)
                            .setMaturityDate(maturityDate)
                            .setPsgFundMapping(psgFundMapping)
                            .setNumberOfAccounts(numberofAccounts)
                            .setReg28InstrumentType(reg28InstrumentType)
                            .setIssuerMapping(issuerMapping)
                            .build();


                    String error = validator.validate(holdingValidationBean);
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
