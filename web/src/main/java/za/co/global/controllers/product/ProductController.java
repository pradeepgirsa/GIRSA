package za.co.global.controllers.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import za.co.global.domain.EntityStatus;
import za.co.global.domain.client.Client;
import za.co.global.domain.product.Product;
import za.co.global.persistence.client.ClientRepository;
import za.co.global.persistence.product.ProductRepository;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;


    @GetMapping(value = {"/product"})
    public String login(Model model, Product product) {
        //model.addAttribute("now", LocalDateTime.now());
        //System.out.println("udyanaUserRepository = " + udyanaUserRepository.findAll());
        return "product/maintainProduct";

    }

    @PostMapping(value = {"/update_product"})
    public String updateClient(Model model, Product product) {
        product.setStatus(EntityStatus.ACTIVE);
        productRepository.save(product);
        return "product/maintainProduct";
    }



}
