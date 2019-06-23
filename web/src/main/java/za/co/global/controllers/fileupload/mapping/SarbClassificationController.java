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
import za.co.global.domain.fileupload.mapping.SARBClassificationMapping;
import za.co.global.persistence.fileupload.mapping.SARBClassificationMappingRepository;
import za.co.global.services.upload.FileAndObjectResolver;

import java.io.IOException;

@Controller
public class SarbClassificationController extends BaseFileUploadController {

    private static final String FILE_TYPE = FileAndObjectResolver.SARB_CLASSIFICATION.getFileType();

    @Autowired
    private SARBClassificationMappingRepository sarbClassificationMappingRepository;


    @GetMapping("/upload_sarbClassification")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/mapping/performance/sarbClassification");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_sarbClassification")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/mapping/performance/sarbClassification", "errorMessage", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (IOException e) {
            return new ModelAndView("fileupload/mapping/performance/sarbClassification", "errorMessage", "Error: "+e.getMessage());
        } catch (Exception e) {
            return new ModelAndView("fileupload/mapping/performance/sarbClassification", "errorMessage", "Error: "+e.getMessage());
        }
        return new ModelAndView("fileupload/mapping/performance/sarbClassification", "successMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    @GetMapping(value = {"/view_sarbClassification"})
    public String viewSarbClassification(Model model) {
        model.addAttribute("sarbClassifications", sarbClassificationMappingRepository.findAll());
        return "fileupload/mapping/performance/view/viewSarbClassification";

    }

    @Override
    protected void processObject(Object obj) {
        if(obj instanceof SARBClassificationMapping) {
            SARBClassificationMapping sarbClassification = getSarbClassification(obj);
            sarbClassificationMappingRepository.save(sarbClassification);
        }
    }

    private SARBClassificationMapping getSarbClassification(Object object) {
        SARBClassificationMapping sarbClassification = (SARBClassificationMapping) object;
        SARBClassificationMapping existingSarbClassificationMapping= sarbClassificationMappingRepository.findBySarbClassification(sarbClassification.getSarbClassification());
        if(existingSarbClassificationMapping == null) {
           return sarbClassification;
        }

        existingSarbClassificationMapping.setSarbClassification(sarbClassification.getSarbClassification());
        existingSarbClassificationMapping.setAssetClass(sarbClassification.getAssetClass());
        return existingSarbClassificationMapping;
    }

}
