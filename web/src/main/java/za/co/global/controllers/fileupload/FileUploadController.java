package za.co.global.controllers.fileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.barra.DSU5_GIRREP4;
import za.co.global.domain.product.Product;
import za.co.global.persistence.client.ClientRepository;
import za.co.global.persistence.product.ProductRepository;
import za.co.global.services.upload.FileAndObjectResolver;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;

@Controller
public class FileUploadController extends BaseFileUploadController {

    public static final String FILE_TYPE = FileAndObjectResolver.DSU5_GIRREP4.getFileType();

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    private List<Client> clients;
    private List<Product> products;

    @GetMapping("/upload_file")
    public ModelAndView showUpload() {
        clients = clientRepository.findAll();
        products = productRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("upload/uploadFile");
        modelAndView.addObject("clients", clients);
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @Transactional
    @PostMapping("/upload")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file, String productId, String clientId) {
        if (file.isEmpty()) {
            return new ModelAndView("upload/status", "message", "Please select a file and try again");
        }

        try {
            Client client = clientRepository.findOne(Long.parseLong(clientId));
            Product product = productRepository.findOne(Long.parseLong(productId));

            processFile(file, FILE_TYPE, client, product);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("upload/status", "message", "File Uploaded successfully... " + file.getOriginalFilename());
    }

    @Override
    protected void processObject(Object obj) {
        if (obj instanceof DSU5_GIRREP4) {
            DSU5_GIRREP4 ex = (DSU5_GIRREP4) obj;
            entityManager.persist(ex);
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
