package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.servicestation.models.Order;
import pl.coderslab.servicestation.repositories.OrderRepository;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomePageController {

    private final OrderRepository orderRepository;

    @RequestMapping
    public String home() {
        return "home";
    }

    @ModelAttribute("ordersStarted")
    public List<Order> ordersStarted(){
       return orderRepository.findStartedOrders();
    }

    @ModelAttribute("plannedOrders")
    public List<Order> plannedOrders(){
        return orderRepository.findPlannedOrders();
    }
}
