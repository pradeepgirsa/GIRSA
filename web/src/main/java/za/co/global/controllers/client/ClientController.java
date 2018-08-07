package za.co.global.controllers.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import za.co.global.domain.EntityStatus;
import za.co.global.domain.client.Client;
import za.co.global.persistence.client.ClientRepository;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;


    @GetMapping(value = {"/client"})
    public String login(Model model, Client client) {
        //model.addAttribute("now", LocalDateTime.now());
        //System.out.println("udyanaUserRepository = " + udyanaUserRepository.findAll());
        return "client/maintainClient";

    }

    @PostMapping(value = {"/update_client"})
    public String updateClient(Model model, Client client) {
        client.setStatus(EntityStatus.ACTIVE);
        clientRepository.save(client);
        return "client/maintainClient";
    }



}
