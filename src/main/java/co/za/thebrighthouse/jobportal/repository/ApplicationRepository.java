package co.za.thebrighthouse.jobportal.repository;

import co.za.thebrighthouse.jobportal.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
