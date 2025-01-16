package com.alurachallenge.forohub.infra.security;

import com.alurachallenge.forohub.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    @Value("${forohub.security.secret}")
    private String SECRET_KEY;

    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer("forohub")
                    .withSubject(usuario.getCorreoElectronico())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    public String getSubject(String token){
        DecodedJWT decodedJWT = null;
        if(token != null){
            try {
                Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer("forohub")
                        .build();

                decodedJWT = verifier.verify(token);
            } catch (JWTVerificationException exception){
                throw new RuntimeException(exception.getMessage());
            }
        }

        if(decodedJWT == null)
            throw new RuntimeException("Verifier invalido!");
        return decodedJWT.getSubject();
    }

    private Instant generarFechaExpiracion(){
        long HOURS_EXPIRATION = 2L;

        return LocalDateTime.now().plusHours(HOURS_EXPIRATION)
                .toInstant(ZoneOffset.of("-05:00"));
    }
}
