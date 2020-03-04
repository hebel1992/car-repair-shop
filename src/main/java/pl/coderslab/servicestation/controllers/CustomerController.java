package pl.coderslab.servicestation.controllers;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.servicestation.models.Customer;
import pl.coderslab.servicestation.services.CustomerService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping
    public String allCustomers() {
        return "customers/customersList";
    }

    @PostMapping("/filtered")
    public String filteredCustomers(Model addToModel, @RequestParam("firstName") String firstName,
                                   @RequestParam("lastName") String lastName, @RequestParam("phone") String phoneNumber) {

        List<Customer> customers = customerService.findFiltered(firstName, lastName, phoneNumber);
        addToModel.addAttribute("customers", customers);

        return "customers/customersList";
    }

    @GetMapping("/add")
    public String addCustomer(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customers/addCustomer";
    }

    @PostMapping("/add-action")
    public String addCustomer(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "customers/addCustomer";
        }

        customerService.saveCustomer(customer);

        return "redirect:/customers";
    }

    @RequestMapping("/details/{id}")
    public String customerDetails(@PathVariable("id") Long id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        return "customers/customerDetails";
    }

    @GetMapping("/update/{id}")
    public String updateCustomer(Model model, @PathVariable Long id) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        return "customers/editCustomer";
    }

    @PostMapping("/update-action")
    public String updateCustomer(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "customers/editCustomer";
        }

        customerService.saveCustomer(customer);

        return "redirect:/customers/details/" + customer.getId();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete-action/{id}")
    public String deleteCustomerAction(@PathVariable Long id, @RequestParam("action") boolean action) {
        if (action) {
            customerService.deleteCustomer(id);
            return "redirect:/customers";
        } else {
            return "redirect:/customers/update/" + id;
        }
    }

    @ModelAttribute("customers")
    public List<Customer> getCustomers() {
        return customerService.findAll();
    }
}
