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
import java.math.BigDecimal;
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
        return new ModelAndView(VIEW_FILE);
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

                workbook.getCreationHelper();

                // Create a Sheet
                Sheet sheet = workbook.createSheet();

                CellStyle percentageCellStyle = workbook.createCellStyle();
                percentageCellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));

                // Create a Font for styling header cells
                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerFont.setFontHeightInPoints((short) 14);
//            headerFont.setColor(IndexedColors.RED.getIndex());

                // Create a CellStyle with the font
                CellStyle headerCellStyle = workbook.createCellStyle();
                headerCellStyle.setFont(headerFont);

                int i = 0;
                i = addReport1(sheet, i, headerCellStyle, percentageCellStyle);//DSU4- Top 10 By InstSubType with 'Composite' value
                i = addReport2(sheet, i, headerCellStyle, percentageCellStyle);//DSU3 - Top 10 By InstSubType with 'Equity Security' value
                i = addReport3(sheet, i, headerCellStyle, percentageCellStyle);//DSU3 - Top 10 By InstSubType with all value
                i = addReport4(sheet, i, headerCellStyle, percentageCellStyle);//DSU3 - Group By IcbIndustry and summing up eff weight and also displaying 'Excluding N/A'
                i = addReport5(sheet, i, headerCellStyle, percentageCellStyle);//DSU3 - Group By LocalMarket and summing up eff weight. Displaying first 10 and  displaying remaining values by summing up eff weight as Other
                i = addReport6(sheet, i, headerCellStyle, percentageCellStyle);//DSU3 - Group By IcbSuperSector and summing up eff weight
                addReportForSARBClassificationAndAssetClass(sheet, i, headerCellStyle, percentageCellStyle);//DSU3 - Group By SARB classification, summing up eff weight and mapping to AssetClass

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
    private int addReport1(Sheet sheet, int i, CellStyle headerCellStyle, CellStyle percentageCellStyle) {
        Row headerRow = sheet.createRow(i); //Header row
        Cell cell0 = headerRow.createCell(0);
        cell0.setCellValue("Identifier");
        cell0.setCellStyle(headerCellStyle);

        Cell cell1 = headerRow.createCell(1);
        cell1.setCellValue("Top10 Fundlevel");
        cell1.setCellStyle(headerCellStyle);

        Cell cell2 = headerRow.createCell(2);
        cell2.setCellValue("% Exposure");
        cell2.setCellStyle(headerCellStyle);


        String sumString = null;

        // Data rows
        List<AssetDSU4> assetDSU4s = assetDSU4Repository.findFirst10ByNetIndicatorIsFalseAndInstSubTypeOrderByEffWeightDesc("Composite");
        for (AssetDSU4 assetDSU4 : assetDSU4s) {
            i += 1;
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(assetDSU4.getAssetIdType());
            row.createCell(1).setCellValue(assetDSU4.getAssetName());

            Cell effWeightCell = row.createCell(2);
            effWeightCell.setCellValue(assetDSU4.getEffWeight().doubleValue());
            effWeightCell.setCellStyle(percentageCellStyle);

            CellReference cr = new CellReference(effWeightCell);
            sumString = sumString == null ? cr.formatAsString() : (sumString + "," + cr.formatAsString());
        }

        i += 1;
        Row totalRow = sheet.createRow(i); //Total eff weight
        Cell totalCell = totalRow.createCell(2);
        totalCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCell.setCellFormula("SUM(" + sumString + ")");
        totalCell.setCellStyle(percentageCellStyle);

        return i;
    }

    /*Second report : Get top 10 DSU3 values - 'Identifier(InstSubType)', 'Top10 Equity(Asset name)' and '% Exposure(Eff weight)'
                   by Inst. sub type with 'Equity Security' value */
    private int addReport2(Sheet sheet, int i, CellStyle headerCellStyle, CellStyle percentageCellStyle) {
        i += 1;
        sheet.createRow(i); //Empty row

        i += 1;
        Row headerRow = sheet.createRow(i); //Header row
        headerRow.createCell(0).setCellValue("Identifier");
        headerRow.createCell(1).setCellValue("Top10 Equity");
        headerRow.createCell(2).setCellValue("% Exposure");

        String sumString = null;

        //Data rows
        List<AssetDSU3> assetDSU3s = assetDSU3Repository.findFirst10ByNetIndicatorIsFalseAndInstSubTypeOrderByEffWeightDesc("Equity Security");
        for (AssetDSU3 assetDSU3 : assetDSU3s) {
            i += 1;
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(assetDSU3.getInstSubType());
            row.createCell(1).setCellValue(assetDSU3.getAssetName());

            Cell effWeightCell = row.createCell(2);
            effWeightCell.setCellValue(assetDSU3.getEffWeight().doubleValue());
            CellReference cr = new CellReference(effWeightCell);
            sumString = sumString == null ? cr.formatAsString() : (sumString + "," + cr.formatAsString());
        }

        i += 1;
        Row totalRow = sheet.createRow(i); //Total eff weight
        Cell totalCell = totalRow.createCell(2);
        totalCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCell.setCellFormula("SUM(" + sumString + ")");
        return i;
    }

    /*3rd report : Get top 10 DSU3 values - 'Identifier(InstSubType)', 'Top10 ALL(Asset name)' and '% Exposure(Eff weight)'
                   by Inst. sub type irrespective of specific value*/
    private int addReport3(Sheet sheet, int i, CellStyle headerCellStyle, CellStyle percentageCellStyle) {
        i += 1;
        sheet.createRow(i); //Empty row

        i += 1;
        Row headerRow = sheet.createRow(i); //Header row
        headerRow.createCell(0).setCellValue("Identifier");
        headerRow.createCell(1).setCellValue("Top10 ALL");
        headerRow.createCell(2).setCellValue("% Exposure");

        String sumString = null;

        //Data rows
        List<AssetDSU3> assetDSU3s = assetDSU3Repository.findFirst10ByNetIndicatorIsFalseOrderByEffWeightDesc();
        for (AssetDSU3 assetDSU3 : assetDSU3s) {
            i += 1;
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(assetDSU3.getInstSubType());
            row.createCell(1).setCellValue(assetDSU3.getAssetName());

            Cell effWeightCell = row.createCell(2);
            effWeightCell.setCellValue(assetDSU3.getEffWeight().doubleValue());
            CellReference cr = new CellReference(effWeightCell);
            sumString = sumString == null ? cr.formatAsString() : (sumString + "," + cr.formatAsString());
        }

        i += 1;
        Row totalRow = sheet.createRow(i); //Total eff weight
        Cell totalCell = totalRow.createCell(2);
        totalCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCell.setCellFormula("SUM(" + sumString + ")");

        return i;
    }

    /*4th report : Get DSU3 values - 'ICB Industry(IcbIndustry)' and 'Exposure(Sum of Eff weight)' group by IcbIndustry*/
    private int addReport4(Sheet sheet, int i, CellStyle headerCellStyle, CellStyle percentageCellStyle) {
        i += 1;
        sheet.createRow(i); //Empty row

        i += 1;
        Row headerRow = sheet.createRow(i); //Header row
        headerRow.createCell(0).setCellValue("ICB Industry");
        headerRow.createCell(1).setCellValue("Exposure");

        String sumString = null;
        String sumExcludingNAString = null;

        //Data rows
        List<Object[]> assetDSU3s = assetDSU3Repository.findEffWeightSumGroupByIcbIndustry();
        for (Object[] objects : assetDSU3s) {
            i += 1;
            Row row = sheet.createRow(i);
            String icbIndustry = objects[0] != null ? (String) objects[0] : "N/A";
            row.createCell(0).setCellValue(icbIndustry);

            Cell effWeightCell = row.createCell(1);
            BigDecimal effWeight = objects[1] != null ? (BigDecimal) objects[1] : BigDecimal.ZERO;
            effWeightCell.setCellValue(effWeight.doubleValue());
            CellReference cr = new CellReference(effWeightCell);
            sumString = sumString == null ? cr.formatAsString() : (sumString + "," + cr.formatAsString());
            if (!"N/A".equalsIgnoreCase(icbIndustry)) {
                sumExcludingNAString = sumExcludingNAString == null ? cr.formatAsString() : (sumExcludingNAString + "," + cr.formatAsString());
            }
        }

        i += 1;
        Row totalRow = sheet.createRow(i); //Total eff weight
        Cell totalCell = totalRow.createCell(1);
        totalCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCell.setCellFormula("SUM(" + sumString + ")");

        i += 1;
        sheet.createRow(i); //Empty row

        i += 1;
        Row excludingRow = sheet.createRow(i); //Total eff weight excluding 'N/A'
        excludingRow.createCell(0).setCellValue("Excluding \"N/A\"");
        Cell totalCellExcludingNA = excludingRow.createCell(1);
        totalCellExcludingNA.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCellExcludingNA.setCellFormula("SUM(" + sumExcludingNAString + ")");

        return i;
    }

    /*5th report : Get DSU3 values - 'Local Market(Local Market)' and 'Exposure(Sum of Eff weight)' group by LocalMarket
     Displaying top 10 and remaining grouping as Other */
    private int addReport5(Sheet sheet, int i, CellStyle headerCellStyle, CellStyle percentageCellStyle) {
        i += 1;
        sheet.createRow(i); //Empty row

        i += 1;
        Row headerRow = sheet.createRow(i); //Header row
        headerRow.createCell(0).setCellValue("Local Market");
        headerRow.createCell(1).setCellValue("Exposure");

        String sumFormula = null;
        BigDecimal sumOfOtherEffWeight = BigDecimal.ZERO;

        //Data rows
        List<Object[]> assetDSU3s = assetDSU3Repository.findEffWeightSumGroupByLocalMarket();
        int j = 0;
        for (Object[] objects : assetDSU3s) {
            j += 1;
            BigDecimal effWeight = objects[1] != null ? (BigDecimal) objects[1] : BigDecimal.ZERO;
            if (j > 10) {
                sumOfOtherEffWeight = sumOfOtherEffWeight.add(effWeight);
            } else {
                i += 1;
                Row row = sheet.createRow(i);
                String localMarket = objects[0] != null ? (String) objects[0] : "";
                row.createCell(0).setCellValue(localMarket);

                Cell effWeightCell = row.createCell(1);
                effWeightCell.setCellValue(effWeight.doubleValue());
                CellReference cr = new CellReference(effWeightCell);
                sumFormula = sumFormula == null ? cr.formatAsString() : (sumFormula + "," + cr.formatAsString());
            }
        }

        i += 1;
        Row otherRow = sheet.createRow(i); //Other column
        otherRow.createCell(0).setCellValue("Other");
        otherRow.createCell(1).setCellValue(sumOfOtherEffWeight.doubleValue());


        i += 1;
        Row totalRow = sheet.createRow(i); //Total eff weight
        Cell totalCell = totalRow.createCell(1);
        totalCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCell.setCellFormula("SUM(" + sumFormula + ")");

        return i;
    }


    /*6th Report: Group by 'ICB Supersector' and summing up eff weight */
    private int addReport6(Sheet sheet, int i, CellStyle headerCellStyle, CellStyle percentageCellStyle) {
        i += 1;
        sheet.createRow(i); //Empty row

        i += 1;
        Row headerRow = sheet.createRow(i); //Header row
        headerRow.createCell(0).setCellValue("ICB Supersector");
        headerRow.createCell(1).setCellValue("Exposure");

        String sumFormula = null;

        //Data rows
        List<Object[]> assetDSU3s = assetDSU3Repository.findEffWeightSumGroupByIcbSuperSector();
        for (Object[] objects : assetDSU3s) {
            i += 1;
            Row row = sheet.createRow(i);
            String icbIndustry = objects[0] != null ? (String) objects[0] : "N/A";
            row.createCell(0).setCellValue(icbIndustry);

            Cell effWeightCell = row.createCell(1);
            BigDecimal effWeight = objects[1] != null ? (BigDecimal) objects[1] : BigDecimal.ZERO;
            effWeightCell.setCellValue(effWeight.doubleValue());
            CellReference cr = new CellReference(effWeightCell);
            sumFormula = sumFormula == null ? cr.formatAsString() : (sumFormula + "," + cr.formatAsString());
        }

        i += 1;
        Row totalRow = sheet.createRow(i); //Total eff weight
        Cell totalCell = totalRow.createCell(1);
        totalCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCell.setCellFormula("SUM(" + sumFormula + ")");

        return i;
    }

    /*7th report: Group by 'SARB Classification', summing up eff weight and mapping to AssetClass
    * 8th Report: Group by AssetClass values and summing up eff weight
    * 9th Report: Simplifying 8th report, i.e. taking out 'zero' values of eff weight*/
    private int addReportForSARBClassificationAndAssetClass(Sheet sheet, int i, CellStyle headerCellStyle, CellStyle percentageCellStyle) {
        i += 1;
        sheet.createRow(i); //Empty row

        Map<String, BigDecimal> assetClassWithEffWeightMap = new HashMap<>();
        Map<String, String> sarbClassificationMappingMap = new HashMap<>();
        List<SARBClassificationMapping> sarbClassificationMappings = sarbClassificationMappingRepository.findAll();
        sarbClassificationMappings.forEach(sarbClassificationMapping ->
                sarbClassificationMappingMap.put(sarbClassificationMapping.getSarbClassification(), sarbClassificationMapping.getAssetClass()));

        i += 1;
        Row headerRow = sheet.createRow(i); //Header row
        headerRow.createCell(0).setCellValue("SARB Classification");
        headerRow.createCell(1).setCellValue("Asset Class");
        headerRow.createCell(2).setCellValue("Exposure");

        String sumFormula = null;

        //Data rows
        List<Object[]> assetDSU3s = assetDSU3Repository.findEffWeightSumGroupBySARBClassification();
        for (Object[] objects : assetDSU3s) {
            i += 1;
            Row row = sheet.createRow(i);
            String sarbClassification = objects[0] != null ? (String) objects[0] : "";
            row.createCell(0).setCellValue(sarbClassification);
            String assetClass = sarbClassificationMappingMap.get(sarbClassification) == null ? "" : sarbClassificationMappingMap.get(sarbClassification);
            row.createCell(1).setCellValue(assetClass);

            Cell effWeightCell = row.createCell(2);
            BigDecimal effWeightSum = objects[1] != null ? (BigDecimal) objects[1] : BigDecimal.ZERO;
            effWeightCell.setCellValue(effWeightSum.doubleValue());
            CellReference cr = new CellReference(effWeightCell);
            sumFormula = sumFormula == null ? cr.formatAsString() : (sumFormula + "," + cr.formatAsString());

            if (!StringUtils.isEmpty(assetClass)) {
                BigDecimal existingEffWeightSum = assetClassWithEffWeightMap.get(assetClass);
                if (existingEffWeightSum != null) {
                    existingEffWeightSum = existingEffWeightSum.add(effWeightSum);
                } else {
                    existingEffWeightSum = effWeightSum;
                }
                assetClassWithEffWeightMap.put(assetClass, existingEffWeightSum);
            }
        }

        i += 1;
        Row totalRow = sheet.createRow(i); //Total eff weight
        Cell totalCell = totalRow.createCell(2);
        totalCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalCell.setCellFormula("SUM(" + sumFormula + ")");

        //Asset class report
        i += 1;
        sheet.createRow(i); //Empty row

        i += 1;
        Row assetClassheaderRow = sheet.createRow(i); //Header row
        assetClassheaderRow.createCell(0).setCellValue("Asset Class");
        assetClassheaderRow.createCell(1).setCellValue("Exposure");

        String assetClassSumFormula = null;

        for (Map.Entry<String, BigDecimal> assetClassWithEffWeightEntry : assetClassWithEffWeightMap.entrySet()) {

            i += 1;
            Row row = sheet.createRow(i);
            String assetClass = assetClassWithEffWeightEntry.getKey();
            row.createCell(0).setCellValue(assetClass);

            Cell effWeightCell = row.createCell(1);
            BigDecimal effWeightSum = assetClassWithEffWeightEntry.getValue();
            effWeightCell.setCellValue(effWeightSum.doubleValue());
            CellReference cr = new CellReference(effWeightCell);
            assetClassSumFormula = assetClassSumFormula == null ? cr.formatAsString() : (assetClassSumFormula + "," + cr.formatAsString());

        }

        i += 1;
        Row totalAssetClassRow = sheet.createRow(i); //Total eff weight
        Cell totalAssetClassCell = totalAssetClassRow.createCell(1);
        totalAssetClassCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalAssetClassCell.setCellFormula("SUM(" + assetClassSumFormula + ")");


        //Simplified Asset class report
        i += 1;
        sheet.createRow(i); //Empty row

        i += 1;
        Row simplifiedAssetClassheaderRow = sheet.createRow(i); //Header row
        simplifiedAssetClassheaderRow.createCell(0).setCellValue("Asset Class");
        simplifiedAssetClassheaderRow.createCell(1).setCellValue("Exposure");

        String simplifiedAssetClassSumFormula = null;

        for (Map.Entry<String, BigDecimal> assetClassWithEffWeightEntry : assetClassWithEffWeightMap.entrySet()) {
            BigDecimal effWeightSum = assetClassWithEffWeightEntry.getValue();
            if (effWeightSum.compareTo(BigDecimal.ZERO) != 0) {
                i += 1;
                Row row = sheet.createRow(i);
                String assetClass = assetClassWithEffWeightEntry.getKey();
                row.createCell(0).setCellValue(assetClass);

                Cell effWeightCell = row.createCell(1);
                effWeightCell.setCellValue(effWeightSum.doubleValue());
                CellReference cr = new CellReference(effWeightCell);
                simplifiedAssetClassSumFormula = simplifiedAssetClassSumFormula == null ? cr.formatAsString() : (simplifiedAssetClassSumFormula + "," + cr.formatAsString());
            }
        }

        i += 1;
        Row totalSimplifiedAssetClassRow = sheet.createRow(i); //Total eff weight
        Cell totalSimplifiedAssetClassCell = totalSimplifiedAssetClassRow.createCell(1);
        totalSimplifiedAssetClassCell.setCellType(Cell.CELL_TYPE_FORMULA);
        totalSimplifiedAssetClassCell.setCellFormula("SUM(" + simplifiedAssetClassSumFormula + ")");

        return i;
    }

}
