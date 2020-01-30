package pl.coderslab.servicestation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.servicestation.models.Employee;

import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "SELECT * FROM employees AS e JOIN orders_employees AS oe ON e.id = oe.employee_id " +
            "WHERE oe.order_id = :orderId"
            , nativeQuery = true)
    Set<Employee> findEmployeesByOrderId(@Param("orderId") Long id);

    @Query(value = "SELECT * FROM employees AS e LEFT JOIN orders_employees AS oe ON e.id = oe.employee_id WHERE e.id NOT IN " +
            "(SELECT id FROM employees AS ee JOIN orders_employees AS oee ON ee.id = oee.employee_id WHERE oee.order_id = :orderId)"
            , nativeQuery = true)
    Set<Employee> findAllEmployeesNotInOrder(@Param("orderId") Long id);
}