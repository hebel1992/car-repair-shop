package pl.coderslab.servicestation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.servicestation.models.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders WHERE status_id=1 OR status_id=2", nativeQuery = true)
    List<Order> findPlannedOrders();

    @Query(value = "SELECT * FROM orders WHERE status_id=3 OR status_id=4", nativeQuery = true)
    List<Order> findStartedOrders();

    @Query(value = "SELECT * FROM orders WHERE status_id=5 OR status_id=6", nativeQuery = true)
    List<Order> findHistoryOrders();

}