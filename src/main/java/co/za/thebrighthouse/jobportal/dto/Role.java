package co.za.thebrighthouse.jobportal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN(List.of("ROLE_ADMIN", "ADMIN_CREATE", "ADMIN_UPDATE", "ADMIN_DELETE", "ADMIN_READ")),
    RECRUITER(List.of("ROLE_RECRUITER", "RECRUITER_CREATE", "RECRUITER_UPDATE", "RECRUITER_DELETE", "RECRUITER_READ"));

    private final List<String> authorities;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
