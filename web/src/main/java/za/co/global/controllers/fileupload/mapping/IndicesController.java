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
import za.co.global.persistence.fileupload.mapping.IndicesRepository;
import za.co.global.services.upload.FileAndObjectResolver;
import za.co.global.services.upload.GirsaExcelParser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class IndicesController extends BaseFileUploadController {

    private static final String FILE_TYPE = FileAndObjectResolver.INDICES.getFileType();

    @Autowired
    private IndicesRepository indicesRepository;


    @GetMapping("/upload_indices")
    public ModelAndView showUpload() {
        ModelAndView modelAndView = new ModelAndView("fileupload/mapping/indices");
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_indices")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/mapping/indices", "saveError", "Please select a file and try again");
        }
        try {
            processFile(file, FILE_TYPE, null, null);
        } catch (IOException e) {
            return new ModelAndView("fileupload/mapping/indices", "saveError", e.getMessage());
        } catch (Exception e) {
            return new ModelAndView("fileupload/mapping/indices", "saveError", e.getMessage());
        }
        return new ModelAndView("fileupload/mapping/indices", "saveMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    @GetMapping(value = {"/view_indices"})
    public String viewIndices(Model model) {
        model.addAttribute("indices", indicesRepository.findAll());
        return "fileupload/mapping/view/viewIndices";

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
                    Indices indices = getIndices(obj, sheetName);
                    indices.setType(sheetName);
                    indicesRepository.save(indices);
                }
            }
        }
    }

    private Indices getIndices(Object object, String type) {
        Indices indices = (Indices) object;
        Indices existingIndice = indicesRepository.findBySecurityAndType(indices.getSecurity(), type);
        if(existingIndice == null) {
            return indices;
        }
        existingIndice.setAsk(indices.getAsk());
        existingIndice.setBid(indices.getBid());
        existingIndice.setDescription(indices.getDescription());
        existingIndice.setExch(indices.getExch());
        existingIndice.setGicsCode(indices.getGicsCode());
        existingIndice.setIndexPercentage(indices.getIndexPercentage());
        existingIndice.setIndexPoints(indices.getIndexPoints());
        existingIndice.setIndexPrice(indices.getIndexPrice());
        existingIndice.setIssue(indices.getIssue());
        existingIndice.setIwf(indices.getIwf());
        existingIndice.setLast(indices.getLast());
        existingIndice.setMarketCap(indices.getMarketCap());
        existingIndice.setMarketCapLive(indices.getMarketCapLive());
        existingIndice.setPeRatio(indices.getPeRatio());
        existingIndice.setPositiveOrNegative(indices.getPositiveOrNegative());
        existingIndice.setR(indices.getR());
        existingIndice.setSecurity(indices.getSecurity());
        existingIndice.setSubIndustry(indices.getSubIndustry());
        existingIndice.setYldHist(indices.getYldHist());

        return existingIndice;
    }

    @Override
    protected void processObject(Object obj) {

    }

}
