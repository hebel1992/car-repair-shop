package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.servicestation.models.Customer;
import pl.coderslab.servicestation.models.Vehicle;
import pl.coderslab.servicestation.repositories.CustomerRepository;
import pl.coderslab.servicestation.repositories.VehicleRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    @RequestMapping("/list")
    public String allCustomers() {
        return "customers/customersList";
    }

    @GetMapping("/add")
    public String addCustomer(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customers/addCustomer";
    }

    @PostMapping("/add-execute")
    public String addCustomer(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "customers/addCustomer";
        }

        customerRepository.save(customer);
        return "redirect:/customers/list";
    }

    @GetMapping("/details/{id}")
    public String customerDetails(@PathVariable("id") Long id, Model model) {
        Customer customer = customerRepository.findById(id).get();
        List<Vehicle> customerVehicles = vehicleRepository.findByCustomer_Id(id);
        model.addAttribute("customer", customer);
        model.addAttribute("vehicles", customerVehicles);
        return "/customers/customerDetails";
    }

    @GetMapping("/update/{id}")
    public String updateCustomer(Model model, @PathVariable Long id) {
        Customer customer = customerRepository.findById(id).get();
        model.addAttribute( "customer", customer);
        return "customers/editCustomer";
    }

    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "customers/editCustomer";
        }
        customerRepository.save(customer);
        return "redirect:/customers/details/" + customer.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return "customers/delete";
    }

    @GetMapping("/delete-action/{id}")
    public String deleteCustomerAction(@PathVariable Long id, @RequestParam("action") boolean action) {
        if (action) {
            Customer customer = customerRepository.findById(id).get();
            customerRepository.delete(customer);
            return "redirect:/customers/list";
        } else {
            return "redirect:/customers/details/" + id;
        }
    }

    @ModelAttribute("customers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @ModelAttribute("vehicles")
    public List<Vehicle> getVehicles() {
        return vehicleRepository.findAll();
    }
}
