package za.co.global.controllers.fileupload;

import com.gizbel.excel.enums.ExcelFactoryType;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.FileDetails;
import za.co.global.domain.product.Product;
import za.co.global.persistence.fileupload.FileDetailsRepository;
import za.co.global.services.helper.FileUtil;
import za.co.global.services.upload.GirsaExcelParser;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseFileUploadController {

    @Value("${file.upload.folder}")
    protected String fileUploadFolder;

    @Autowired
    private FileDetailsRepository fileDetailsRepository;



    protected void processFile(MultipartFile file, String fileType, Client client, Product product) throws IOException, Exception {
        // read and write the file to the selected location-
        byte[] bytes = file.getBytes();

        //store the uploaded file in specified directory and also persist in database
        File uploadedFile = storeFileDetails(file, fileType, bytes);

        FileDetails fileDetails = saveFileDetails(client, product, null, uploadedFile);

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if ("xls".equals(extension) || "xlsx".equals(extension)) {
            readFileAndStoreInDB(uploadedFile, fileType);
        } else if ("zip".equals(extension)) {
            String unzipFolderName = uploadedFile.getParent() + File.separator + FilenameUtils.removeExtension(uploadedFile.getName());
            List<File> extractedFiles = FileUtil.unZipIt(uploadedFile, unzipFolderName);
            for (File extractedFile : extractedFiles) {
                extension = FilenameUtils.getExtension(extractedFile.getName());
                if ("xls".equals(extension) || "xlsx".equals(extension)) {
                    saveFileDetails(null, null, fileDetails, extractedFile);

                    readFileAndStoreInDB(extractedFile, fileType);
                }
            }
        } else {
            System.out.println("some other format");
        }
    }

    protected FileDetails saveFileDetails(Client client, Product product, FileDetails parent, File file) {
        FileDetails subFileDetails = new FileDetails();
        subFileDetails.setClient(client);
        subFileDetails.setProduct(product);
        subFileDetails.setFilePath(file.getAbsolutePath());
        subFileDetails.setFileExtension(FilenameUtils.getExtension(file.getName()));
        subFileDetails.setReceivedDate(new Date());
        subFileDetails.setParentFileDetails(parent);
        return fileDetailsRepository.save(subFileDetails);
    }

    protected File storeFileDetails(MultipartFile file, String fileType, byte[] bytes) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        String folderWithDate = dateFormat.format(new Date());


        String folderName = String.format("%s%s%s%s%s", fileUploadFolder, File.separator, fileType, File.separator,
                folderWithDate);

        String filename = folderName + File.separator + file.getOriginalFilename();

        File uploadedFile = FileUtil.createFile(filename, bytes);
        return uploadedFile;
    }

    protected void readFileAndStoreInDB(File file, String fileType) throws Exception {
        GirsaExcelParser parser = new GirsaExcelParser(ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION);
        Map<String, List<Object>> result = parser.parse(file, fileType); //Whatever excel file you want
        Set<Map.Entry<String, List<Object>>> entries = result.entrySet();
        for (Map.Entry<String, List<Object>> map : entries) {
            List<Object> value = map.getValue();
            for (Object obj : value) {
                processObject(obj);
            }
        }
    }

    protected abstract void processObject(Object object);

}
