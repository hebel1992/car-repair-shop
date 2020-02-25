package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginViewController {

    @GetMapping
    public String loginPage(Model model) {
        model.addAttribute("loginError", false);
        return "loginForm";
    }

    @GetMapping("/login-error")
    public String loginErrorPage(Model model) {
        model.addAttribute("loginError", true);
        return "loginForm";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "accessDeniedPage";
    }

//    @GetMapping("/admin")
//    @ResponseBody
//    public String userInfo(@AuthenticationPrincipal CurrentUser customUser) {
//        logger.info("customUser class {} ", customUser.getClass());
//        User entityUser = customUser.getUser();
//        return "Hello " + entityUser.getUsername();
//    }
}
