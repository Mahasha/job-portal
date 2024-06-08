package co.za.thebrighthouse.jobportal.config;

import co.za.thebrighthouse.jobportal.entity.User;
import co.za.thebrighthouse.jobportal.repository.TokenRepository;
import co.za.thebrighthouse.jobportal.repository.UserRepository;
import co.za.thebrighthouse.jobportal.service.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final UserRepository repository;

    @Bean
    public JwtAuthenticationFilter jwtAuthFilter(JwtService jwtService, TokenRepository tokenRepository, CustomUserDetailsServiceImpl userDetailsService) {
        return new JwtAuthenticationFilter(jwtService, userDetailsService, tokenRepository);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            Optional<User> userOpt = repository.findByEmail(email);
            User user = userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                    .password(user.getPassword())
                    .authorities(user.getAuthorities())
                    .build();
        };
    }
}