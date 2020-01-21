package pl.coderslab.servicestation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.servicestation.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}