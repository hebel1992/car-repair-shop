package pl.coderslab.servicestation.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.servicestation.EntityNotFoundException;
import pl.coderslab.servicestation.models.Customer;
import pl.coderslab.servicestation.models.FuelType;
import pl.coderslab.servicestation.models.Vehicle;

@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    VehicleService vehicleService;

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

        customerService.saveCustomer(customer1);
        customerService.saveCustomer(customer2);

        long count = customerService.findAll().size();
        Assertions.assertThat(count).isEqualTo(2);
        Assertions.assertThat(customer1.getId()).isNotNull();

        Customer c = customerService.findById(customer2.getId());
        Assertions.assertThat(c).isNotNull();
    }

    @Test
    void shouldDeleteCustomerWithAllHisCars() {
        Customer customer = new Customer();
        customer.setFirstName("Michal");
        customer.setLastName("Karolak");
        customer.setPhoneNumber("5551234561");

        customerService.saveCustomer(customer);
        Long customerId = customer.getId();

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Audi");
        vehicle.setModel("A6");
        vehicle.setPlateNumber("AU64 GB9");
        vehicle.setFuelType(FuelType.DIESEL);
        vehicle.setCustomer(customer);

        vehicleService.saveVehicle(vehicle);
        Long vehicleId = vehicle.getId();

        Assertions.assertThat(customer).isNotNull();

        customerService.deleteCustomer(customer.getId());

        try {
            customerService.findById(customerId);
            vehicleService.findById(vehicleId);
        } catch (EntityNotFoundException ex) {

        }
    }
}