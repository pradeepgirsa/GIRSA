package za.co.global.controllers.fileupload.mapping;

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
import za.co.global.domain.fileupload.client.DailyPricing;
import za.co.global.persistence.fileupload.client.DailyPricingRepository;
import za.co.global.services.upload.FileAndObjectResolver;

import java.io.IOException;

@Controller
public class DailyPricingMappingController extends BaseFileUploadController {


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
            return new ModelAndView("fileupload/mapping/dailyPricing", "errorMessage", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (IOException e) {
            e.printStackTrace();
            return new ModelAndView("fileupload/mapping/dailyPricing", "errorMessage", "Error: "+e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("fileupload/mapping/dailyPricing", "errorMessage", "Error: "+e.getMessage());
        }
        return new ModelAndView("fileupload/mapping/dailyPricing", "successMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
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

//    private static void readFileAndStoreInDB(File file, String fileType) throws Exception {
//        GirsaExcelParser parser = new GirsaExcelParser(ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION);
//        Map<String, List<Object>> result = parser.parse(file, fileType); //Whatever excel file you want
//        Set<Map.Entry<String, List<Object>>> entries = result.entrySet();
//        for (Map.Entry<String, List<Object>> map : entries) {
//            //  String sheetName = map.getKey();
//            List<Object> value = map.getValue();
//            for (Object obj : value) {
//                if (obj instanceof DailyPricing) {
////                    DailyPricing dailyPricing = getDailyPricing(obj);
////                    dailyPricing.setType(sheetName);
////                    dailyPricingRepository.save(dailyPricing);
//                }
//            }
//        }
//    }


    @Override
    protected void processObject(Object obj) {
        if(obj instanceof DailyPricing) {
            DailyPricing dailyPricing = getDailyPricing(obj);
            dailyPricingRepository.save(dailyPricing);
        }
    }

    private DailyPricing getDailyPricing(Object obj) {
        DailyPricing dailyPricing = (DailyPricing) obj;
        DailyPricing existingDailyPricing = dailyPricingRepository.findByBondCode(dailyPricing.getBondCode());
        if(existingDailyPricing == null) {
            return dailyPricing;
        }
        existingDailyPricing.setBondCode(dailyPricing.getBondCode());
        existingDailyPricing.setCoupon(dailyPricing.getCoupon());
        existingDailyPricing.setCurrentYield(dailyPricing.getCurrentYield());
        existingDailyPricing.setFeatures(dailyPricing.getFeatures());
        existingDailyPricing.setFitch(dailyPricing.getFitch());
        existingDailyPricing.setGlobal(dailyPricing.getGlobal());
        existingDailyPricing.setGoviBenchmark(dailyPricing.getGoviBenchmark());
        existingDailyPricing.setIssueDate(dailyPricing.getIssueDate());
        existingDailyPricing.setIssuer(dailyPricing.getIssuer());
        existingDailyPricing.setIssueSize(dailyPricing.getIssueSize());
        existingDailyPricing.setLastMTMChangeDate(dailyPricing.getLastMTMChangeDate());
        existingDailyPricing.setLiquidityNominalTraded(dailyPricing.getLiquidityNominalTraded());
        existingDailyPricing.setLastTradedDate(dailyPricing.getLastTradedDate());
        existingDailyPricing.setLiquidityNoOfTrade(dailyPricing.getLiquidityNoOfTrade());
        existingDailyPricing.setMaturityOrCallDate(dailyPricing.getMaturityOrCallDate());
        existingDailyPricing.setMoodys(dailyPricing.getMoodys());
        existingDailyPricing.setSbrFairValue(dailyPricing.getSbrFairValue());
        existingDailyPricing.setSpreadAtIssue(dailyPricing.getSpreadAtIssue());
        existingDailyPricing.setSpreadToGovi(dailyPricing.getSpreadToGovi());
        existingDailyPricing.setSpreadToGovi1DayChange(dailyPricing.getSpreadToGovi1DayChange());
        existingDailyPricing.setSpreadToJibarOrASW(dailyPricing.getSpreadToJibarOrASW());
        existingDailyPricing.setSpreadToJibarOrASW1DayChange(dailyPricing.getSpreadToJibarOrASW1DayChange());
        existingDailyPricing.setStandardAndPoor(dailyPricing.getStandardAndPoor());
        existingDailyPricing.setSummaryRating(dailyPricing.getSummaryRating());
        existingDailyPricing.setType(dailyPricing.getType());
        return existingDailyPricing;
    }


}
