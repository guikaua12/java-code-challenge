package me.approximations.javacodechallenge.config.security;

import lombok.Getter;
import org.springframework.http.HttpMethod;


@Getter
public class PathMatcher {
    private final HttpMethod httpMethod;
    private final String[] patterns;

    public PathMatcher(HttpMethod httpMethod, String... patterns) {
        this.httpMethod = httpMethod;
        this.patterns = patterns;
    }

    public PathMatcher(String... patterns) {
        this.httpMethod = null;
        this.patterns = patterns;
    }
}
