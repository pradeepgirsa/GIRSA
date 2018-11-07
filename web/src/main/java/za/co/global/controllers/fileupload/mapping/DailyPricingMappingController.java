package za.co.global.controllers.fileupload.mapping;

import com.gizbel.excel.enums.ExcelFactoryType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.controllers.fileupload.BaseFileUploadController;
import za.co.global.domain.fileupload.mapping.DailyPricing;
import za.co.global.persistence.fileupload.mapping.DailyPricingRepository;
import za.co.global.services.helper.FileUtil;
import za.co.global.services.upload.FileAndObjectResolver;
import za.co.global.services.upload.GirsaExcelParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Controller
public class DailyPricingMappingController {


    private static final String FILE_TYPE = FileAndObjectResolver.DAILY_PRICING.getFileType();

    @Autowired
    private DailyPricingRepository dailyPricingRepository;


    @GetMapping("/upload_daily_pricing")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/mapping/dailyPricing");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_daily_pricing")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/mapping/dailyPricing", "saveError", "Please select a file and try again");
        }
        try {
            //processFile(file, FILE_TYPE, null, null);
        //} catch (IOException e) {
            //return new ModelAndView("fileupload/mapping/dailyPricing", "saveError", e.getMessage());
        } catch (Exception e) {
            return new ModelAndView("fileupload/mapping/dailyPricing", "saveError", e.getMessage());
        }
        return new ModelAndView("fileupload/mapping/dailyPricing", "saveMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    @GetMapping(value = {"/view_daily_pricing"})
    public String viewDailyPricing(Model model) {
        model.addAttribute("dailyPrices", dailyPricingRepository.findAll());
        return "fileupload/mapping/view/viewDailyPricing";

    }

    //@Override //TODO - check asset info stored correctly or not
//    protected void readFileAndStoreInDB(File file, String fileType) throws Exception {
//        GirsaExcelParser parser = new GirsaExcelParser(ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION);
//        Map<String, List<Object>> result = parser.parse(file, fileType); //Whatever excel file you want
//        Set<Map.Entry<String, List<Object>>> entries = result.entrySet();
//        for (Map.Entry<String, List<Object>> map : entries) {
//          //  String sheetName = map.getKey();
//            List<Object> value = map.getValue();
//            for (Object obj : value) {
//                if (obj instanceof DailyPricing) {
//                    DailyPricing dailyPricing = getDailyPricing(obj);
////                    dailyPricing.setType(sheetName);
//                    dailyPricingRepository.save(dailyPricing);
//                }
//            }
//        }
//    }

    private static void readFileAndStoreInDB(File file, String fileType) throws Exception {
        GirsaExcelParser parser = new GirsaExcelParser(ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION);
        Map<String, List<Object>> result = parser.parse(file, fileType); //Whatever excel file you want
        Set<Map.Entry<String, List<Object>>> entries = result.entrySet();
        for (Map.Entry<String, List<Object>> map : entries) {
            //  String sheetName = map.getKey();
            List<Object> value = map.getValue();
            for (Object obj : value) {
                if (obj instanceof DailyPricing) {
//                    DailyPricing dailyPricing = getDailyPricing(obj);
//                    dailyPricing.setType(sheetName);
//                    dailyPricingRepository.save(dailyPricing);
                }
            }
        }
    }

    private DailyPricing getDailyPricing(Object object) {
        DailyPricing dailyPricing = (DailyPricing) object;
        /*DailyPricing existingDailyPricing = dailyPricingRepository.findBySecurityAndType(indices.getSecurity(), type);
        if(existingDailyPricing == null) {
            return dailyPricing;
        }
        existingDailyPricing.setAsk(indices.getAsk());
        existingDailyPricing.setBid(indices.getBid());
        existingDailyPricing.setDescription(indices.getDescription());
        existingDailyPricing.setExch(indices.getExch());
        existingDailyPricing.setGicsCode(indices.getGicsCode());
        existingDailyPricing.setIndex(indices.getIndex());
        existingDailyPricing.setIndexPoints(indices.getIndexPoints());
        existingDailyPricing.setIndexPrice(indices.getIndexPrice());
        existingDailyPricing.setIssue(indices.getIssue());
        existingDailyPricing.setIwf(indices.getIwf());
        existingDailyPricing.setLast(indices.getLast());
        existingDailyPricing.setMarketCap(indices.getMarketCap());
        existingDailyPricing.setMarketCapLive(indices.getMarketCapLive());
        existingDailyPricing.setPeRatio(indices.getPeRatio());
        existingDailyPricing.setPositiveOrNegative(indices.getPositiveOrNegative());
        existingDailyPricing.setR(indices.getR());
        existingDailyPricing.setSecurity(indices.getSecurity());
        existingDailyPricing.setSubIndustry(indices.getSubIndustry());
        existingDailyPricing.setYldHist(indices.getYldHist());
*/
        return null;
    }

//    @Override
//    protected void processObject(Object obj) {
//
//    }

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

    public static void main(String[] args) {
        File file = new File("C:\\Users\\SHARANYAREDDY\\Desktop\\TSR\\GIRSA\\Updated\\c. DailyPricingSpreadsheet 26 June 2018.xlsx");
        try {
            readFileAndStoreInDB(file, FILE_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
