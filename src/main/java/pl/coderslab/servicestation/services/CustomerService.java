package pl.coderslab.servicestation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.servicestation.models.Customer;
import pl.coderslab.servicestation.repositories.CustomerRepository;
import pl.coderslab.servicestation.repositories.VehicleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
//        customer.getVehicles().stream()
//                .forEach(v -> {
//                    v.setCustomer(customer);
//                    vehicleRepository.save(v);
//                });
    }

    public void updateCustomer(Customer customer) {
//        if (customer.getVehicles() != null) {
//            customer.getVehicles().stream()
//                    .forEach(v -> {
//                        v.setCustomer(customer);
//                        vehicleRepository.save(v);
//                    });
//        }
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long id){
        Customer customer = customerRepository.findById(id).get();
        customerRepository.delete(customer);
    }

    public Customer findById(Long id) {
        Customer customer = customerRepository.findById(id).get();
        return customer;
    }

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }
}
