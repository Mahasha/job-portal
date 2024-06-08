package co.za.thebrighthouse.jobportal.repository;

import co.za.thebrighthouse.jobportal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}