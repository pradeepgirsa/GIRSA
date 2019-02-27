package za.co.global.controllers.fileupload.mapping;

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
import za.co.global.domain.fileupload.mapping.ClientFundMapping;
import za.co.global.persistence.fileupload.mapping.ClientFundMappingRepository;
import za.co.global.services.upload.FileAndObjectResolver;

import java.io.IOException;

@Controller
public class ClientFundMappingController extends BaseFileUploadController {

    public static final String FILE_TYPE = FileAndObjectResolver.CLIENT_FUND_MAPPING.getFileType();

    @Autowired
    private ClientFundMappingRepository clientFundMappingRepository;

    @GetMapping("/upload_clientFundMapping")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/mapping/clientFundMapping");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_clientFundMapping")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/mapping/clientFundMapping", "errorMessage", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (IOException e) {
            return new ModelAndView("fileupload/mapping/clientFundMapping", "errorMessage", e.getMessage());
        } catch (Exception e) {
            return new ModelAndView("fileupload/mapping/clientFundMapping", "errorMessage", e.getMessage());
        }
        return new ModelAndView("fileupload/mapping/clientFundMapping", "successMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    @GetMapping(value = {"/view_client_fund_mapping"})
    public String viewPSGFundMapping(Model model) {
        model.addAttribute("fundMappings", clientFundMappingRepository.findAll());
        return "fileupload/mapping/view/viewClientFundMapping";

    }

    @Override
    protected void processObject(Object obj) {
        if(obj instanceof ClientFundMapping) {
            ClientFundMapping clientFundMapping = getClientFundMapping(obj);
            clientFundMappingRepository.save(clientFundMapping);
        }
    }

    private ClientFundMapping getClientFundMapping(Object object) {
        ClientFundMapping clientFundMapping = (ClientFundMapping) object;
        //TODO - check with manager fundcode
        ClientFundMapping existingClientFundMapping = clientFundMappingRepository.findByClientFundCode(clientFundMapping.getManagerFundCode());
        if(existingClientFundMapping == null) {
            return clientFundMapping;
        }
        existingClientFundMapping.setBarraFundName(clientFundMapping.getBarraFundName());
        existingClientFundMapping.setManagerFundName(clientFundMapping.getManagerFundName());
        existingClientFundMapping.setClientFundCode(clientFundMapping.getClientFundCode());
        existingClientFundMapping.setComments(clientFundMapping.getComments());
        existingClientFundMapping.setFundCurrency(clientFundMapping.getFundCurrency());
        return existingClientFundMapping;
    }

}
