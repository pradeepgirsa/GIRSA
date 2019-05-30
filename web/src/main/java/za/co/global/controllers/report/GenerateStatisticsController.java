package za.co.global.controllers.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.domain.fileupload.mapping.SARBClassificationMapping;
import za.co.global.domain.fileupload.system.AssetDSU3;
import za.co.global.domain.fileupload.system.AssetDSU4;
import za.co.global.persistence.fileupload.mapping.SARBClassificationMappingRepository;
import za.co.global.persistence.fileupload.system.AssetDSU3Repository;
import za.co.global.persistence.fileupload.system.AssetDSU4Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private SARBClassificationMappingRepository sarbClassificationMappingRepository;

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
                i = addReport2(sheet, i);
                i = addReport3(sheet, i);
                i = addReport4(sheet, i);
                i = addReport5(sheet, i);
                i = addReport6(sheet, i);
                addReport(sheet, i);


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
        Row headerRow = sheet.createRow(i); //Header row
        headerRow.createCell(0).setCellValue("Identifier");
        headerRow.createCell(1).setCellValue("Top10 Fundlevel");
        headerRow.createCell(2).setCellValue("% Exposure");

        String sumString = null;

        // Data rows
        List<AssetDSU4> assetDSU4s = assetDSU4Repository.findFirst10ByInstSubTypeOrderByEffWeightDesc("Composite");
        for (AssetDSU4 assetDSU4: assetDSU4s) {
            i+=1;
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(assetDSU4.getAssetIdType());
            row.createCell(1).setCellValue(assetDSU4.getAssetName());

            Cell effWeightCell = row.createCell(2);
            effWeightCell.setCellValue(assetDSU4.getEffWeight().doubleValue());
            CellReference cr = new CellReference(effWeightCell);
            sumString = sumString ==null ? cr.formatAsString() : (sumString + "," + cr.formatAsString());
        }

        i+=1;
        Row totalRow = sheet.createRow(i); //Total eff weight
        Cell totalCell = totalRow.createCell(2);
        totalCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCell.setCellFormula("SUM("+sumString+")");

        return i;
    }


    private int addReport2(Sheet sheet, int i) {
        i+=1;
        sheet.createRow(i); //Empty row

        i+=1;
        Row headerRow = sheet.createRow(i); //Header row
        headerRow.createCell(0).setCellValue("Identifier");
        headerRow.createCell(1).setCellValue("Top10 Equity");
        headerRow.createCell(2).setCellValue("% Exposure");

        String sumString = null;

        //Data rows
        List<AssetDSU3> assetDSU3s = assetDSU3Repository.findFirst10ByInstSubTypeOrderByEffWeightDesc("Equity Security");
        for (AssetDSU3 assetDSU3: assetDSU3s) {
            i+=1;
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(assetDSU3.getInstSubType());
            row.createCell(1).setCellValue(assetDSU3.getAssetName());

            Cell effWeightCell = row.createCell(2);
            effWeightCell.setCellValue(assetDSU3.getEffWeight().doubleValue());
            CellReference cr = new CellReference(effWeightCell);
            sumString = sumString ==null ? cr.formatAsString() : (sumString + "," + cr.formatAsString());

        }

        i+=1;
        Row totalRow = sheet.createRow(i); //Total eff weight
        Cell totalCell = totalRow.createCell(2);
        totalCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCell.setCellFormula("SUM("+sumString+")");
        return i;
    }


    private int addReport3(Sheet sheet, int i) {
        i+=1;
        sheet.createRow(i); //Empty row

        i+=1;
        Row headerRow = sheet.createRow(i); //Header row
        headerRow.createCell(0).setCellValue("Identifier");
        headerRow.createCell(1).setCellValue("Top10 ALL");
        headerRow.createCell(2).setCellValue("% Exposure");

        String sumString = null;

        //Data rows
        List<AssetDSU3> assetDSU3s = assetDSU3Repository.findFirst10ByNetIndicatorIsFalseOrderByEffWeightDesc();
        for (AssetDSU3 assetDSU3: assetDSU3s) {
            i+=1;
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(assetDSU3.getInstSubType());
            row.createCell(1).setCellValue(assetDSU3.getAssetName());
            Cell effWeightCell = row.createCell(2);
            effWeightCell.setCellValue(assetDSU3.getEffWeight().doubleValue());
            CellReference cr = new CellReference(effWeightCell);
            sumString = sumString ==null ? cr.formatAsString() : (sumString + "," + cr.formatAsString());
        }

        i+=1;
        Row totalRow = sheet.createRow(i); //Total eff weight
        Cell totalCell = totalRow.createCell(2);
        totalCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCell.setCellFormula("SUM("+sumString+")");

        return i;
    }

    private int addReport4(Sheet sheet, int i) {
        i+=1;
        sheet.createRow(i); //Empty row

        i+=1;
        Row headerRow = sheet.createRow(i); //Header row
        headerRow.createCell(0).setCellValue("ICB Industry");
        headerRow.createCell(1).setCellValue("Exposure");

        String sumString = null;
        String sumExcludingNAString = null;

        //Data rows
        List<List<Object>> assetDSU3s = assetDSU3Repository.findEffWeightSumGroupByIcbIndustry();
        for (List<Object> list: assetDSU3s) {
            i+=1;
            Row row = sheet.createRow(i);
            String icbIndustry = (String) list.get(0);
            row.createCell(0).setCellValue(icbIndustry);

            Cell effWeightCell = row.createCell(1);
            Double doubleaa = (Double) list.get(1);
            effWeightCell.setCellValue(doubleaa);
            CellReference cr = new CellReference(effWeightCell);
            sumString = sumString ==null ? cr.formatAsString() : (sumString + "," + cr.formatAsString());
            if(!"N/A".equalsIgnoreCase(icbIndustry)) {
                sumExcludingNAString = sumExcludingNAString ==null ? cr.formatAsString() : (sumExcludingNAString + "," + cr.formatAsString());
            }
        }

        i+=1;
        Row totalRow = sheet.createRow(i); //Total eff weight
        Cell totalCell = totalRow.createCell(1);
        totalCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCell.setCellFormula("SUM("+sumString+")");

        i+=1;
        sheet.createRow(i); //Empty row

        i+=1;
        Row excludingRow = sheet.createRow(i); //Total eff weight excluding 'N/A'
        excludingRow.createCell(0).setCellValue("Excluding \"N/A\"");
        Cell totalCellExcludingNA = excludingRow.createCell(1);
        totalCellExcludingNA.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCellExcludingNA.setCellFormula("SUM("+sumExcludingNAString+")");

        return i;
    }


    /*Report: Group by 'Local Market' and summing up eff weight.
    Displaying top 10 and remaining grouping as Other */
    private int addReport5(Sheet sheet, int i) {
        i+=1;
        sheet.createRow(i); //Empty row

        i+=1;
        Row headerRow = sheet.createRow(i); //Header row
        headerRow.createCell(0).setCellValue("Local Market");
        headerRow.createCell(1).setCellValue("Exposure");

        String sumFormula = null;
        Double sumOfOther = 0d;

        //Data rows
        List<List<Object>> assetDSU3s = assetDSU3Repository.findEffWeightSumGroupByLocalMarket();
        int j = 0;
        for (List<Object> list: assetDSU3s) {
            j+=1;
            i+=1;
            if(j > 10) {
                Double doubleaa = (Double) list.get(1);
                sumOfOther = sumOfOther + doubleaa;
            } else {
                Row row = sheet.createRow(i);
                String icbIndustry = (String) list.get(0);
                row.createCell(0).setCellValue(icbIndustry);

                Cell effWeightCell = row.createCell(1);
                Double doubleaa = (Double) list.get(1);
                effWeightCell.setCellValue(doubleaa);
                CellReference cr = new CellReference(effWeightCell);
                sumFormula = sumFormula == null ? cr.formatAsString() : (sumFormula + "," + cr.formatAsString());
            }
        }

        i+=1;
        Row OtherRow = sheet.createRow(i); //Other column
        OtherRow.createCell(0).setCellValue("Other");
        OtherRow.createCell(1).setCellValue(sumOfOther);


        i+=1;
        Row totalRow = sheet.createRow(i); //Total eff weight
        Cell totalCell = totalRow.createCell(1);
        totalCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCell.setCellFormula("SUM("+sumFormula+")");

        return i;
    }


    /*Report: Group by 'ICB Supersector' and summing up eff weight */
    private int addReport6(Sheet sheet, int i) {
        i+=1;
        sheet.createRow(i); //Empty row

        i+=1;
        Row headerRow = sheet.createRow(i); //Header row
        headerRow.createCell(0).setCellValue("ICB Supersector");
        headerRow.createCell(1).setCellValue("Exposure");

        String sumFormula = null;

        //Data rows
        List<List<Object>> assetDSU3s = assetDSU3Repository.findEffWeightSumGroupByIcbSuperSector();
        for (List<Object> list: assetDSU3s) {
            i+=1;
                Row row = sheet.createRow(i);
                String icbIndustry = (String) list.get(0);
                row.createCell(0).setCellValue(icbIndustry);

                Cell effWeightCell = row.createCell(1);
                Double doubleaa = (Double) list.get(1);
                effWeightCell.setCellValue(doubleaa);
                CellReference cr = new CellReference(effWeightCell);
                sumFormula = sumFormula == null ? cr.formatAsString() : (sumFormula + "," + cr.formatAsString());
        }


        i+=1;
        Row totalRow = sheet.createRow(i); //Total eff weight
        Cell totalCell = totalRow.createCell(1);
        totalCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCell.setCellFormula("SUM("+sumFormula+")");

        return i;
    }



    /*Report:  */
    private int addReport(Sheet sheet, int i) {
        i+=1;
        sheet.createRow(i); //Empty row


        Map<String, Double> assetClassWithEffWeightMap = new HashMap<>();
        Map<String, String> sarbClassificationMappingMap = new HashMap<>();
        List<SARBClassificationMapping> sarbClassificationMappings = sarbClassificationMappingRepository.findAll();
        sarbClassificationMappings.forEach(sarbClassificationMapping ->
                sarbClassificationMappingMap.put(sarbClassificationMapping.getSarbClassification(), sarbClassificationMapping.getAssetClass()));

        i+=1;
        Row headerRow = sheet.createRow(i); //Header row
        headerRow.createCell(0).setCellValue("SARB Classification");
        headerRow.createCell(1).setCellValue("Asset Class");
        headerRow.createCell(2).setCellValue("Exposure");

        String sumFormula = null;


        //Data rows
        List<List<Object>> assetDSU3s = assetDSU3Repository.findEffWeightSumGroupBySARBClassification();
        for (List<Object> list: assetDSU3s) {
            i+=1;
            Row row = sheet.createRow(i);
            String sarbClassification = (String) list.get(0);
            row.createCell(0).setCellValue(sarbClassification);
            String assetClass = sarbClassificationMappingMap.get(sarbClassification) == null ? "" : sarbClassificationMappingMap.get(sarbClassification);
            row.createCell(1).setCellValue(assetClass);

            Cell effWeightCell = row.createCell(2);
            Double effWeightSum = (Double) list.get(1);
            effWeightCell.setCellValue(effWeightSum);
            CellReference cr = new CellReference(effWeightCell);
            sumFormula = sumFormula == null ? cr.formatAsString() : (sumFormula + "," + cr.formatAsString());

            if(!StringUtils.isEmpty(assetClass)) {
                Double effWeight = assetClassWithEffWeightMap.get(assetClass);
                if(effWeight != null) {
                    effWeight+= effWeightSum;
                } else {
                    effWeight = effWeightSum;
                }
                assetClassWithEffWeightMap.put(assetClass, effWeight);
            }

        }


        i+=1;
        Row totalRow = sheet.createRow(i); //Total eff weight
        Cell totalCell = totalRow.createCell(2);
        totalCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCell.setCellFormula("SUM("+sumFormula+")");




        //Asset class report

        i+=1;
        sheet.createRow(i); //Empty row

        i+=1;
        Row assetClassheaderRow = sheet.createRow(i); //Header row
        assetClassheaderRow.createCell(0).setCellValue("Asset Class");
        assetClassheaderRow.createCell(1).setCellValue("Exposure");

        String assetClassSumFormula = null;

        for(Map.Entry<String, Double> assetClassWithEffWeightEntry: assetClassWithEffWeightMap.entrySet()) {

            i+=1;
            Row row = sheet.createRow(i);
            String assetClass = assetClassWithEffWeightEntry.getKey();
            row.createCell(0).setCellValue(assetClass);

            Cell effWeightCell = row.createCell(1);
            Double effWeightSum = assetClassWithEffWeightEntry.getValue();
            effWeightCell.setCellValue(effWeightSum);
            CellReference cr = new CellReference(effWeightCell);
            sumFormula = sumFormula == null ? cr.formatAsString() : (sumFormula + "," + cr.formatAsString());

        }

        i+=1;
        Row totalAssetClassRow = sheet.createRow(i); //Total eff weight
        Cell totalAssetClassCell = totalAssetClassRow.createCell(1);
        totalAssetClassCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalAssetClassCell.setCellFormula("SUM("+assetClassSumFormula+")");


        //Simplified Asset class report

        i+=1;
        sheet.createRow(i); //Empty row

        i+=1;
        Row simplifiedAssetClassheaderRow = sheet.createRow(i); //Header row
        simplifiedAssetClassheaderRow.createCell(0).setCellValue("Asset Class");
        simplifiedAssetClassheaderRow.createCell(1).setCellValue("Exposure");

        String simplifiedAssetClassSumFormula = null;

        for(Map.Entry<String, Double> assetClassWithEffWeightEntry: assetClassWithEffWeightMap.entrySet()) {

            Double effWeightSum = assetClassWithEffWeightEntry.getValue();
            if(effWeightSum != 0.0) {
                i += 1;
                Row row = sheet.createRow(i);
                String assetClass = assetClassWithEffWeightEntry.getKey();
                row.createCell(0).setCellValue(assetClass);

                Cell effWeightCell = row.createCell(1);
                effWeightCell.setCellValue(effWeightSum);
                CellReference cr = new CellReference(effWeightCell);
                sumFormula = sumFormula == null ? cr.formatAsString() : (sumFormula + "," + cr.formatAsString());
            }

        }

        i+=1;
        Row totalSimplifiedAssetClassRow = sheet.createRow(i); //Total eff weight
        Cell totalSimplifiedAssetClassCell = totalSimplifiedAssetClassRow.createCell(1);
        totalSimplifiedAssetClassCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalSimplifiedAssetClassCell.setCellFormula("SUM("+simplifiedAssetClassSumFormula+")");


        return i;
    }

}
