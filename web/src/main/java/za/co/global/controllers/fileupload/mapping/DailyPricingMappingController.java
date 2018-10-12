package za.co.global.controllers.fileupload.mapping;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import za.co.global.services.helper.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
public class DailyPricingMappingController {

    private static void readAndStoreFundManagerHoldingSheetData(File uploadedFile) throws IOException, InvalidFormatException {

        try(FileInputStream fis = new FileInputStream(uploadedFile);
                XSSFWorkbook workbook = new XSSFWorkbook(fis);) {
            DataFormatter dataFormatter = new DataFormatter();

            Map<String, Integer> headersMap = new HashMap<>();

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                if (sheet.getPhysicalNumberOfRows() > 0) {
                    Iterator<Row> rowIterator = sheet.rowIterator();

                    while (rowIterator.hasNext()) {
                        XSSFRow row = (XSSFRow) rowIterator.next();
                        //If it is empty row skip it
                        if (FileUtil.checkIfRowIsEmpty(row)) {
                            continue;
                        }

                        //Get first row
                        String firstCellValue = dataFormatter.formatCellValue(row.getCell(0));
                        firstCellValue = !StringUtils.isEmpty(firstCellValue) ? firstCellValue.trim() : firstCellValue;
                    }


                } //if - emty sheet checking
            } //for - Sheet iterator
        }
    }

//    public static void main(String[] args) {
//        File file = new File("C:\\Users\\SHARANYAREDDY\\Desktop\\TSR\\GIRSA\\Updated\\c. DailyPricingSpreadsheet 26 June 2018.xlsx");
//        try {
//            readAndStoreFundManagerHoldingSheetData(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidFormatException e) {
//            e.printStackTrace();
//        }
//    }

}
