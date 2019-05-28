package za.co.global.controllers.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.domain.fileupload.system.AssetDSU4;
import za.co.global.persistence.fileupload.system.AssetDSU3Repository;
import za.co.global.persistence.fileupload.system.AssetDSU4Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class GenerateStatisticsController {

    private static final String VIEW_FILE = "report/performanceStats";

    @Value("${file.upload.folder}")
    protected String fileUploadFolder;

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateStatisticsController.class);

    @Autowired
    private AssetDSU4Repository assetDSU4Repository;

    @Autowired
    private AssetDSU3Repository assetDSU3Repository;

    @GetMapping("/generate_performanceReport")
    public ModelAndView displayScreen() {
        ModelAndView modelAndView = new ModelAndView(VIEW_FILE);
        return modelAndView;
    }

    @PostMapping("/generate_performanceReport")
    public ModelAndView generateReport() {

        ModelAndView modelAndView = new ModelAndView(VIEW_FILE);
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
            String filename = "PerformanceReport_" + dateFormat.format(new Date());
            String filePath = fileUploadFolder + File.separator + filename + ".xlsx";

            try (Workbook workbook = new XSSFWorkbook();
                 FileOutputStream fileOut = new FileOutputStream(filePath)) {

                CreationHelper createHelper = workbook.getCreationHelper();

                // Create a Sheet
                Sheet sheet = workbook.createSheet();



                int i = addReport1(sheet, 0);


                // Resize all columns to fit the content size
                for (int j = 0; j < 3; j++) {
                    sheet.autoSizeColumn(j);
                }

                // Write the output to a file

                workbook.write(fileOut);

            }





             





                modelAndView.addObject("successMessage", "Performance report file created successfully, file: " + filePath);

        } catch (Exception e) {
            LOGGER.error("Error generating report file", e);
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        return modelAndView;
    }

    /*First report : Get top 10 DSU4 values - 'Identifier(Asset Id type)', 'Top10 Fundlevel(Asset name)' and '% Exposure(Eff weight)'
                   by Inst. sub type with composite value */
    private int addReport1(Sheet sheet, int i) {
        List<AssetDSU4> assetDSU4s = assetDSU4Repository.findFirst10ByInstSubTypeOrderByEffWeightDesc("Composite");
        Row headerRow = sheet.createRow(i);
        headerRow.createCell(0).setCellValue("Identifier");
        headerRow.createCell(1).setCellValue("Top10 Fundlevel");
        headerRow.createCell(2).setCellValue("% Exposure");

        // Create cells
        for (AssetDSU4 assetDSU4: assetDSU4s) {
            i+=1;
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(assetDSU4.getAssetIdType());
            row.createCell(1).setCellValue(assetDSU4.getAssetName());
            row.createCell(2).setCellValue(assetDSU4.getEffWeight().doubleValue());

        }
        return i;
    }

}
