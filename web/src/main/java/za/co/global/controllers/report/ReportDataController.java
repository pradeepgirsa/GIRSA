package za.co.global.controllers.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.domain.report.ReportData;
import za.co.global.domain.report.ReportStatus;
import za.co.global.persistence.report.ReportDataRepository;

import java.util.List;

@Controller
public class ReportDataController {

    @Autowired
    protected ReportDataRepository reportDataRepository;

    @GetMapping("/view_reportData")
    public ModelAndView viewReportData() {
        List<ReportData> reportDataList = reportDataRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("report/viewReportData");
        modelAndView.addObject("reportDataList", reportDataList);
        return modelAndView;
    }

    @GetMapping("/update_reportDataStatus")
    public ModelAndView updateReportData(@RequestParam(value = "reportDataId", required = false) String reportDataId){
        if(reportDataId != null) {
            ReportData reportData = reportDataRepository.findOne(Long.parseLong(reportDataId));
            if (reportData != null) {
                reportData.setReportStatus(ReportStatus.COMPLETED);
                reportDataRepository.save(reportData);
            }
        }
        return viewReportData();
    }
}
