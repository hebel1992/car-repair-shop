package pl.coderslab.servicestation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LandingPageController {
    @GetMapping
    public String landingPage() {
        return "landingPage";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginError", false);
        return "loginForm";
    }

    @GetMapping("/login-error")
    public String loginErrorPage(Model model) {
        model.addAttribute("loginError", true);
        return "loginForm";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "registerForm";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "accessDeniedPage";
    }
}
