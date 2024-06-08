package co.za.thebrighthouse.jobportal.config;

import co.za.thebrighthouse.jobportal.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final TokenRepository tokenRepository;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/api/auth/**").permitAll() // Public endpoints for authentication
                        .requestMatchers(GET, "/api/jobs/**").permitAll() // Public access to view jobs
                        .requestMatchers(POST, "/api/jobs/**").hasAuthority("ADMIN_CREATE")
                        .requestMatchers(PUT, "/api/jobs/**").hasAuthority("ADMIN_UPDATE")
                        .requestMatchers(DELETE, "/api/jobs/**").hasAuthority("ADMIN_DELETE")
                        .requestMatchers("/api/applicants/**").authenticated() // Only authenticated users can apply or view their applications
                        .requestMatchers(POST, "/api/applicants/**").authenticated() // Apply for jobs
                        .requestMatchers(GET, "/api/applicants/**").hasAnyAuthority("ADMIN_READ", "RECRUITER_READ")
                        .requestMatchers("/api/applications/**").hasAnyRole("ADMIN", "RECRUITER")
                        .requestMatchers(GET, "/api/applications/**").hasAnyAuthority("ADMIN_READ", "RECRUITER_READ")
                        .requestMatchers(POST, "/api/applications/**").hasAnyAuthority("ADMIN_CREATE", "RECRUITER_CREATE")
                        .requestMatchers(PUT, "/api/applications/**").hasAnyAuthority("ADMIN_UPDATE", "RECRUITER_UPDATE")
                        .requestMatchers(DELETE, "/api/applications/**").hasAnyAuthority("ADMIN_DELETE", "RECRUITER_DELETE")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .addLogoutHandler(customLogoutHandler())
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LogoutHandler customLogoutHandler() {
        return (request, response, authentication) -> {
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return;
            }
            String jwt = authHeader.substring(7);
            var storedToken = tokenRepository.findByToken(jwt)
                    .orElse(null);
            if (storedToken != null) {
                storedToken.setExpired(true);
                storedToken.setRevoked(true);
                tokenRepository.save(storedToken);
            }
        };
    }
}