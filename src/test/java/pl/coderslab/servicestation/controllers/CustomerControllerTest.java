package pl.coderslab.servicestation.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderslab.servicestation.models.Customer;
import pl.coderslab.servicestation.repositories.CustomerRepository;
import pl.coderslab.servicestation.repositories.VehicleRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CustomerRepository customerRepository;
    @MockBean
    VehicleRepository vehicleRepository;

    @Test
    void testListOfCustomers() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/customersList"));
    }

    @Test
    void testAddCustomerMethod() throws Exception {
        mockMvc.perform(get("/customers/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("customer", new Customer()))
                .andExpect(view().name("customers/addCustomer"));
    }
}