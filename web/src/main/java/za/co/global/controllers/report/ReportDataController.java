package za.co.global.controllers.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.client.InstrumentData;
import za.co.global.domain.report.ReportData;
import za.co.global.domain.report.ReportStatus;
import za.co.global.persistence.fileupload.client.InstrumentDataRepository;
import za.co.global.persistence.report.ReportDataRepository;

import java.util.List;

@Controller
public class ReportDataController {

    @Autowired
    protected ReportDataRepository reportDataRepository;

    @Autowired
    protected InstrumentDataRepository instrumentDataRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportDataController.class);

    @GetMapping("/view_reportData")
    public ModelAndView viewReportData() {
        ModelAndView modelAndView = new ModelAndView("report/viewReportData");
        try {
            List<ReportData> reportDataList = reportDataRepository.findAll();
            modelAndView.addObject("reportDataList", reportDataList);
        } catch (Exception e) {
            LOGGER.error("Error on report data view", e);
            return modelAndView.addObject("errorMessage", "Error: "+e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/update_reportDataStatus")
    public ModelAndView updateReportData(@RequestParam(value = "reportDataId", required = false) String reportDataId) {
        ModelAndView modelAndView = new ModelAndView("report/viewReportData");
        try {
            if (reportDataId != null) {
                ReportData reportData = reportDataRepository.findOne(Long.parseLong(reportDataId));
                if (reportData != null) {
                    reportData.setReportStatus(ReportStatus.COMPLETED);

                    deleteInstrumentData(reportData.getClient());

                    reportDataRepository.save(reportData);
                }
            }
            modelAndView.addObject("reportDataList", reportDataRepository.findAll());
        } catch (Exception e) {
            LOGGER.error("Error updating report data status", e);
            return modelAndView.addObject("errorMessage", "Error: "+e.getMessage());
        }
        return modelAndView;
    }

    private void deleteInstrumentData(Client client) {
        List<InstrumentData> instrumentDataList = instrumentDataRepository.findByClient(client);
        for(InstrumentData instrumentData :  instrumentDataList) {
            instrumentDataRepository.delete(instrumentData);
        }
    }
}
