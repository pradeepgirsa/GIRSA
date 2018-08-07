package za.co.global.controllers.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @GetMapping(value = {"/","/login"})
    public String login(Model model, String username, String password) {
        //model.addAttribute("now", LocalDateTime.now());
        //System.out.println("udyanaUserRepository = " + udyanaUserRepository.findAll());
        return "users/login";

    }

    @RequestMapping(value = "/verify_login", method = RequestMethod.POST)
    public String verifyLogin(Model model, String username, String password){
        if("girsa".equals(username) && "girsa123".equals(password) ){
            return "home";
        } else {
            model.addAttribute("message", "Incorrect Username and password");
            return "home";
        }
    }


}
