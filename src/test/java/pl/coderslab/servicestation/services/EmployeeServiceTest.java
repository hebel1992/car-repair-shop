package pl.coderslab.servicestation.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.servicestation.EntityNotFoundException;
import pl.coderslab.servicestation.models.Employee;
import pl.coderslab.servicestation.models.Role;
import pl.coderslab.servicestation.models.User;
import pl.coderslab.servicestation.repositories.RoleRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Test
    void shouldRemoveRoleAndUserWhenEmployeeDeleted() {
        Employee employee = new Employee();
        employee.setFirstName("Roger");
        employee.setLastName("Murray");
        employee.setDateOfBirth(LocalDate.of(1990, 8, 17));
        employee.setAddress("addressOfEmployee");
        employee.setPhoneNumber("4569871231");
        employee.setRatePerHour(12.0);
        employeeService.saveEmployee(employee);

        Role role = new Role();
        roleRepository.save(role);

        User user = new User();
        user.setUsername("user1");
        user.setPassword("user1");
        user.setRoles(new HashSet<Role>(Arrays.asList(role)));
        user.setEmployee(employee);
        userService.saveUser(user);

        Assertions.assertThat(employeeService.findById(1L)).isNotNull();

        employeeService.deleteEmployee(employee.getId());

        try{
            employeeService.findById(employee.getId());
        }catch (EntityNotFoundException ex){

        }

    }
}