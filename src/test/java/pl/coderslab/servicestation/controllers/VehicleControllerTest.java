package pl.coderslab.servicestation.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderslab.servicestation.repositories.CustomerRepository;
import pl.coderslab.servicestation.repositories.VehicleRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    VehicleRepository vehicleRepository;

    @MockBean
    CustomerRepository customerRepository;

    @Test
    void testListOfCustomers() throws Exception {
        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(view().name("vehicles/vehiclesList"));
    }
}