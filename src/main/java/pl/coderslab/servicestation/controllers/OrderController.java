package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.servicestation.models.Employee;
import pl.coderslab.servicestation.models.Order;
import pl.coderslab.servicestation.models.Status;
import pl.coderslab.servicestation.models.Vehicle;
import pl.coderslab.servicestation.repositories.EmployeeRepository;
import pl.coderslab.servicestation.repositories.OrderRepository;
import pl.coderslab.servicestation.repositories.StatusRepository;
import pl.coderslab.servicestation.repositories.VehicleRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final VehicleRepository vehicleRepository;
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final StatusRepository statusRepository;

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
        return "redirect:/";
    }

    @GetMapping("/details/{id}")
    public String customerDetails(@PathVariable("id") Long id, Model model) {
        Order order = orderRepository.findById(id).get();
        Set<Employee> employeesAssignedToOrder = employeeRepository.findEmployeesByOrderId(id);
        model.addAttribute("order", order);
        model.addAttribute("employeesAssignedToOrder", employeesAssignedToOrder);
        return "/orders/orderDetails";
    }

    @GetMapping("/update/{id}")
    public String updateCustomer(Model model, @PathVariable Long id) {
        Order order = orderRepository.findById(id).get();
        Set<Employee> employeesNotInOrder = employeeRepository.findAllEmployeesNotInOrder(id);
        model.addAttribute("order", order);
        model.addAttribute("employeesNotInOrder", employeesNotInOrder);
        return "orders/editOrder";
    }

    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orders/editOrder";
        }
        Order orderBeforeUpdate = orderRepository.findById(order.getId()).get();

        order.getEmployees().addAll(orderBeforeUpdate.getEmployees());
        orderRepository.save(order);
        return "redirect:/orders/details/" + order.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return "orders/deleteOrder";
    }

    @GetMapping("/delete-action/{id}")
    public String deleteCustomerAction(@PathVariable Long id, @RequestParam("action") boolean action) {
        if (action) {
            Order order = orderRepository.findById(id).get();
            orderRepository.delete(order);
            return "redirect:/";
        } else {
            return "redirect:/orders/details/" + id;
        }
    }

    @GetMapping("/detachEmployee/{employeeId}/{orderId}")
    public String detachVehicle(@PathVariable Long employeeId, @PathVariable Long orderId, Model model){
        Order order = orderRepository.findById(orderId).get();
        Employee employee = employeeRepository.findById(employeeId).get();
        order.getEmployees().remove(employee);
        orderRepository.save(order);

        return "redirect:/orders/update/"+orderId;
    }

    @RequestMapping("/historyOrders")
    public String historyOrdersView() {
        return "orders/historyOrders";
    }

    @ModelAttribute("vehicles")
    public List<Vehicle> getCustomers() {
        return vehicleRepository.findAll();
    }

    @ModelAttribute("employees")
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @ModelAttribute("statusOptions")
    public List<Status> getStatusList() {
        return statusRepository.findAll();
    }

    @ModelAttribute("historyOrders")
    public List<Order> historyOrders(){
        return orderRepository.findHistoryOrders();
    }
}
