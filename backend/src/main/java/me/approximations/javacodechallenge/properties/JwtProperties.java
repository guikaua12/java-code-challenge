package me.approximations.javacodechallenge.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Getter
@Setter
@RequiredArgsConstructor
@Component
@ConfigurationProperties(prefix="jwt")
public class JwtProperties {
    private String secret;
    private Duration expiration;
}
