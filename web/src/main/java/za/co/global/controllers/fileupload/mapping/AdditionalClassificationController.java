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
import za.co.global.domain.fileupload.mapping.AdditionalClassification;
import za.co.global.persistence.fileupload.mapping.AdditionalClassificationRepository;
import za.co.global.services.upload.FileAndObjectResolver;

import java.io.IOException;

@Controller
public class AdditionalClassificationController extends BaseFileUploadController {

    private static final String FILE_TYPE = FileAndObjectResolver.ADDITIONAL_CLASSIFICATION.getFileType();

    @Autowired
    private AdditionalClassificationRepository additionalClassificationRepository;


    @GetMapping("/upload_additionalClassification")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/mapping/additionalClassification");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_additionalClassification")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/mapping/additionalClassification", "saveError", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (IOException e) {
            return new ModelAndView("fileupload/mapping/additionalClassification", "saveError", e.getMessage());
        } catch (Exception e) {
            return new ModelAndView("fileupload/mapping/additionalClassification", "saveError", e.getMessage());
        }
        return new ModelAndView("fileupload/mapping/additionalClassification", "saveMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    @GetMapping(value = {"/view_additional_classification"})
    public String viewAdditionalClassifications(Model model) {
        model.addAttribute("additionalClassifications", additionalClassificationRepository.findAll());
        return "fileupload/mapping/view/viewAdditionalClassification";

    }

    @Override
    protected void processObject(Object obj) {
        if(obj instanceof AdditionalClassification) {
            AdditionalClassification ex = getAdditionalClassification(obj);
            additionalClassificationRepository.save(ex);
        }
    }

    private AdditionalClassification getAdditionalClassification(Object obj) {
        AdditionalClassification ex = (AdditionalClassification) obj;
        AdditionalClassification existing = additionalClassificationRepository.findByAlphaCode(ex.getAlphaCode());
        if(existing == null) {
            return ex;
        }
        existing.setIndustry(ex.getIndustry());
        existing.setSector(ex.getSector());
        existing.setSubSector(ex.getSubSector());
        existing.setSuperSector(ex.getSuperSector());
        return existing;
    }

}
