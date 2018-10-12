package za.co.global.controllers.fileupload.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.controllers.fileupload.BaseFileUploadController;
import za.co.global.domain.fileupload.mapping.AdditionalClassification;
import za.co.global.domain.fileupload.mapping.Reg28InstrumentType;
import za.co.global.persistence.fileupload.mapping.AdditionalClassificationRepository;
import za.co.global.persistence.fileupload.mapping.Reg28InstrumentTypeRepository;
import za.co.global.services.upload.FileAndObjectResolver;

import java.io.IOException;

@Controller
public class Reg28InstrumentTypeController extends BaseFileUploadController {

    private static final String FILE_TYPE = FileAndObjectResolver.REG28_INSTR_TYPE.getFileType();

    @Autowired
    private Reg28InstrumentTypeRepository reg28InstrumentTypeRepository;


    @GetMapping("/upload_reg28InstrumentType")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/mapping/reg28InstrumentType");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_reg28InstrumentType")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/mapping/reg28InstrumentType", "saveError", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (IOException e) {
            return new ModelAndView("fileupload/mapping/reg28InstrumentType", "saveError", e.getMessage());
        } catch (Exception e) {
            return new ModelAndView("fileupload/mapping/reg28InstrumentType", "saveError", e.getMessage());
        }
        return new ModelAndView("fileupload/mapping/reg28InstrumentType", "saveMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    @Override
    protected void processObject(Object obj) {
        if(obj instanceof Reg28InstrumentType) {
            Reg28InstrumentType ex = (Reg28InstrumentType) obj;
            reg28InstrumentTypeRepository.save(ex);
        }
    }

}
