package me.approximations.javacodechallenge.security.jwt.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import me.approximations.javacodechallenge.dtos.TokenResponse;
import me.approximations.javacodechallenge.properties.JwtProperties;
import me.approximations.javacodechallenge.security.jwt.payload.JwtPayload;
import me.approximations.javacodechallenge.security.jwt.service.JwtService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtServiceImpl implements JwtService {
    private static final String ISSUER = "Jwt Issuer";
    private final Algorithm algorithm;
    private final JwtProperties jwtProperties;

    public JwtServiceImpl(JwtProperties jwtProperties) {
        this.algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
        this.jwtProperties = jwtProperties;
    }

    @Override
    public TokenResponse encode(JwtPayload payload) {
        final Instant expiresAt = expiresAt();

        final String token = JWT.create()
                .withIssuer(ISSUER)
                .withSubject(String.valueOf(payload.id()))
                .withClaim(JwtPayload.EMAIL, payload.email())
                .withIssuedAt(Instant.now())
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        return new TokenResponse(payload.id(), payload.email(), token, expiresAt.toEpochMilli());
    }

    private Instant expiresAt() {
        return Instant.now().plus(jwtProperties.getExpiration());
    }

    @Override
    public JwtPayload decode(String token) {
        final DecodedJWT jwt = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token);

        return new JwtPayload(Long.valueOf(jwt.getSubject()), jwt.getClaim(JwtPayload.EMAIL).asString());
    }
}
