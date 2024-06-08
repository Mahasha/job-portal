package co.za.thebrighthouse.jobportal.repository;

import co.za.thebrighthouse.jobportal.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
}
