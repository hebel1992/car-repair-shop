package pl.coderslab.servicestation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.servicestation.models.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders WHERE actual_repair_start > NOW() AND status_id!=5 AND status_id!=6", nativeQuery = true)
    List<Order> findAlreadyStartedOrders();

    @Query(value = "SELECT * FROM orders WHERE actual_repair_start IS NULL AND status_id!=5 AND status_id!=6", nativeQuery = true)
    List<Order> findAwaitingOrders();
}