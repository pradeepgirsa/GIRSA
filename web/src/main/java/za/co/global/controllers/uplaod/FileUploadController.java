package za.co.global.controllers.uplaod;

import com.gizbel.excel.enums.ExcelFactoryType;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import utils.FileUtil;
import za.co.global.domain.client.Client;
import za.co.global.domain.product.Product;
import za.co.global.domain.upload.DSU5_GIRREP4;
import za.co.global.domain.upload.FileDetails;
import za.co.global.persistence.client.ClientRepository;
import za.co.global.persistence.product.ProductRepository;
import za.co.global.persistence.upload.FileDetailsRepository;
import za.co.global.services.upload.GirsaExcelParser;
import za.co.global.services.upload.SheetAndObjectResolver;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Controller
public class FileUploadController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileDetailsRepository fileDetailsRepository;

    private List<Client> clients;
    private List<Product> products;

    @Value("${file.upload.folder}")
    private String fileUploadFolder;


    //destination folder to upload the files
    private static String UPLOAD_FOLDER = "C://temp//";

    @GetMapping("/upload_file")
    public ModelAndView showUpload() {
        clients = clientRepository.findAll();
//        clientId = clients.get(0).getId();

        products = productRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("upload/uploadFile");
        modelAndView.addObject("clients", clients);
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @PostMapping("/upload")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file, String productId, String clientId) {
        if (file.isEmpty()) {
            return new ModelAndView("upload/status", "message", "Please select a file and try again");
        }

        try {
            // read and write the file to the selected location-
            byte[] bytes = file.getBytes();

            Client client = clientRepository.findOne(Long.parseLong(clientId));
            Product product = productRepository.findOne(Long.parseLong(productId));

            //store the uploaded file in specified directory and also persist in database
            File uploadedFile = storeFileDetails(file, client, product, bytes);

            FileDetails fileDetails = saveFileDetails(client, product, null, uploadedFile);

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if ("xls".equals(extension) || "xlsx".equals(extension)) {
                readFileAndStoreInDB(uploadedFile);
            } else if ("zip".equals(extension)) {
                String unzipFolderName = uploadedFile.getParent() + File.separator + FilenameUtils.removeExtension(uploadedFile.getName());
                List<File> extractedFiles = unZipIt(uploadedFile, unzipFolderName);
                for (File extractedFile : extractedFiles) {

                    saveFileDetails(client, product, fileDetails, extractedFile);

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

    private File storeFileDetails(MultipartFile file, Client client, Product product, byte[] bytes) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        String folderWithDate = dateFormat.format(new Date());


        String folderName = String.format("%s%s%s%s%s%s%s", fileUploadFolder, File.separator, folderWithDate, File.separator,
                client.getClientName(), File.separator, product.getProductName());

        String filename = folderName + File.separator + file.getOriginalFilename();

        File uploadedFile = FileUtil.createFile(filename, bytes);
        return uploadedFile;
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

    public List<File> unZipIt(File zipFile, String outputFolder) {

        byte[] buffer = new byte[1024];
        List<File> files = new ArrayList<>();

        try {

            //create output directory is not exists
//            File folder = new File(OUTPUT_FOLDER);
//            if(!folder.exists()){
//                folder.mkdir();
//            }

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);

//                System.out.println("file unzip : "+ newFile.getAbsoluteFile());
                files.add(newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return files;
    }

    private void readFileAndStoreInDB(File file) throws Exception {
        GirsaExcelParser parser = new GirsaExcelParser(ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION);
        Map<String, List<Object>> result = parser.parse(file); //Whatever excel file you want
        Set<Map.Entry<String, List<Object>>> entries = result.entrySet();
        for (Map.Entry<String, List<Object>> map : entries) {
            String key = map.getKey();
            System.out.println("---" + key);
            List<Object> value = map.getValue();
            for (Object obj : value) {
                Class clazz = SheetAndObjectResolver.getClazzFromSheetName(key);
                if(DSU5_GIRREP4.class.getCanonicalName().equals(clazz.getCanonicalName())) {
                    DSU5_GIRREP4 ex = (DSU5_GIRREP4) obj;
                    entityManager.persist(ex);
                }
            }
        }
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
