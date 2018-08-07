package za.co.global.controllers.uplaod;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileUploadController {


    //destination folder to upload the files
    private static String UPLOAD_FOLDER = "C://temp//";

    @GetMapping("/upload_file")
    public ModelAndView showUpload() {
        return new ModelAndView("upload/uploadFile");
    }

    @PostMapping("/upload")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            return new ModelAndView("upload/status", "message", "Please select a file and try again");
        }

        try {
            // read and write the file to the selected location-
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("upload/status", "message", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }
}
