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
//import za.co.global.domain.fileupload.client.InstitutionalDetails;
//import za.co.global.persistence.fileupload.client.InstitutionalDetailsRepository;
//import za.co.global.services.upload.FileAndObjectResolver;
//
//import java.io.IOException;
//
//@Controller
//public class InstitutionalDetailsController extends BaseFileUploadController {
//
//    public static final String FILE_TYPE = FileAndObjectResolver.INSTITUTIONAL_DETAILS.getFileType();
//
//    @Autowired
//    private InstitutionalDetailsRepository institutionalDetailsRepository;
//
//    @GetMapping("/upload_institutionalDetails")
//    public ModelAndView showUpload() {
//        ModelAndView modelAndView = new ModelAndView("fileupload/client/institutionalDetails");
//        return modelAndView;
//    }
//
//    @Transactional
//    @PostMapping("/upload_institutionalDetails")
//    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return new ModelAndView("fileupload/client/institutionalDetails", "saveError", "Please select a file and try again");
//        }
//        try {
//            processFile(file, FILE_TYPE, null, null);
//        } catch (IOException e) {
//            return new ModelAndView("fileupload/client/institutionalDetails", "saveError", e.getMessage());
//        } catch (Exception e) {
//            return new ModelAndView("fileupload/client/institutionalDetails", "saveError", e.getMessage());
//        }
//        return new ModelAndView("fileupload/client/institutionalDetails", "saveMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
//    }
//
//    @GetMapping(value = {"/view_institutional_details"})
//    public String viewInstitutionalDetails(Model model) {
//        model.addAttribute("institutionalDetails", institutionalDetailsRepository.findAll());
//        return "fileupload/client/view/viewInstitutionalDetails";
//
//    }
//
//    @Override
//    protected void processObject(Object obj) {
//        if (obj instanceof InstitutionalDetails) {
//            InstitutionalDetails institutionalDetails = getInstitutionalDetails(obj);
//            institutionalDetailsRepository.save(institutionalDetails);
//        }
//    }
//
//    private InstitutionalDetails getInstitutionalDetails(Object object) {
//        InstitutionalDetails institutionalDetails = (InstitutionalDetails) object;
//        InstitutionalDetails existingInstitutionalDetails = institutionalDetailsRepository.findByClientFundCode(institutionalDetails.getClientFundCode());
//        if (existingInstitutionalDetails == null) {
//            return institutionalDetails;
//        }
//        existingInstitutionalDetails.setInstitutionalTotal(institutionalDetails.getInstitutionalTotal());
//        existingInstitutionalDetails.setFundName(institutionalDetails.getFundName());
//        existingInstitutionalDetails.setPercentage(institutionalDetails.getPercentage());
//        existingInstitutionalDetails.setTotal(institutionalDetails.getTotal());
//        return existingInstitutionalDetails;
//    }
//
//}
