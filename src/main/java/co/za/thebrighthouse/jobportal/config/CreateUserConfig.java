package co.za.thebrighthouse.jobportal.config;

import co.za.thebrighthouse.jobportal.dto.Role;
import co.za.thebrighthouse.jobportal.entity.User;
import co.za.thebrighthouse.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class CreateUserConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner createUsers() {
        return args -> {
            if (userRepository.findByEmail("admin@example.com").isEmpty()) {
                User admin = User.builder()
                        .firstname("Admin")
                        .lastname("User")
                        .email("admin@example.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(admin);
            }

            if (userRepository.findByEmail("recruiter@example.com").isEmpty()) {
                User recruiter = User.builder()
                        .firstname("Recruiter")
                        .lastname("User")
                        .email("recruiter@example.com")
                        .password(passwordEncoder.encode("recruiter"))
                        .role(Role.RECRUITER)
                        .build();
                userRepository.save(recruiter);
            }
        };
    }
}