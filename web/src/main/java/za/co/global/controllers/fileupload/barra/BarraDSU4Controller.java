package za.co.global.controllers.fileupload.barra;

import com.gizbel.excel.enums.ExcelFactoryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import za.co.global.domain.fileupload.system.AssetDSU4;
import za.co.global.persistence.fileupload.system.AssetDSU4Repository;
import za.co.global.services.upload.FileAndObjectResolver;
import za.co.global.services.upload.GirsaExcelParser;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class BarraDSU4Controller extends BaseFileUploadController {

    public static final String FILE_TYPE = FileAndObjectResolver.BARRA_DSU4.getFileType();

    private final static Logger LOGGER = LoggerFactory.getLogger(BarraDSU4Controller.class);

    @Autowired
    private AssetDSU4Repository assetDSU4Repository;

    @GetMapping("/upload_barra_dsu4")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/system/dsu4");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_barra_dsu4")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        LOGGER.debug("Uploading barra dsu4 info...");
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/system/dsu4", "errorMessage", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (Exception e) {
            LOGGER.error("Error", e);
            return new ModelAndView("fileupload/system/dsu4", "errorMessage", e.getMessage());
        }
        return new ModelAndView("fileupload/system/dsu4", "successMessage", "Barra DSU4 File Uploaded successfully... " + file.getOriginalFilename());

    }

    @GetMapping(value = {"/view_barra_dsu4"})
    public String viewAssetDsu4(Model model) {
        try {
            LOGGER.debug("Navigating to view Dsu4...");
            model.addAttribute("assetDSU4List", assetDSU4Repository.findAll());
        } catch (Exception e) {
            LOGGER.error("Error", e);
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "fileupload/system/viewBarraDsu4";

    }

    @GetMapping(value = "/view_dsu4")
    public ModelAndView viewDsu4(@RequestParam(value = "assetId", required = false) String assetId) {
        ModelAndView modelAndView = new ModelAndView("fileupload/system/viewDsu4");
        try {
            LOGGER.debug("Navigating to view Asset data...");
            AssetDSU4 assetDSU4 = assetDSU4Repository.findByAssetId(assetId);
            modelAndView.addObject("assetDSU4", assetDSU4);
        } catch (Exception e) {
            LOGGER.error("Error", e);
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        return modelAndView;
    }

    @Override //TODO - check asset info stored correctly or not
    protected void readFileAndStoreInDB(File file, String fileType) throws Exception {
        GirsaExcelParser parser = new GirsaExcelParser(ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION);
        Map<String, List<Object>> result = parser.parse(file, fileType); //Whatever excel file you want
        Set<Map.Entry<String, List<Object>>> entries = result.entrySet();
        for (Map.Entry<String, List<Object>> map : entries) {
            List<Object> value = map.getValue();
            boolean netIndicator = false;
            for (Object obj : value) {
                if (obj instanceof AssetDSU4) {
                    AssetDSU4 assetDSU4 = getAssetDsu4(obj);
                    if (!netIndicator) {
                        netIndicator = true;
                        assetDSU4.setNetIndicator(Boolean.TRUE.booleanValue());
                    }
                    assetDSU4Repository.save(assetDSU4);
                }
            }
        }
    }

    private AssetDSU4 getAssetDsu4(Object object) {
        AssetDSU4 assetDSU4 = (AssetDSU4) object;
        AssetDSU4 existingAssetDsu4 = assetDSU4Repository.findByAssetId(assetDSU4.getAssetId());
        if (existingAssetDsu4 == null) {
            return assetDSU4;
        }
        existingAssetDsu4.setAssetName(assetDSU4.getAssetName());
        existingAssetDsu4.setAfricaValues(assetDSU4.getAfricaValues());
        existingAssetDsu4.setEffExposure(assetDSU4.getEffExposure());
        existingAssetDsu4.setEffWeight(assetDSU4.getEffWeight());
        existingAssetDsu4.setGirIssuer(assetDSU4.getGirIssuer());
        existingAssetDsu4.setHoldings(assetDSU4.getHoldings());
        existingAssetDsu4.setInstSubType(assetDSU4.getInstSubType());
        existingAssetDsu4.setInstType(assetDSU4.getInstType());
        existingAssetDsu4.setLocalMarket(assetDSU4.getLocalMarket());
        existingAssetDsu4.setMarketValue(assetDSU4.getMarketValue());
        existingAssetDsu4.setPrice(assetDSU4.getPrice());
        existingAssetDsu4.setReg28InstrType(assetDSU4.getReg28InstrType());
        existingAssetDsu4.setSarbClassification(assetDSU4.getSarbClassification());

        return existingAssetDsu4;
    }

    @Override
    protected void processObject(Object obj) {
    }

}
