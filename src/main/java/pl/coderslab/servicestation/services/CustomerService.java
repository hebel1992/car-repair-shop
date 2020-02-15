package pl.coderslab.servicestation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.servicestation.models.Customer;
import pl.coderslab.servicestation.repositories.CustomerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).get();
        customerRepository.delete(customer);
    }

    public Customer findById(Long id) {
        Customer customer = customerRepository.findById(id).get();
        return customer;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
