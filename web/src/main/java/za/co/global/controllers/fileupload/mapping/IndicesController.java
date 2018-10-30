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
import za.co.global.domain.fileupload.mapping.Indices;
import za.co.global.persistence.fileupload.mapping.IndicesRepository;
import za.co.global.services.upload.FileAndObjectResolver;

import java.io.IOException;

@Controller
public class IndicesController extends BaseFileUploadController {

    private static final String FILE_TYPE = FileAndObjectResolver.INDICES.getFileType();

    @Autowired
    private IndicesRepository indicesRepository;


    @GetMapping("/upload_indices")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/mapping/indices");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_indices")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/mapping/indices", "saveError", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (IOException e) {
            return new ModelAndView("fileupload/mapping/indices", "saveError", e.getMessage());
        } catch (Exception e) {
            return new ModelAndView("fileupload/mapping/indices", "saveError", e.getMessage());
        }
        return new ModelAndView("fileupload/mapping/indices", "saveMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    @GetMapping(value = {"/view_indices"})
    public String viewIndices(Model model) {
        model.addAttribute("indices", indicesRepository.findAll());
        return "fileupload/mapping/view/viewIndices";

    }

    @Override
    protected void processObject(Object obj) {
        if(obj instanceof Indices) {
            Indices ex = (Indices) obj;
            indicesRepository.save(ex);
        }
    }

}
