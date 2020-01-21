package pl.coderslab.servicestation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.servicestation.models.Order;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}