package pl.coderslab.servicestation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.servicestation.models.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT c FROM Customer c WHERE c.firstName LIKE %?1% AND c.lastName LIKE %?2% " +
            "AND c.phoneNumber LIKE %?3%")
    List<Customer> findWithFilters(String firstName, String lastName, String phoneNumber);
}