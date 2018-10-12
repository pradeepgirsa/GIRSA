package za.co.global.controllers.fileupload.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.controllers.fileupload.BaseFileUploadController;
import za.co.global.domain.fileupload.mapping.PSGFundMapping;
import za.co.global.persistence.fileupload.mapping.PSGFundMappingRepository;
import za.co.global.services.upload.FileAndObjectResolver;

import java.io.IOException;

@Controller
public class PSGFundMappingController extends BaseFileUploadController {

    public static final String FILE_TYPE = FileAndObjectResolver.PSG_FUND_MAPPING.getFileType();

    @Autowired
    private PSGFundMappingRepository psgFundMappingRepository;

    @GetMapping("/upload_psgFundMapping")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/mapping/psgFundMapping");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_psgFundMapping")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/mapping/psgFundMapping", "saveError", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (IOException e) {
            return new ModelAndView("fileupload/mapping/psgFundMapping", "saveError", e.getMessage());
        } catch (Exception e) {
            return new ModelAndView("fileupload/mapping/psgFundMapping", "saveError", e.getMessage());
        }
        return new ModelAndView("fileupload/mapping/psgFundMapping", "saveMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    @Override
    protected void processObject(Object obj) {
        if(obj instanceof PSGFundMapping) {
            PSGFundMapping ex = (PSGFundMapping) obj;
            psgFundMappingRepository.save(ex);
        }
    }

}
