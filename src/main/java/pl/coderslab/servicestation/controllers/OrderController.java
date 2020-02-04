package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.servicestation.models.*;
import pl.coderslab.servicestation.repositories.*;

import javax.validation.Valid;
import java.time.LocalDate;
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
    private final PartRepository partRepository;

    @GetMapping("/add/{orderId}")
    public String addOrder(Model model, @PathVariable String orderId) {
        if ("empty".equals(orderId)) {
            Order order = new Order();
            model.addAttribute("order", order);
            return "/orders/addOrderStep1";
        } else {
            Order order = orderRepository.findById(Long.parseLong(orderId)).get();
            model.addAttribute("order", order);
        }
        return "/orders/addOrderStep1";
    }

    @PostMapping("/add-execute")
    public String addOrderExecute(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/orders/addOrderStep1";
        }
        if (order.getPlannedRepairStart().equals(LocalDate.now())) {
            order.setActualRepairStart(LocalDate.now());
            order.setStatus(statusRepository.findById(2).get());
        } else {
            order.setStatus(statusRepository.findById(1).get());
        }

        orderRepository.save(order);
        return "redirect:/orders/add-part/" + order.getId();
    }

    @GetMapping("/add-part/{orderId}")
    public String addPartsToOrder(Model model, @PathVariable Long orderId) {
        Part part = new Part();
        Order order = orderRepository.findById(orderId).get();
        model.addAttribute("part", part);
        model.addAttribute("addedParts", order.getParts());
        return "/orders/addOrderStep2";
    }

    @PostMapping("/add-part-execute/{orderId}")
    public String addPartExecute(Model model, @PathVariable Long orderId, @ModelAttribute("part") @Valid Part part, BindingResult bindingResult) {
        Order order = orderRepository.findById(orderId).get();
        if (bindingResult.hasErrors()) {
            model.addAttribute("addedParts", order.getParts());
            return "/orders/addOrderStep2";
        }
        partRepository.save(part);

        order.getParts().add(part);

        order.getParts().stream()
                .forEach(v -> {
                    v.setOrder(order);
                    partRepository.save(v);
                });

        orderRepository.save(order);

        model.addAttribute("addedParts", order.getParts());

        return "redirect:/orders/add-part/" + orderId;
    }

    @GetMapping("/add-last-page/{orderId}")
    public String addOrderLastPage(Model model, @PathVariable Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        model.addAttribute("order", order);
        return "/orders/addOrderStep3";
    }

    @PostMapping("/add-last-page-execute")
    public String addOrderLastPageExecute(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/orders/addOrderStep3";
        }
        orderRepository.save(order);
        return "redirect:/";
    }


    @GetMapping("/delete-part/{partId}/{orderId}")
    public String deletePart(@PathVariable Integer partId, @PathVariable Integer orderId) {
        Part part = partRepository.findById(partId).get();
        partRepository.delete(part);
        return "redirect:/orders/add-part/" + orderId;
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
    public String detachVehicle(@PathVariable Long employeeId, @PathVariable Long orderId, Model model) {
        Order order = orderRepository.findById(orderId).get();
        Employee employee = employeeRepository.findById(employeeId).get();
        order.getEmployees().remove(employee);
        orderRepository.save(order);

        return "redirect:/orders/update/" + orderId;
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
}
