//package za.co.global.controllers.fileupload.client;
//
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
//import za.co.global.domain.fileupload.client.NumberOfAccounts;
//import za.co.global.persistence.fileupload.client.NumberOfAccountsRepository;
//import za.co.global.services.upload.FileAndObjectResolver;
//
//import java.io.IOException;
//
//@Controller
//public class NumberOfAccountsController extends BaseFileUploadController {
//
//    public static final String FILE_TYPE = FileAndObjectResolver.NUMBER_OF_ACCOUNTS.getFileType();
//
//    @Autowired
//    private NumberOfAccountsRepository numberOfAccountsRepository;
//
//    @GetMapping("/upload_numberOfAccounts")
//    public ModelAndView showUpload() {
//        ModelAndView modelAndView = new ModelAndView("fileupload/client/numberOfAccounts");
//        return modelAndView;
//    }
//
//    @Transactional
//    @PostMapping("/upload_numberOfAccounts")
//    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return new ModelAndView("fileupload/client/numberOfAccounts", "saveError", "Please select a file and try again");
//        }
//        try {
//            processFile(file, FILE_TYPE, null, null);
//        } catch (IOException e) {
//            return new ModelAndView("fileupload/client/numberOfAccounts", "saveError", e.getMessage());
//        } catch (Exception e) {
//            return new ModelAndView("fileupload/client/numberOfAccounts", "saveError", e.getMessage());
//        }
//        return new ModelAndView("fileupload/client/numberOfAccounts", "saveMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
//    }
//
//    @GetMapping(value = {"/view_number_of_Accounts"})
//    public String viewNumberOfAccounts(Model model) {
//        model.addAttribute("numberOfAccounts", numberOfAccountsRepository.findAll());
//        return "fileupload/client/view/viewNumberOfAccounts";
//
//    }
//
//    @Override
//    protected void processObject(Object obj) {
//        if (obj instanceof NumberOfAccounts) {
//            NumberOfAccounts numberOfAccounts = getNumberOfAccounts(obj);
//            numberOfAccountsRepository.save(numberOfAccounts);
//        }
//    }
//
//    private NumberOfAccounts getNumberOfAccounts(Object obj) {
//        NumberOfAccounts numberOfAccounts = (NumberOfAccounts) obj;
//        NumberOfAccounts existingNumberOfAccounts = numberOfAccountsRepository.findByFundCode(numberOfAccounts.getFundCode());
//        if (existingNumberOfAccounts == null) {
//            return numberOfAccounts;
//        }
//        existingNumberOfAccounts.setTotal(numberOfAccounts.getTotal());
//        return existingNumberOfAccounts;
//    }
//
//
//}
