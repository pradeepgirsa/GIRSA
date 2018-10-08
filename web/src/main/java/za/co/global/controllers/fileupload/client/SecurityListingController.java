package za.co.global.controllers.fileupload.client;

import com.gizbel.excel.enums.ExcelFactoryType;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.FileDetails;
import za.co.global.domain.fileupload.client.SecurityListing;
import za.co.global.domain.product.Product;
import za.co.global.persistence.fileupload.FileDetailsRepository;
import za.co.global.persistence.fileupload.client.SecurityListingRepository;
import za.co.global.services.helper.FileUtil;
import za.co.global.services.upload.FileAndObjectResolver;
import za.co.global.services.upload.GirsaExcelParser;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class SecurityListingController {

    public static final String FILE_TYPE = FileAndObjectResolver.ADDITIONAL_CLASSIFICATION.getFileType();

    @Value("${file.upload.folder}")
    private String fileUploadFolder;

    @Autowired
    private SecurityListingRepository securityListingRepository;

    @Autowired
    private FileDetailsRepository fileDetailsRepository;

    @GetMapping("/upload_securityListing")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/client/securityListing");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_securityListing")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file, String productId, String clientId) {
        if (file.isEmpty()) {
            return new ModelAndView("upload/status", "message", "Please select a file and try again");
        }

        try {
            // read and write the file to the selected location-
            byte[] bytes = file.getBytes();

            //store the uploaded file in specified directory and also persist in database
            File uploadedFile = storeFileDetails(file, FILE_TYPE, bytes);

            FileDetails fileDetails = saveFileDetails(null, null, null, uploadedFile);

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if ("xls".equals(extension) || "xlsx".equals(extension)) {
                readFileAndStoreInDB(uploadedFile);
            } else if ("zip".equals(extension)) {
                String unzipFolderName = uploadedFile.getParent() + File.separator + FilenameUtils.removeExtension(uploadedFile.getName());
                List<File> extractedFiles = FileUtil.unZipIt(uploadedFile, unzipFolderName);
                for (File extractedFile : extractedFiles) {

                    saveFileDetails(null, null, fileDetails, extractedFile);

                    readFileAndStoreInDB(extractedFile);
                }
            } else {
                System.out.println("some other format");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("upload/status", "message", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    private FileDetails saveFileDetails(Client client, Product product, FileDetails parent, File file) {
        FileDetails subFileDetails = new FileDetails();
        subFileDetails.setClient(client);
        subFileDetails.setProduct(product);
        subFileDetails.setFilePath(file.getAbsolutePath());
        subFileDetails.setFileExtension(FilenameUtils.getExtension(file.getName()));
        subFileDetails.setReceivedDate(new Date());
        subFileDetails.setParentFileDetails(parent);
        return fileDetailsRepository.save(subFileDetails);
    }

    private File storeFileDetails(MultipartFile file, String fileType, byte[] bytes) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        String folderWithDate = dateFormat.format(new Date());


        String folderName = String.format("%s%s%s%s%s", fileUploadFolder, File.separator, fileType, File.separator,
                folderWithDate);

        String filename = folderName + File.separator + file.getOriginalFilename();

        File uploadedFile = FileUtil.createFile(filename, bytes);
        return uploadedFile;
    }


    private void readFileAndStoreInDB(File file) throws Exception {
        GirsaExcelParser parser = new GirsaExcelParser(ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION);
        Map<String, List<Object>> result = parser.parse(file, FILE_TYPE); //Whatever excel file you want
        Set<Map.Entry<String, List<Object>>> entries = result.entrySet();
        for (Map.Entry<String, List<Object>> map : entries) {
            String key = map.getKey();
            System.out.println("---" + key);
            List<Object> value = map.getValue();
            for (Object obj : value) {
                if(obj instanceof SecurityListing) {
                    SecurityListing ex = (SecurityListing) obj;
                    securityListingRepository.save(ex);
                }
            }
        }
    }

}
