package me.approximations.javacodechallenge.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private static final PathMatcher[] PUBLIC_ENDPOINTS = {
            new PathMatcher(HttpMethod.POST, "/user/register"),
            new PathMatcher(HttpMethod.POST, "/user/login"),
            new PathMatcher(HttpMethod.GET, "/user/"),
            new PathMatcher(HttpMethod.GET, "/user/token"),
            new PathMatcher(HttpMethod.GET, "/user/{id}"),
            new PathMatcher(HttpMethod.GET, "/department/"),
            new PathMatcher(HttpMethod.GET, "/department/{id}"),
            /* swagger */
            new PathMatcher(HttpMethod.GET, "/v3/api-docs/**"),
            new PathMatcher(HttpMethod.GET, "/swagger-ui.html"),
            new PathMatcher(HttpMethod.GET, "/swagger-ui/*"),
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JWTConfigurer jwtConfigurer) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(c ->
                        configurePublicEndpoints(c)
                                .anyRequest().authenticated()
                ).apply(jwtConfigurer);

        return http.build();
    }

    private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry configurePublicEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry configurer) {
        for (final PathMatcher matcher : PUBLIC_ENDPOINTS) {
            configurer.requestMatchers(matcher.getHttpMethod(), matcher.getPatterns()).permitAll();
        }

        return configurer;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Profile("default")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    static RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("MEMBER")
                .build();
    }

    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        final DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }
}
