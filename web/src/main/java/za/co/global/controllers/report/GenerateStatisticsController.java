package za.co.global.controllers.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.persistence.fileupload.system.AssetDSU3Repository;
import za.co.global.persistence.fileupload.system.AssetDSU4Repository;

public class GenerateStatisticsController {

    private static final String VIEW_FILE = "report/generateStatistics";

    @Value("${file.upload.folder}")
    protected String fileUploadFolder;

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateStatisticsController.class);

    @Autowired
    private AssetDSU4Repository assetDSU4Repository;

    @Autowired
    private AssetDSU3Repository assetDSU3Repository;

    @GetMapping("/generate_statistics")
    public ModelAndView displayScreen() {
        ModelAndView modelAndView = new ModelAndView(VIEW_FILE);
        return modelAndView;
    }

    @PostMapping("/generate_statistics")
    public ModelAndView generateReport() {

        ModelAndView modelAndView = new ModelAndView(VIEW_FILE);
        try {





             




               // String filePath = createExcelFile(qStatsBeans, client);
                //modelAndView.addObject("successMessage", "Qstats file created successfully, file: " + filePath);

        } catch (Exception e) {
            LOGGER.error("Error generating report file", e);
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        return modelAndView;
    }

}
