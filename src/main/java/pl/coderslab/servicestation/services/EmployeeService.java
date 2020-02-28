package pl.coderslab.servicestation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.servicestation.EntityNotFoundException;
import pl.coderslab.servicestation.models.Employee;
import pl.coderslab.servicestation.repositories.EmployeeRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public void saveEmployee(Employee employee) {
        if (employee.getEmail() != null && employee.getEmail().length() == 0) {
            employee.setEmail(null);
        }
        employeeRepository.save(employee);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, Employee.class.getSimpleName()));
    }

    public void deleteEmployee(Long id) {
        Employee employee = findById(id);
        employeeRepository.delete(employee);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Set<Employee> findEmployeesByOrderId(Long orderId){
        return employeeRepository.findEmployeesByOrderId(orderId);
    }
}
