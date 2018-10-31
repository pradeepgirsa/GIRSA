package za.co.global.controllers.fileupload.barra;

import com.gizbel.excel.enums.ExcelFactoryType;
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
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class AssetInfoController extends BaseFileUploadController {

    public static final String FILE_TYPE = FileAndObjectResolver.BARRA_FILE.getFileType();

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
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/system/assetInfo", "saveError", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (IOException e) {
            e.printStackTrace();
            return new ModelAndView("fileupload/system/assetInfo", "saveError", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("fileupload/system/assetInfo", "saveError", e.getMessage());
        }
        return new ModelAndView("fileupload/system/assetInfo", "saveMessage", "File Uploaded successfully... " + file.getOriginalFilename());
    }

    @GetMapping(value = {"/view_assetInfo"})
    public String viewAssetInfo(Model model) {
        model.addAttribute("assetInfoList", barraAssetInfoRepository.findAll());
        return "fileupload/system/viewAssetInfo";

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
                if (obj instanceof BarraAssetInfo) {
                    BarraAssetInfo barraAssetInfo = getAssetInfo(obj);
                    if(!netIndicator) {
                        netIndicator = true;
                        barraAssetInfo.setNetIndicator(Boolean.TRUE.booleanValue());
                    }
                    barraAssetInfoRepository.save(barraAssetInfo);
                }
            }
        }
    }
    
    private BarraAssetInfo getAssetInfo(Object object) {
        BarraAssetInfo barraAssetInfo = (BarraAssetInfo) object;
        BarraAssetInfo existingBarraAssetInfo = barraAssetInfoRepository.findByAssetId(barraAssetInfo.getAssetId());
        if(existingBarraAssetInfo == null) {
            return barraAssetInfo;
        }
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

        return existingBarraAssetInfo;
    }

    @Override
    protected void processObject(Object obj) {       
    }

}
