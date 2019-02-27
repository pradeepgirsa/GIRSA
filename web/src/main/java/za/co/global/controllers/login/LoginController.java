package za.co.global.controllers.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

   @Value("${girsa.environment}")
   private String environment;

    @Value("${girsa.version}")
    private String version;

   @RequestMapping(value = {"/login", "/" })
   public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error,
                                 @RequestParam(value = "logout", required = false) String logout,
                                 @RequestParam(value = "request", required = false) String request){

       ModelAndView model = new ModelAndView();
       model.addObject("environment", environment);
       model.addObject("version", version);

       if (error != null) {
           model.addObject("errorMessage", true);
       }

       if (logout != null) {
           model.addObject("logoutMessage", true);
       }

       if (request != null) {
           model.addObject("requestMessage", true);
       }

       model.setViewName("users/login");

       return model;
   }

    @PostMapping(value = {"/logout"})
    public String logout(Model model, @RequestParam(value = "error", required = false) String error,
                                  @RequestParam(value = "logout", required = false) String logout,
                                  @RequestParam(value = "request", required = false) String request){

        model.addAttribute("environment", environment);
        model.addAttribute("version", version);

        if (error != null) {
            model.addAttribute("errorMessage", true);
        }

        if (logout != null) {
            model.addAttribute("logoutMessage", true); //TODO - add logout message
        }

        if (request != null) {
            model.addAttribute("requestMessage", true);
        }

        return "users/login";
    }


    @RequestMapping(value = "/verify_login", method = RequestMethod.POST)
    public String verifyLogin(Model model, String username, String password){
        model.addAttribute("environment", environment);
        model.addAttribute("version", version);
        if("girsa".equals(username) && "girsa123".equals(password) ){
            return "home";
        } else {
            model.addAttribute("errorMessage", "#Username or Password wrong");
            return "users/login";
        }
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(){
        return "home";
    }

    @RequestMapping(value = {"/logout"})
    public ModelAndView logoutPage(){

        ModelAndView model = new ModelAndView();
        model.addObject("environment", environment);
        model.addObject("version", version);

        model.addObject("logoutMessage", true);

        model.setViewName("login");

        return model;
    }

}
