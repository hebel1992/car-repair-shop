package pl.coderslab.servicestation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.servicestation.models.Vehicle;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByCustomer_Id(Long id);
    @Query("SELECT a FROM Vehicle a WHERE a.customer IS NULL")
    List<Vehicle> findWithoutCustomer();
}
