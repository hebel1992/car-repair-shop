package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.servicestation.models.CurrentUser;
import pl.coderslab.servicestation.models.User;
import pl.coderslab.servicestation.services.UserService;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

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

    @GetMapping("/create-user")
    @ResponseBody
    public String createUser() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        userService.saveUser(user);
        return "admin";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String userInfo(@AuthenticationPrincipal CurrentUser customUser) {
        logger.info("customUser class {} ", customUser.getClass());
        User entityUser = customUser.getUser();
        return "Hello " + entityUser.getUsername();
    }
}
