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
import za.co.global.domain.fileupload.system.AssetDSU3;
import za.co.global.persistence.fileupload.system.AssetDSU3Repository;
import za.co.global.services.upload.FileAndObjectResolver;
import za.co.global.services.upload.GirsaExcelParser;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class BarraDSU3Controller extends BaseFileUploadController {

    public static final String FILE_TYPE = FileAndObjectResolver.BARRA_DSU3.getFileType();

    private final static Logger LOGGER = LoggerFactory.getLogger(BarraDSU3Controller.class);

    @Autowired
    private AssetDSU3Repository assetDSU3Repository;

    @GetMapping("/upload_barra_dsu3")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/system/dsu3");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_barra_dsu3")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        LOGGER.debug("Uploading barra dsu3 info...");
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/system/dsu3", "errorMessage", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (Exception e) {
            LOGGER.error("Error", e);
            return new ModelAndView("fileupload/system/dsu3", "errorMessage", e.getMessage());
        }
        return new ModelAndView("fileupload/system/dsu3", "successMessage", "Barra DSU3 File Uploaded successfully... " + file.getOriginalFilename());

    }

    @GetMapping(value = {"/view_barra_dsu3"})
    public String viewAssetDsu3(Model model) {
        try {
            LOGGER.debug("Navigating to view Dsu3...");
            model.addAttribute("assetDSU3List", assetDSU3Repository.findAll());
        } catch (Exception e) {
            LOGGER.error("Error", e);
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "fileupload/system/viewBarraDsu3";

    }

    @GetMapping(value = "/view_dsu3")
    public ModelAndView viewDsu3(@RequestParam(value = "assetId", required = false) String assetId) {
        ModelAndView modelAndView = new ModelAndView("fileupload/system/viewDsu3");
        try {
            LOGGER.debug("Navigating to view Asset data...");
            AssetDSU3 assetDSU3 = assetDSU3Repository.findByAssetId(assetId);
            modelAndView.addObject("assetDSU3", assetDSU3);
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
                if (obj instanceof AssetDSU3) {
                    AssetDSU3 assetDSU3 = getAssetDsu3(obj);
                    if (!netIndicator) {
                        netIndicator = true;
                        assetDSU3.setNetIndicator(Boolean.TRUE.booleanValue());
                    }
                    assetDSU3Repository.save(assetDSU3);
                }
            }
        }
    }

    private AssetDSU3 getAssetDsu3(Object object) {
        AssetDSU3 assetDSU3 = (AssetDSU3) object;
        AssetDSU3 existingAssetDsu3 = assetDSU3Repository.findByAssetId(assetDSU3.getAssetId());
        if (existingAssetDsu3 == null) {
            return assetDSU3;
        }
        existingAssetDsu3.setAssetName(assetDSU3.getAssetName());
        existingAssetDsu3.setAfricaValues(assetDSU3.getAfricaValues());
        existingAssetDsu3.setAmountIssued(assetDSU3.getAmountIssued());
        existingAssetDsu3.setCountryOfExposure(assetDSU3.getCountryOfExposure());
        existingAssetDsu3.setCountryOfIncorporation(assetDSU3.getCountryOfIncorporation());
        existingAssetDsu3.setCountryOfQuotation(assetDSU3.getCountryOfQuotation());
        existingAssetDsu3.setEffExposure(assetDSU3.getEffExposure());
        existingAssetDsu3.setEffWeight(assetDSU3.getEffWeight());
        existingAssetDsu3.setGicsIndustryGroup(assetDSU3.getGicsIndustryGroup());
        existingAssetDsu3.setAmountIssued(assetDSU3.getAmountIssued());
        existingAssetDsu3.setGirIssuer(assetDSU3.getGirIssuer());
        existingAssetDsu3.setHoldings(assetDSU3.getHoldings());
        existingAssetDsu3.setIcbIndustry(assetDSU3.getIcbIndustry());
        existingAssetDsu3.setIcbSuperSector(assetDSU3.getIcbSuperSector());
        existingAssetDsu3.setInstSubType(assetDSU3.getInstSubType());
        existingAssetDsu3.setInstType(assetDSU3.getInstType());
        existingAssetDsu3.setLocalMarket(assetDSU3.getLocalMarket());
        existingAssetDsu3.setMarketCapitalization(assetDSU3.getMarketCapitalization());
        existingAssetDsu3.setMarketValue(assetDSU3.getMarketValue());
        existingAssetDsu3.setMoodyRating(assetDSU3.getMoodyRating());
        existingAssetDsu3.setParentIssuerID(assetDSU3.getParentIssuerID());
        existingAssetDsu3.setPrice(assetDSU3.getPrice());
        existingAssetDsu3.setReg28InstrType(assetDSU3.getReg28InstrType());
        existingAssetDsu3.setSarbClassification(assetDSU3.getSarbClassification());
        existingAssetDsu3.setSubsector(assetDSU3.getSubsector());
        existingAssetDsu3.setTimeToMaturity(assetDSU3.getTimeToMaturity());
        existingAssetDsu3.setIssuerShortName(assetDSU3.getIssuerShortName());

        return existingAssetDsu3;
    }

    @Override
    protected void processObject(Object obj) {
    }

}
