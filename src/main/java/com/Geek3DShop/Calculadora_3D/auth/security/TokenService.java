package com.Geek3DShop.Calculadora_3D.auth.security;

import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class TokenService {
    @Value("${JWT_SECRET}")
    private String secret;

    private static final String ISSUER = "Calculadora-3D";

    public String generateToken(Usuario usuario) {
        Instant now = Instant.now();

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(usuario.getUuid().toString())
                .withClaim("papel", usuario.getPapel().name())
                .withIssuedAt(now)
                .withExpiresAt(now.plus(2, ChronoUnit.HOURS))
                .sign(algorithm);
    }

    public Optional<String> validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String subject = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();

            return Optional.ofNullable(subject);

        } catch (JWTVerificationException exception) {
            return Optional.empty();
        }
    }
}
