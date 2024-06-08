package co.za.thebrighthouse.jobportal.service;

import co.za.thebrighthouse.jobportal.config.JwtService;
import co.za.thebrighthouse.jobportal.dto.TokenType;
import co.za.thebrighthouse.jobportal.entity.Token;
import co.za.thebrighthouse.jobportal.entity.User;
import co.za.thebrighthouse.jobportal.repository.TokenRepository;
import co.za.thebrighthouse.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public String generateToken(UserDetails userDetails) {
        String jwt = jwtService.generateToken(userDetails);
        saveToken(userDetails, jwt);
        return jwt;
    }

    public void saveToken(UserDetails userDetails, String jwt) {
        // Retrieve the custom User entity from the database
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Token token = Token.builder()
                .token(jwt)
                .expired(false)
                .revoked(false)
                .tokenType(TokenType.BEARER)
                .user(user)
                .build();
        tokenRepository.save(token);
    }

    public void revokeToken(String jwt) {
        tokenRepository.findByToken(jwt).ifPresent(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
        });
    }
}