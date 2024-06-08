package co.za.thebrighthouse.jobportal.repository;

import co.za.thebrighthouse.jobportal.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);
}