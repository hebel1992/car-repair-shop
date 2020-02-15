package pl.coderslab.servicestation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.servicestation.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
