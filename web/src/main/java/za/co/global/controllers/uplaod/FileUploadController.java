package za.co.global.controllers.uplaod;

import com.gizbel.excel.enums.ExcelFactoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.global.domain.upload.DSU5_GIRREP4;
import za.co.global.services.upload.GirsaExcelParser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class FileUploadController {

    @PersistenceContext
    private EntityManager entityManager;


    //destination folder to upload the files
    private static String UPLOAD_FOLDER = "C://temp//";

    @GetMapping("/upload_file")
    public ModelAndView showUpload() {
        return new ModelAndView("upload/uploadFile");
    }

    @PostMapping("/upload")
    @Transactional
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            return new ModelAndView("upload/status", "message", "Please select a file and try again");
        }

        try {
            // read and write the file to the selected location-
            byte[] bytes = file.getBytes();
            File uploadDirectory = new File(UPLOAD_FOLDER);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdir();
            }
            Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            readFileAndStoreInDB(path);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("upload/status", "message", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    private void readFileAndStoreInDB(Path path) throws Exception {
        GirsaExcelParser parser = new GirsaExcelParser(ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION);
        Map<String, List<Object>> result = parser.parse(new File(path.toString())); //Whatever excel file you want
        Set<Map.Entry<String, List<Object>>> entries = result.entrySet();
        for (Map.Entry<String, List<Object>> map : entries) {
            String key = map.getKey();
            System.out.println("---" + key);
            List<Object> value = map.getValue();
            for (Object obj : value) {
                if (key.equals("DSU5_GIRREP4")) {
                    DSU5_GIRREP4 ex = (DSU5_GIRREP4) obj;
                    System.out.println("col1: " + ex.getHoldings());
                    entityManager.persist(ex);
                }
            }
        }
    }
}
