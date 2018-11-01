package za.co.global.controllers.fileupload.mapping;

import com.gizbel.excel.enums.ExcelFactoryType;
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
import za.co.global.domain.fileupload.mapping.TransactionListing;
import za.co.global.persistence.fileupload.mapping.IndicesRepository;
import za.co.global.persistence.fileupload.mapping.TransactionListingRepository;
import za.co.global.services.upload.FileAndObjectResolver;
import za.co.global.services.upload.GirsaExcelParser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class TransactionListingController extends BaseFileUploadController {

    private static final String FILE_TYPE = FileAndObjectResolver.TRANSACTION_LISTING.getFileType();

    @Autowired
    private TransactionListingRepository transactionListingRepository;


    @GetMapping("/upload_transaction_listing")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/mapping/transactionListing");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_transaction_listing")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/mapping/transactionListing", "saveError", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (IOException e) {
            return new ModelAndView("fileupload/mapping/transactionListing", "saveError", e.getMessage());
        } catch (Exception e) {
            return new ModelAndView("fileupload/mapping/transactionListing", "saveError", e.getMessage());
        }
        return new ModelAndView("fileupload/mapping/transactionListing", "saveMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    @GetMapping(value = {"/view_transaction_listing"})
    public String viewTransactionListing(Model model) {
        model.addAttribute("transactionListings", transactionListingRepository.findAll());
        return "fileupload/mapping/view/viewTransactionListing";

    }

    @Override //TODO - check asset info stored correctly or not
    protected void readFileAndStoreInDB(File file, String fileType) throws Exception {
        GirsaExcelParser parser = new GirsaExcelParser(ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION);
        Map<String, List<Object>> result = parser.parse(file, fileType); //Whatever excel file you want
        Set<Map.Entry<String, List<Object>>> entries = result.entrySet();
        for (Map.Entry<String, List<Object>> map : entries) {
            String sheetName = map.getKey();
            List<Object> value = map.getValue();
            for (Object obj : value) {
                if (obj instanceof Indices) {
                    TransactionListing transactionListing = getTransactionListing(obj, sheetName);
                    //transactionListing.setType(sheetName);
                    transactionListingRepository.save(transactionListing);
                }
            }
        }
    }

    private TransactionListing getTransactionListing(Object object, String type) {
        TransactionListing transactionListing = (TransactionListing) object;
        TransactionListing existingTransactionListing = null;
        if(existingTransactionListing == null) {
            return transactionListing;
        }
        /*existingTransactionListing.setAsk(indices.getAsk());
        existingTransactionListing.setBid(indices.getBid());
        existingTransactionListing.setDescription(indices.getDescription());
        existingTransactionListing.setExch(indices.getExch());
        existingTransactionListing.setGicsCode(indices.getGicsCode());
        existingTransactionListing.setIndex(indices.getIndex());
        existingTransactionListing.setIndexPoints(indices.getIndexPoints());
        existingTransactionListing.setIndexPrice(indices.getIndexPrice());
        existingTransactionListing.setIssue(indices.getIssue());
        existingTransactionListing.setIwf(indices.getIwf());
        existingTransactionListing.setLast(indices.getLast());
        existingTransactionListing.setMarketCap(indices.getMarketCap());
        existingTransactionListing.setMarketCapLive(indices.getMarketCapLive());
        existingTransactionListing.setPeRatio(indices.getPeRatio());
        existingTransactionListing.setPositiveOrNegative(indices.getPositiveOrNegative());
        existingTransactionListing.setR(indices.getR());
        existingTransactionListing.setSecurity(indices.getSecurity());
        existingTransactionListing.setSubIndustry(indices.getSubIndustry());
        existingTransactionListing.setYldHist(indices.getYldHist());*/

        return existingTransactionListing;
    }

    @Override
    protected void processObject(Object obj) {

    }

}
