package pl.coderslab.servicestation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.servicestation.models.Employee;
import pl.coderslab.servicestation.repositories.EmployeeRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public Employee findById(Long id) {
        Employee employee = employeeRepository.findById(id).get();
        return employee;
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).get();
        employeeRepository.delete(employee);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Set<Employee> findEmployeesByOrderId(Long orderId){
        return employeeRepository.findEmployeesByOrderId(orderId);
    }

    public Set<Employee> findAllEmployeesNotInThisOrder(Long orderId){
        return employeeRepository.findAllEmployeesNotInOrder(orderId);
    }
}
