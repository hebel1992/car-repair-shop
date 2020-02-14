package pl.coderslab.servicestation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.coderslab.servicestation.controllers.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ServiceStationApplicationTests {

    @Autowired
    HomePageController homePageController;
    @Autowired
    CustomerController customerController;
    @Autowired
    EmployeeController employeeController;
    @Autowired
    VehicleController vehicleController;
    @Autowired
    OrderController orderController;

    @Test
    void checkIfControllersLoaded() throws Exception {
        assertThat(homePageController).isNotNull();
    }
}
