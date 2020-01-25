package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.servicestation.models.Order;
import pl.coderslab.servicestation.models.Vehicle;
import pl.coderslab.servicestation.repositories.OrderRepository;
import pl.coderslab.servicestation.repositories.VehicleRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final VehicleRepository vehicleRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/add")
    public String addOrder(Model model) {
        Order order = new Order();
        model.addAttribute("order", order);
        return "orders/addOrder";
    }

    @PostMapping("/add-execute")
    public String addOrder(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orders/addOrder";
        }
        orderRepository.save(order);
        return "redirect:/orders/list";
    }

    @GetMapping("/details/{id}")
    public String customerDetails(@PathVariable("id") Long id, Model model) {
        Order order = orderRepository.findById(id).get();
        model.addAttribute("order", order);
        return "/orders/customerDetails";
    }

    @GetMapping("/update/{id}")
    public String updateCustomer(Model model, @PathVariable Long id) {
        Order order = orderRepository.findById(id).get();
        model.addAttribute("order", order);
        return "orders/editCustomer";
    }

    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orders/editCustomer";
        }
        orderRepository.save(order);
        return "redirect:/orders/details/" + order.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return "orders/deleteCustomer";
    }

    @GetMapping("/delete-action/{id}")
    public String deleteCustomerAction(@PathVariable Long id, @RequestParam("action") boolean action) {
        if (action) {
            Order order = orderRepository.findById(id).get();
            orderRepository.delete(order);
            return "redirect:/orders/list";
        } else {
            return "redirect:/orders/details/" + id;
        }
    }

    @ModelAttribute("orders")
    public List<Order> getCustomers() {
        return orderRepository.findAll();
    }

    @ModelAttribute("vehiclesWithourOwner")
    public List<Vehicle> getVehicles() {
        return vehicleRepository.findWithoutCustomer();
    }
}
