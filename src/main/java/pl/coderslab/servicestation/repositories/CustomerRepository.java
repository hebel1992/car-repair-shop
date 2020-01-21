package pl.coderslab.servicestation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.servicestation.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}