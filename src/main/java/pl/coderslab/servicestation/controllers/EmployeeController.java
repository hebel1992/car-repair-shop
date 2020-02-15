package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.servicestation.models.Employee;
import pl.coderslab.servicestation.repositories.EmployeeRepository;
import pl.coderslab.servicestation.services.EmployeeService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    @GetMapping
    public String allEmployees() {
        return "employees/employeesList";
    }

    @GetMapping("/add")
    public String addEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employees/addEmployee";
    }

    @PostMapping("/add-action")
    public String addEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "employees/addEmployee";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/details/{id}")
    public String employeeDetails(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "/employees/employeeDetails";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/update/{id}")
    public String updateEmployee(Model model, @PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "employees/editEmployee";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/update-action")
    public String updateEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "employees/editEmployee";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/employees/details/" + employee.getId();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public String deleteEmployee(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return "employees/deleteEmployee";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete-action/{id}")
    public String deleteEmployeeAction(@PathVariable Long id, @RequestParam("action") boolean action) {
        if (action) {
            employeeService.deleteEmployee(id);
            return "redirect:/employees";
        } else {
            return "redirect:/employees/details/" + id;
        }
    }

    @ModelAttribute("employees")
    public List<Employee> getEmployees() {
        return employeeService.findAll();
    }
}
