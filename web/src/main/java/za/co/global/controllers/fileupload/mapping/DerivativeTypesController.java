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
import za.co.global.domain.fileupload.mapping.DerivativeType;
import za.co.global.persistence.fileupload.mapping.DerivativeTypesRepository;
import za.co.global.services.upload.FileAndObjectResolver;

import java.io.IOException;

@Controller
public class DerivativeTypesController extends BaseFileUploadController {

    private static final String FILE_TYPE = FileAndObjectResolver.DERIVATIVE_TYPES.getFileType();

    @Autowired
    private DerivativeTypesRepository derivativeTypesRepository;


    @GetMapping("/upload_derivativeTypes")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/mapping/derivativeTypes");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_derivativeTypes")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/mapping/derivativeTypes", "errorMessage", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (IOException e) {
            e.printStackTrace();
            return new ModelAndView("fileupload/mapping/derivativeTypes", "errorMessage", "Error: "+e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("fileupload/mapping/derivativeTypes", "errorMessage", "Error: "+e.getMessage());
        }
        return new ModelAndView("fileupload/mapping/derivativeTypes", "successMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    @GetMapping(value = {"/view_derivative_types"})
    public String viewDerivativeTypes(Model model) {
        model.addAttribute("derivativeTypes", derivativeTypesRepository.findAll());
        return "fileupload/mapping/view/viewDerivativeTypes";

    }

    @Override
    protected void processObject(Object obj) {
        if(obj instanceof DerivativeType) {
            DerivativeType derivativeType = getDerivativeType(obj);
            derivativeTypesRepository.save(derivativeType);
        }
    }

    private DerivativeType getDerivativeType(Object obj) {
        DerivativeType ex = (DerivativeType) obj;
        DerivativeType existingDerivativeType = derivativeTypesRepository.findByType(ex.getType());
        if(existingDerivativeType == null) {
            return ex;
        }
        existingDerivativeType.setForeignClassification(ex.getForeignClassification());
        existingDerivativeType.setLocalClassification(ex.getLocalClassification());
        return existingDerivativeType;
    }

}
