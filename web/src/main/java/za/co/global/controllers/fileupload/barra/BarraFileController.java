package za.co.global.controllers.fileupload.barra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.controllers.fileupload.BaseFileUploadController;
import za.co.global.domain.fileupload.barra.BarraFile;
import za.co.global.persistence.fileupload.barra.BarraFileRepository;
import za.co.global.services.upload.FileAndObjectResolver;

import java.io.IOException;

@Controller
public class BarraFileController extends BaseFileUploadController {

    public static final String FILE_TYPE = FileAndObjectResolver.BARRA_FILE.getFileType();

    @Autowired
    private BarraFileRepository barraFileRepository;

    @GetMapping("/upload_barraFile")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/barra/barraFile");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_barraFile")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/barra/barraFile", "saveError", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (IOException e) {
            e.printStackTrace();
            return new ModelAndView("fileupload/barra/barraFile", "saveError", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("fileupload/barra/barraFile", "saveError", e.getMessage());
        }
        return new ModelAndView("fileupload/barra/barraFile", "saveMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    @Override
    protected void processObject(Object obj) {
        if (obj instanceof BarraFile) {
            BarraFile ex = (BarraFile) obj;
            barraFileRepository .save(ex);
        }
    }

}
