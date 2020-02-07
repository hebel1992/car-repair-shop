package pl.coderslab.servicestation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.servicestation.models.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT o FROM Order o WHERE o.status.id=1")
    List<Order> findPlannedOrders();

    @Query(value = "SELECT o FROM Order o WHERE o.status.id=2")
    List<Order> findStartedOrders();

    @Query(value = "SELECT o FROM Order o WHERE o.status.id=3 OR o.status.id=4")
    List<Order> findFinishedAndCancelledOrders();

    @Query(value = "SELECT o FROM Order o WHERE o.vehicle.id = :vehicleId AND (o.status.id=3 OR o.status.id=4)")
    List<Order> findHistoryOrdersByVehicleId(Long vehicleId);
}