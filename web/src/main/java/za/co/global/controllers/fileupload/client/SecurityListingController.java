package za.co.global.controllers.fileupload.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.controllers.fileupload.BaseFileUploadController;
import za.co.global.domain.fileupload.client.SecurityListing;
import za.co.global.persistence.fileupload.client.SecurityListingRepository;
import za.co.global.services.upload.FileAndObjectResolver;

import java.io.IOException;

@Controller
public class SecurityListingController extends BaseFileUploadController {

    public static final String FILE_TYPE = FileAndObjectResolver.SECUTIY_LISTING_CONTROLLER.getFileType();

    @Autowired
    private SecurityListingRepository securityListingRepository;

    @GetMapping("/upload_securityListing")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/client/securityListing");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_securityListing")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/status", "message", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (IOException e) {
            return new ModelAndView("fileupload/status", "message", e.getMessage());
        } catch (Exception e) {
            return new ModelAndView("fileupload/status", "message", e.getMessage());
        }
        return new ModelAndView("fileupload/status", "message", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    @Override
    protected void processObject(Object obj) {
        if (obj instanceof SecurityListing) {
            SecurityListing ex = (SecurityListing) obj;
            securityListingRepository.save(ex);
        }
    }


}
