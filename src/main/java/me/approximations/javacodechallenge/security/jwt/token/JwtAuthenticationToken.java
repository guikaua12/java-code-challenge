package me.approximations.javacodechallenge.security.jwt.token;

import lombok.Getter;
import me.approximations.javacodechallenge.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class JwtAuthenticationToken implements Authentication {
    private final CustomUserDetails userDetails;
    private boolean authenticated;

    public JwtAuthenticationToken(CustomUserDetails userDetails, boolean authenticated) {
        this.userDetails = userDetails;
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return userDetails.getUsername();
    }

    @Override
    public Object getPrincipal() {
        return userDetails.getUser();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }
}
