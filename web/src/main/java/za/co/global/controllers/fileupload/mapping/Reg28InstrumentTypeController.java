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
import za.co.global.domain.fileupload.mapping.Reg28InstrumentType;
import za.co.global.persistence.fileupload.mapping.Reg28InstrumentTypeRepository;
import za.co.global.services.upload.FileAndObjectResolver;

import java.io.IOException;

@Controller
public class Reg28InstrumentTypeController extends BaseFileUploadController {

    private static final String FILE_TYPE = FileAndObjectResolver.REG28_INSTR_TYPE.getFileType();

    @Autowired
    private Reg28InstrumentTypeRepository reg28InstrumentTypeRepository;


    @GetMapping("/upload_reg28InstrumentType")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/mapping/reg28InstrumentType");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_reg28InstrumentType")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/mapping/reg28InstrumentType", "errorMessage", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (IOException e) {
            return new ModelAndView("fileupload/mapping/reg28InstrumentType", "errorMessage", "Error: "+e.getMessage());
        } catch (Exception e) {
            return new ModelAndView("fileupload/mapping/reg28InstrumentType", "errorMessage", "Error: "+e.getMessage());
        }
        return new ModelAndView("fileupload/mapping/reg28InstrumentType", "successMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    @GetMapping(value = {"/view_reg28_instrument_type"})
    public String viewReg28InstrumentType(Model model) {
        model.addAttribute("reg28InstrumentTypes", reg28InstrumentTypeRepository.findAll());
        return "fileupload/mapping/view/viewReg28InstrumentType";

    }

    @Override
    protected void processObject(Object obj) {
        if(obj instanceof Reg28InstrumentType) {
            Reg28InstrumentType reg28InstrumentType = getReg28InstrumentType(obj);
            reg28InstrumentTypeRepository.save(reg28InstrumentType);
        }
    }

    private Reg28InstrumentType getReg28InstrumentType(Object object) {
        Reg28InstrumentType reg28InstrumentType = (Reg28InstrumentType) object;
        Reg28InstrumentType existingReg28InstrType= reg28InstrumentTypeRepository.findByReg28InstrType(reg28InstrumentType.getReg28InstrType());
        if(existingReg28InstrType == null) {
           return reg28InstrumentType;
        }

        existingReg28InstrType.setAciAssetClass(reg28InstrumentType.getAciAssetClass());
        existingReg28InstrType.setAddClassificationOne(reg28InstrumentType.getAddClassificationOne());
        existingReg28InstrType.setAddClassificationThree(reg28InstrumentType.getAddClassificationThree());
        existingReg28InstrType.setAddClassificationTwo(reg28InstrumentType.getAddClassificationTwo());
        existingReg28InstrType.setAsisaDefined2(reg28InstrumentType.getAsisaDefined2());
        existingReg28InstrType.setBnCategories(reg28InstrumentType.getBnCategories());
        existingReg28InstrType.setInstitutionType(reg28InstrumentType.getInstitutionType());
        existingReg28InstrType.setMarketCap(reg28InstrumentType.getMarketCap());
        existingReg28InstrType.setRsaOrForeign(reg28InstrumentType.getRsaOrForeign());
        existingReg28InstrType.setSecurityType(reg28InstrumentType.getSecurityType());
        return existingReg28InstrType;
    }

}
