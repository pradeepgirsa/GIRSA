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
import za.co.global.domain.fileupload.mapping.IssuerMapping;
import za.co.global.persistence.fileupload.mapping.IssuerMappingsRepository;
import za.co.global.services.upload.FileAndObjectResolver;

import java.io.IOException;

@Controller
public class IssuerMappingsController extends BaseFileUploadController {

    private static final String FILE_TYPE = FileAndObjectResolver.ISSUER_MAPPINGS.getFileType();

    @Autowired
    private IssuerMappingsRepository issuerMappingsRepository;


    @GetMapping("/upload_issuerMappings")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/mapping/issuerMappings");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_issuerMappings")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/mapping/issuerMappings", "errorMessage", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (IOException e) {
            return new ModelAndView("fileupload/mapping/issuerMappings", "errorMessage", e.getMessage());
        } catch (Exception e) {
            return new ModelAndView("fileupload/mapping/issuerMappings", "errorMessage", e.getMessage());
        }
        return new ModelAndView("fileupload/mapping/issuerMappings", "successMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    @GetMapping(value = {"/view_issuer_mappings"})
    public String viewIssuerMappings(Model model) {
        model.addAttribute("issuerMappings", issuerMappingsRepository.findAll());
        return "fileupload/mapping/view/viewIssuerMappings";

    }

    @Override
    protected void processObject(Object obj) {
        if(obj instanceof IssuerMapping) {
            IssuerMapping ex = (IssuerMapping) obj;
            issuerMappingsRepository.save(ex);
        }
    }

}
