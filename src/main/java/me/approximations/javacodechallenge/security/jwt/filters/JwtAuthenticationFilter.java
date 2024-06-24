package me.approximations.javacodechallenge.security.jwt.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.approximations.javacodechallenge.security.CustomUserDetails;
import me.approximations.javacodechallenge.security.jwt.payload.JwtPayload;
import me.approximations.javacodechallenge.security.jwt.service.JwtService;
import me.approximations.javacodechallenge.security.jwt.token.JwtAuthenticationToken;
import me.approximations.javacodechallenge.services.UsuarioService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.replace("Bearer ", "");

        try {
            final JwtPayload jwt = jwtService.decode(token);

            final CustomUserDetails userDetails = usuarioService.loadUserByUsername(jwt.email());

            SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userDetails, true));
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException | AuthenticationException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
    }
}
