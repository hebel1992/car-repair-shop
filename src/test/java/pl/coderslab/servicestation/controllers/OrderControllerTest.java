package pl.coderslab.servicestation.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderslab.servicestation.repositories.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    VehicleRepository vehicleRepository;
    @MockBean
    OrderRepository orderRepository;
    @MockBean
    EmployeeRepository employeeRepository;
    @MockBean
    StatusRepository statusRepository;
    @MockBean
    PartRepository partRepository;
    @MockBean
    InvoiceRepository invoiceRepository;

    @Test
    void testAddingEmptyOrder() throws Exception {
        mockMvc.perform(get("/orders/add/{orderId}", "empty"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders/addOrder"));
    }
}