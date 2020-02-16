package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.servicestation.models.Employee;
import pl.coderslab.servicestation.models.Role;
import pl.coderslab.servicestation.models.User;
import pl.coderslab.servicestation.repositories.RoleRepository;
import pl.coderslab.servicestation.services.EmployeeService;
import pl.coderslab.servicestation.services.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping
    public String allEmployees() {
        return "employees/employeesList";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/add")
    public String addEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employees/addEmployee";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/add-action")
    public String addEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "employees/addEmployee";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/create-user/{employeeId}")
    public String addUserToEmployee(Model model, @PathVariable("employeeId") Long employeeId) {
        model.addAttribute("employeeId", employeeId);
        model.addAttribute("user", new User());
        return "/employees/createUserForm";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/create-user-action/{employeeId}")
    public String addUserToEmployeeAction(Model model, @PathVariable("employeeId") Long employeeId,
                                          @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/employees/createUserForm";
        }
        user.setEnabled(1);
        userService.saveUser(user);
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

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
