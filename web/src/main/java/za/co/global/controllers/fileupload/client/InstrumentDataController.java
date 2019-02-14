package za.co.global.controllers.fileupload.client;

import com.gizbel.excel.enums.ExcelFactoryType;
import org.apache.commons.io.FilenameUtils;
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
import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.FileDetails;
import za.co.global.domain.fileupload.client.InstrumentData;
import za.co.global.domain.product.Product;
import za.co.global.domain.report.ReportStatus;
import za.co.global.persistence.client.ClientRepository;
import za.co.global.persistence.fileupload.client.InstrumentDataRepository;
import za.co.global.services.helper.FileUtil;
import za.co.global.services.upload.FileAndObjectResolver;
import za.co.global.services.upload.GirsaExcelParser;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class InstrumentDataController extends BaseFileUploadController {

    private static final String FILE_TYPE = FileAndObjectResolver.INSTRUMENT_DATA.getFileType();

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private InstrumentDataRepository instrumentDataRepository;

    private List<Client> clients;

    @GetMapping("/upload_instrumentData")
    public ModelAndView showUpload() {
        clients = clientRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("fileupload/client/instrumentData");
        modelAndView.addObject("clients", clients);
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload_instrumentData")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file, String clientId) {
        if (file.isEmpty()) {
            return new ModelAndView("fileupload/client/instrumentData", "saveError", "Please select a file and try again");
        }
        try {
            Client client = clientRepository.findOne(Long.parseLong(clientId));
            processFile(file, FILE_TYPE, client, null);
        } catch (IOException e) {
            return new ModelAndView("fileupload/client/instrumentData", "saveError", e.getMessage());
        } catch (Exception e) {
            return new ModelAndView("fileupload/client/instrumentData", "saveError", e.getMessage());
        }
        return new ModelAndView("fileupload/client/instrumentData", "saveMessage", "File Uploaded sucessfully... " + file.getOriginalFilename());
    }

    @Override
    protected void processFile(MultipartFile file, String fileType, Client client, Product product) throws Exception {
        // read and write the file to the selected location-
        byte[] bytes = file.getBytes();

        //store the uploaded file in specified directory and also persist in database
        File uploadedFile = storeFileDetails(file, fileType, bytes);

        FileDetails fileDetails = saveFileDetails(client, product, null, uploadedFile);

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if ("xls".equals(extension) || "xlsx".equals(extension)) {
            readFileAndStoreInDB(uploadedFile, fileType, client);
        } else if ("zip".equals(extension)) {
            String unzipFolderName = uploadedFile.getParent() + File.separator + FilenameUtils.removeExtension(uploadedFile.getName());
            List<File> extractedFiles = FileUtil.unZipIt(uploadedFile, unzipFolderName);
            for (File extractedFile : extractedFiles) {
                extension = FilenameUtils.getExtension(extractedFile.getName());
                if ("xls".equals(extension) || "xlsx".equals(extension)) {
                    saveFileDetails(null, null, fileDetails, extractedFile);

                    readFileAndStoreInDB(extractedFile, fileType, client);
                }
            }
        } else {
            System.out.println("some other format");
        }
    }

    protected void readFileAndStoreInDB(File file, String fileType, Client client) throws Exception {
        GirsaExcelParser parser = new GirsaExcelParser(ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION);
        Map<String, List<Object>> result = parser.parse(file, fileType); //Whatever excel file you want
        Set<Map.Entry<String, List<Object>>> entries = result.entrySet();
        for (Map.Entry<String, List<Object>> map : entries) {
            List<Object> value = map.getValue();
            for (Object obj : value) {
                processObject(obj, client);
            }
        }
    }

    @GetMapping(value = {"/view_instrumentData"})
    public String viewInstrumentCode(Model model) {
        model.addAttribute("instrumentsData", instrumentDataRepository.findAll());
        return "fileupload/client/view/viewInstrumentData";

    }

    protected void processObject(Object obj, Client client) {
        if(obj instanceof InstrumentData) {
            InstrumentData instrumentData = (InstrumentData) obj;

            if(instrumentData == null){
                throw new IllegalStateException("Something wrong with data");
            }
            List<InstrumentData> instumentDataList = instrumentDataRepository.findByPortfolioCodeAndInstrumentCodeAndClientAndReportDataIsNullOrPortfolioCodeAndInstrumentCodeAndClientAndReportData_ReportStatus(
                    instrumentData.getPortfolioCode(), instrumentData.getInstrumentCode(), client,
                    instrumentData.getPortfolioCode(), instrumentData.getInstrumentCode(), client, ReportStatus.REGISTERED);
            if(instumentDataList.size() > 1) {
                throw new IllegalStateException("Found duplicate instument record with same Portfolio:" + instrumentData.getPortfolioCode() +
                        ", client:"+client.getClientName()+ ", instrument code: "+instrumentData.getInstrumentCode());
            }
            if(instumentDataList.size() == 1) {
                InstrumentData instrumentDataToSave = instumentDataList.get(0);
                instrumentDataToSave.setCurrentBookValue(instrumentData.getCurrentBookValue());
                instrumentDataToSave.setCurrentMarketValue(instrumentData.getCurrentMarketValue());
                instrumentDataToSave.setInstitutionTotal(instrumentData.getInstitutionTotal());
                instrumentDataToSave.setInstrumentCode(instrumentData.getInstrumentCode());
                instrumentDataToSave.setInstrumentCurrency(instrumentData.getInstrumentCurrency());
                instrumentDataToSave.setMarketValueTotal(instrumentData.getMarketValueTotal());
                instrumentDataToSave.setNominalUnits(instrumentData.getNominalUnits());
                instrumentDataToSave.setNoOfAccounts(instrumentData.getNoOfAccounts());
                instrumentDataToSave.setInstrumentDescription(instrumentData.getInstrumentDescription());
                instrumentDataToSave.setPortfolioCode (instrumentData.getPortfolioCode());
                instrumentDataToSave.setPortfolioName (instrumentData.getPortfolioName());
                instrumentDataToSave.setTradeDate(instrumentData.getTradeDate());
                instrumentDataToSave.setUpdatedDate(new Date());
                instrumentDataRepository.save(instrumentDataToSave);
            } else {
                instrumentData.setClient(client);
                instrumentData.setUpdatedDate(new Date());
                instrumentDataRepository.save(instrumentData);
            }
        }
    }

    @Override
    protected void processObject(Object obj) {
        //TODO - nothing
    }
}
