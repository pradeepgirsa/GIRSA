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
import za.co.global.domain.fileupload.system.BarraAssetInfo;
import za.co.global.persistence.fileupload.system.BarraAssetInfoRepository;
import za.co.global.services.upload.FileAndObjectResolver;
import za.co.global.services.upload.GirsaExcelParser;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class AssetInfoController extends BaseFileUploadController {

    public static final String FILE_TYPE = FileAndObjectResolver.BARRA_FILE.getFileType();

    private final static Logger LOGGER = LoggerFactory.getLogger(AssetInfoController.class);

    @Autowired
    private BarraAssetInfoRepository barraAssetInfoRepository;

    @GetMapping("/upload_assetInfo")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/system/assetInfo");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_assetInfo")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        LOGGER.debug("Uploading barra asset info...");
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/system/assetInfo", "errorMessage", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (Exception e) {
            LOGGER.error("Error", e);
            return new ModelAndView("fileupload/system/assetInfo", "errorMessage", "Error: "+e.getMessage());
        }
        return new ModelAndView("fileupload/system/assetInfo", "successMessage", "File Uploaded successfully... " + file.getOriginalFilename());

    }

    @GetMapping(value = {"/view_assetInfo"})
    public String viewAssetInfo(Model model) {
        try {
            LOGGER.debug("Navigating to view AssetInfo...");
            model.addAttribute("assetInfoList", barraAssetInfoRepository.findAll());
        } catch (Exception e) {
            LOGGER.error("Error", e);
            model.addAttribute("errorMessage", "Error: "+e.getMessage());
        }
        return "fileupload/system/viewAssetInfo";

    }

    @GetMapping(value = "/view_asset")
    public ModelAndView viewAssetData(@RequestParam(value = "assetId", required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView("fileupload/system/asset");
        try {
            LOGGER.debug("Navigating to view Asset data...");
            BarraAssetInfo barraAssetInfo = barraAssetInfoRepository.findOne(id);
            modelAndView.addObject("barraAssetInfo", barraAssetInfo);
        } catch (Exception e) {
            LOGGER.error("Error", e);
            modelAndView.addObject("errorMessage", "Error: "+e.getMessage());
        }
        return modelAndView;
    }

    @Override //TODO - check asset info stored correctly or not
    protected void readFileAndStoreInDB(File file, String fileType) throws Exception {
        GirsaExcelParser parser = new GirsaExcelParser(ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION);
        Map<String, List<Object>> result = parser.parse(file, fileType); //Whatever excel file you want
        Set<Map.Entry<String, List<Object>>> entries = result.entrySet();
        String fundName = getBarraFundName(file.getName());
        for (Map.Entry<String, List<Object>> map : entries) {
            List<Object> value = map.getValue();
            boolean netIndicator = false;
            for (Object obj : value) {
                if (obj instanceof BarraAssetInfo) {
                    BarraAssetInfo barraAssetInfo = getAssetInfo(obj, fundName);
                    if (!netIndicator) {
                        netIndicator = true;
                        barraAssetInfo.setNetIndicator(Boolean.TRUE.booleanValue());
                        BarraAssetInfo netAsset = barraAssetInfoRepository.findByNetIndicatorIsTrueAndFundName(fundName);
                        if(netAsset != null) {
                            convertBarraAssetInfo(barraAssetInfo, netAsset);
                            netAsset.setAssetId(barraAssetInfo.getAssetId());
                            barraAssetInfoRepository.save(netAsset);
                            continue;
                        }
                    }
                    barraAssetInfo.setFundName(fundName);
                    LOGGER.info("Saving barra asset id:{}, fundname:{}", barraAssetInfo.getAssetId(), fundName);
                    barraAssetInfoRepository.save(barraAssetInfo);
                }
            }
        }
    }


    private BarraAssetInfo getAssetInfo(Object object, String fundName) {
        BarraAssetInfo barraAssetInfo = (BarraAssetInfo) object;
        BarraAssetInfo existingBarraAssetInfo = barraAssetInfoRepository.findByAssetIdAndFundName(barraAssetInfo.getAssetId(), fundName);
        if (existingBarraAssetInfo == null) {
            barraAssetInfo.setFundName(fundName);
            return barraAssetInfo;
        }
        convertBarraAssetInfo(barraAssetInfo, existingBarraAssetInfo);

        return existingBarraAssetInfo;
    }

    private void convertBarraAssetInfo(BarraAssetInfo barraAssetInfo, BarraAssetInfo existingBarraAssetInfo) {
        existingBarraAssetInfo.setAssetName(barraAssetInfo.getAssetName());
        existingBarraAssetInfo.setAfricaValues(barraAssetInfo.getAfricaValues());
        existingBarraAssetInfo.setAmountIssued(barraAssetInfo.getAmountIssued());
        existingBarraAssetInfo.setAssetIdType(barraAssetInfo.getAssetIdType());
        existingBarraAssetInfo.setCountryOfExposure(barraAssetInfo.getCountryOfExposure());
        existingBarraAssetInfo.setCountryOfIncorporation(barraAssetInfo.getCountryOfIncorporation());
        existingBarraAssetInfo.setCountryOfQuotation(barraAssetInfo.getCountryOfQuotation());
        existingBarraAssetInfo.setCoupon(barraAssetInfo.getCoupon());
        existingBarraAssetInfo.setCurrentYield(barraAssetInfo.getCurrentYield());
        existingBarraAssetInfo.setEffectiveDuration(barraAssetInfo.getEffectiveDuration());
        existingBarraAssetInfo.setEffExposure(barraAssetInfo.getEffExposure());
        existingBarraAssetInfo.setEffWeight(barraAssetInfo.getEffWeight());
        existingBarraAssetInfo.setFirstCouponDate(barraAssetInfo.getFirstCouponDate());
        existingBarraAssetInfo.setGicsIndustry(barraAssetInfo.getGicsIndustry());
        existingBarraAssetInfo.setGicsIndustryGroup(barraAssetInfo.getGicsIndustryGroup());
        existingBarraAssetInfo.setAmountIssued(barraAssetInfo.getAmountIssued());
        existingBarraAssetInfo.setGicsSector(barraAssetInfo.getGicsSector());
        existingBarraAssetInfo.setGicsSubIndustry(barraAssetInfo.getGicsSubIndustry());
        existingBarraAssetInfo.setGirIndustryICB(barraAssetInfo.getGirIndustryICB());
        existingBarraAssetInfo.setGirIssuer(barraAssetInfo.getGirIssuer());
        existingBarraAssetInfo.setGirSectorICB(barraAssetInfo.getGirSectorICB());
        existingBarraAssetInfo.setGirSubsectorICB(barraAssetInfo.getGirSubsectorICB());
        existingBarraAssetInfo.setGirSupersectorICB(barraAssetInfo.getGirSupersectorICB());
        existingBarraAssetInfo.setHoldings(barraAssetInfo.getHoldings());
        existingBarraAssetInfo.setIcbIndustry(barraAssetInfo.getIcbIndustry());
        existingBarraAssetInfo.setIcbSuperSector(barraAssetInfo.getIcbSuperSector());
        existingBarraAssetInfo.setInstSubType(barraAssetInfo.getInstSubType());
        existingBarraAssetInfo.setInstType(barraAssetInfo.getInstType());
        existingBarraAssetInfo.setIssuer(barraAssetInfo.getIssuer());
        existingBarraAssetInfo.setLocalMarket(barraAssetInfo.getLocalMarket());
        existingBarraAssetInfo.setMacaulayDuration(barraAssetInfo.getMacaulayDuration());
        existingBarraAssetInfo.setMarketCapitalization(barraAssetInfo.getMarketCapitalization());
        existingBarraAssetInfo.setMarketValue(barraAssetInfo.getMarketValue());
        existingBarraAssetInfo.setMaturityDate(barraAssetInfo.getMaturityDate());
        existingBarraAssetInfo.setModifiedDuration(barraAssetInfo.getModifiedDuration());
        existingBarraAssetInfo.setMoodyLongTermIssuerRatingD(barraAssetInfo.getMoodyLongTermIssuerRatingD());
        existingBarraAssetInfo.setMoodyLongTermIssuerRatingF(barraAssetInfo.getMoodyLongTermIssuerRatingF());
        existingBarraAssetInfo.setMoodyRating(barraAssetInfo.getMoodyRating());
        existingBarraAssetInfo.setParentIssuerID(barraAssetInfo.getParentIssuerID());
        existingBarraAssetInfo.setPrice(barraAssetInfo.getPrice());
        existingBarraAssetInfo.setParentIssuerName(barraAssetInfo.getParentIssuerName());
        existingBarraAssetInfo.setPriceCurrency(barraAssetInfo.getPriceCurrency());
        existingBarraAssetInfo.setPricingRedemptionDate(barraAssetInfo.getPricingRedemptionDate());
        existingBarraAssetInfo.setReg28InstrType(barraAssetInfo.getReg28InstrType());
        existingBarraAssetInfo.setReg30InstrType(barraAssetInfo.getReg30InstrType());
        existingBarraAssetInfo.setsAndPLongTermIssuerRatingD(barraAssetInfo.getsAndPLongTermIssuerRatingD());
        existingBarraAssetInfo.setsAndPLongTermIssuerRatingF(barraAssetInfo.getsAndPLongTermIssuerRatingF());
        existingBarraAssetInfo.setSarbClassification(barraAssetInfo.getSarbClassification());
        existingBarraAssetInfo.setSector(barraAssetInfo.getSector());
        existingBarraAssetInfo.setSharesOutstanding(barraAssetInfo.getSharesOutstanding());
        existingBarraAssetInfo.setSubsector(barraAssetInfo.getSubsector());
        existingBarraAssetInfo.setTimeToMaturity(barraAssetInfo.getTimeToMaturity());
        existingBarraAssetInfo.setUltimateIssuerID(barraAssetInfo.getUltimateIssuerID());
        existingBarraAssetInfo.setUltimateIssuerName(barraAssetInfo.getUltimateIssuerName());
        existingBarraAssetInfo.setWeightedAverageLife(barraAssetInfo.getWeightedAverageLife());
    }

    @Override
    protected void processObject(Object obj) {
    }

}
