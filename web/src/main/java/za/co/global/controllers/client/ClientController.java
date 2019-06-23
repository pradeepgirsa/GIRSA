package za.co.global.controllers.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.domain.EntityStatus;
import za.co.global.domain.client.Client;
import za.co.global.persistence.client.ClientRepository;

import java.util.List;

@Controller
public class ClientController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientRepository clientRepository;


    @GetMapping(value = {"/client"})
    public String createorUpdateClient(Model model, Client client) {
        return "client/createClient";
    }

    @GetMapping(value = {"/updateClient"})
    public String createorUpdateClient(Model model, @RequestParam("clientId") String clientId) {
        LOGGER.debug("Navigating to client create or update page");
        return "client/maintainClient";
    }

    @GetMapping(value = {"/view_clients"})
    public String viewClients(Model model) {
        try {
            LOGGER.debug("Navigating to view clients page");
            model.addAttribute("clients", clientRepository.findAll());
            return "client/viewClients";
        } catch (Exception e) {
            LOGGER.error("Error while viewing clients", e);
            model.addAttribute("errorMessage", "Error: "+e.getMessage());
            return "client/viewClients";
        }

    }

    @PostMapping(value = {"/create_client"})
    public ModelAndView saveClient(Model model, Client client) {
        try {
            LOGGER.debug("Before saving client");
            List<Client> clients = clientRepository.findAll();
            for (Client existingClient : clients) {
                if (existingClient.getClientName().equals(client.getClientName())) {
                    LOGGER.error("Client '{}' already exists", client.getClientName());
                    return new ModelAndView("client/createClient", "errorMessage", client.getClientName() + " already exists");
                }
            }
            client.setStatus(EntityStatus.ACTIVE);
            clientRepository.save(client);
            LOGGER.info("Client {} saved successfully", client.getClientName());
            return new ModelAndView("client/createClient", "successMessage", client.getClientName() + " Client created successfully... ");
        } catch (Exception e) {
            LOGGER.error("Error while saving client", e);
            return new ModelAndView("client/createClient", "errorMessage", "Error: "+e.getMessage());
        }
    }



}
