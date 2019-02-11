//package za.co.global.controllers.fileupload.client;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//import za.co.global.controllers.fileupload.BaseFileUploadController;
//import za.co.global.domain.fileupload.client.TransactionListing;
//import za.co.global.persistence.fileupload.client.TransactionListingRepository;
//import za.co.global.services.upload.FileAndObjectResolver;
//
//import java.util.List;
//
//@Controller
//public class TransactionListingController extends BaseFileUploadController {
//
//    private static final String FILE_TYPE = FileAndObjectResolver.TRANSACTION_LISTING.getFileType();
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionListingController.class);
//
//    @Autowired
//    private TransactionListingRepository transactionListingRepository;
//
//
//    @GetMapping("/upload_transaction_listing")
//    public ModelAndView showUpload() {
//        ModelAndView modelAndView = new ModelAndView("fileupload/mapping/transactionListing");
//        return modelAndView;
//    }
//
//    @Transactional
//    @PostMapping("/upload_transaction_listing")
//    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return new ModelAndView("fileupload/mapping/transactionListing", "saveError", "Please select a file and try again");
//        }
//        try {
//            processFile(file, FILE_TYPE, null, null);
//        } catch (Exception e) {
//            LOGGER.error("Error while uploading {}", FILE_TYPE, e);
//            return new ModelAndView("fileupload/mapping/transactionListing", "saveError", e.getMessage());
//        }
//        return new ModelAndView("fileupload/mapping/transactionListing", "saveMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
//    }
//
//    @GetMapping(value = {"/view_transaction_listing"})
//    public String viewTransactionListing(Model model) {
//        model.addAttribute("transactionListings", transactionListingRepository.findAll());
//        return "fileupload/mapping/view/viewTransactionListing";
//
//    }
//
//    @Override
//    protected void processObject(Object obj) {
//        if(obj instanceof TransactionListing) {
//            TransactionListing transactionListing = getTransactionListing(obj);
//            transactionListingRepository.save(transactionListing);
//        }
//    }
//
//    private TransactionListing getTransactionListing(Object object) {
//        TransactionListing transactionListing = (TransactionListing) object;
//        List<TransactionListing> transactionListings = transactionListingRepository.findByClientPortfolioCodeAndInstrumentCodeOrderByTradeDateAsc(transactionListing.getClientPortfolioCode(),
//                transactionListing.getInstrumentCode());
//        if(transactionListings.isEmpty()) {
//            return transactionListing;
//        }
//        TransactionListing existingTransactionListing = transactionListings.get(0);
//        existingTransactionListing.setTradeDate(transactionListing.getTradeDate());
//        return existingTransactionListing;
//    }
//}
