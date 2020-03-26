package pl.coderslab.servicestation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.servicestation.EntityNotFoundException;
import pl.coderslab.servicestation.models.Customer;
import pl.coderslab.servicestation.repositories.CustomerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public void saveCustomer(Customer customer) {
        if (customer.getEmail() != null && customer.getEmail().length() == 0) {
            customer.setEmail(null);
        }

        if (customer.getAddress() != null && customer.getAddress().length() == 0) {
            customer.setAddress(null);
        }
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = findById(id);
        customerRepository.delete(customer);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Customer.class.getSimpleName()));
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findFiltered(String firstName, String lastName, String phoneNumber) {
        return customerRepository.findWithFilters(firstName, lastName, phoneNumber);
    }
}
