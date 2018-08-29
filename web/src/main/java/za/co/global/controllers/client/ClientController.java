package za.co.global.controllers.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import za.co.global.domain.EntityStatus;
import za.co.global.domain.client.Client;
import za.co.global.persistence.client.ClientRepository;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;


    @GetMapping(value = {"/client"})
    public String createorUpdateClient(Model model, Client client) {
        return "client/maintainClient";
    }

    @GetMapping(value = {"/updateClient"})
    public String createorUpdateClient(Model model, @RequestParam("clientId") String clientId) {
        return "client/maintainClient";
    }

    @GetMapping(value = {"/view_clients"})
    public String viewClients(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "client/viewClients";

    }

    @PostMapping(value = {"/update_client"})
    public String saveClient(Model model, Client client) {
        client.setStatus(EntityStatus.ACTIVE);
        clientRepository.save(client);
        return "client/maintainClient";
    }



}
