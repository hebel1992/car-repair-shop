package pl.coderslab.servicestation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.servicestation.models.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {

}
