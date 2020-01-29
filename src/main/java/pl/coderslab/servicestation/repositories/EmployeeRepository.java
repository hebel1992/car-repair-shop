package pl.coderslab.servicestation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.servicestation.models.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value ="SELECT * FROM employees AS e JOIN orders_employees AS oe ON e.id = oe.employees_id " +
            "WHERE oe.order_id = :orderId"
            ,nativeQuery = true)
    List<Employee> findEmployeesByOrderId(@Param("orderId") Long id);
}