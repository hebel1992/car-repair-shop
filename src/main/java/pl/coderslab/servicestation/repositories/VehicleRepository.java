package pl.coderslab.servicestation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.servicestation.models.Vehicle;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByCustomer_Id(Long id);
}
