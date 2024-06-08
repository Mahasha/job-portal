package co.za.thebrighthouse.jobportal.repository;

import co.za.thebrighthouse.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
