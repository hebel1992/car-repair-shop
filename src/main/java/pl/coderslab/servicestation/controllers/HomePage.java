package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.servicestation.repositories.OrderRepository;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomePage {

    private final OrderRepository orderRepository;

    @RequestMapping
    public String home() {
        return "home";
    }

//    public List<Order> ordersStarted(){
//
//    }
}
