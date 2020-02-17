package pl.coderslab.servicestation.services;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.servicestation.models.Customer;
import pl.coderslab.servicestation.repositories.CustomerRepository;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerServiceTest {

    @Autowired
    CustomerRepository customerRepository;

    @Before
    void clearTable() {
        customerRepository.deleteAll();
    }

    @Test
    void shouldSaveNewCustomers() {
        Customer customer1 = new Customer();
        customer1.setFirstName("Bartek");
        customer1.setLastName("Kowalski");
        customer1.setPhoneNumber("1231231231");

        Customer customer2 = new Customer();
        customer2.setFirstName("Magda");
        customer2.setLastName("Nowak");
        customer2.setPhoneNumber("9879871231");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        long count = customerRepository.count();
        Assertions.assertThat(count).isEqualTo(2);
        Assertions.assertThat(customer1.getId()).isNotNull();

        Optional<Customer> c = customerRepository.findById(customer2.getId());
        Assertions.assertThat(c).isPresent();
    }

    @Test
    void shouldDeleteCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Michal");
        customer.setLastName("Karolak");
        customer.setPhoneNumber("5551234561");

        customerRepository.save(customer);
        Long id = customer.getId();

        Assertions.assertThat(customer).isNotNull();

        customerRepository.delete(customer);

        Optional<Customer> deletedCustomer = customerRepository.findById(id);

        Assertions.assertThat(deletedCustomer).isEmpty();
    }
}