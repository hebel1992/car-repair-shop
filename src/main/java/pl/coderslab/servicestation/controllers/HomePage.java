package pl.coderslab.servicestation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomePage {
    @RequestMapping
    public String home(){
        return "home";
    }
}
