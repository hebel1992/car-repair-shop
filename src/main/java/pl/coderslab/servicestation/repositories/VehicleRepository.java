package pl.coderslab.servicestation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.servicestation.models.Vehicle;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByCustomer_Id(Long id);

    @Query(value = "SELECT v FROM Vehicle v WHERE v.brand LIKE %?1% AND v.model LIKE %?2%" +
            "AND v.plateNumber LIKE %?3%")
    List<Vehicle> findWithFilters(String brand, String model, String plate);
}
