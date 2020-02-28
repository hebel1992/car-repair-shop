package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.servicestation.models.Employee;
import pl.coderslab.servicestation.models.Role;
import pl.coderslab.servicestation.models.User;
import pl.coderslab.servicestation.services.EmployeeService;
import pl.coderslab.servicestation.services.RoleService;
import pl.coderslab.servicestation.services.UserService;
import pl.coderslab.servicestation.validationGroups.UserGroup;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final UserService userService;
    private final RoleService roleService;

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
        return "redirect:/employees/create-user/" + employee.getId();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/create-user/{employeeId}")
    public String addUserToEmployee(Model model, @PathVariable("employeeId") Long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        User user = new User();
        user.setEmployee(employee);
        model.addAttribute("user", user);
        return "/employees/createUserForm";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/create-user-action")
    public String addUserToEmployeeAction(@ModelAttribute("user") @Validated({UserGroup.class, Default.class}) User user,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/employees/createUserForm";
        }

        userService.saveUser(user);
        return "redirect:/employees/details/" + user.getEmployee().getId();
    }

    @Secured("ROLE_ADMIN")
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
        User user = userService.findByEmployeeId(id);
        model.addAttribute("employee", employee);
        model.addAttribute("user", user);
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
    @GetMapping("/delete-action/{id}")
    public String deleteEmployeeAction(@PathVariable Long id, @RequestParam("action") boolean action) {
        if (action) {
            employeeService.deleteEmployee(id);
            return "redirect:/employees";
        } else {
            return "redirect:/employees/update/" + id;
        }
    }

    @ModelAttribute("employees")
    public List<Employee> getEmployees() {
        return employeeService.findAll();
    }

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return roleService.findAllRoles();
    }
}
