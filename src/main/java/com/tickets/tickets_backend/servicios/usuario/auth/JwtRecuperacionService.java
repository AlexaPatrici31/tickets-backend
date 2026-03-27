package com.tickets.tickets_backend.servicios.usuario.auth;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtRecuperacionService {

    private static final String SECRET = "clave-super-segura-recuperacion-2026";
    private static final long EXPIRACION_MS = 15 * 60 * 1000;

    public String generarTokenRecuperacion(Long idUsuario, String correo) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + EXPIRACION_MS);

        return Jwts.builder()
                .setSubject(correo)
                .claim("idUsuario", idUsuario)
                .claim("tipo", "recuperacion")
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public boolean esTokenRecuperacionValido(String token) {
        try {
            Claims claims = obtenerClaims(token);
            return "recuperacion".equals(claims.get("tipo"));
        } catch (Exception e) {
            return false;
        }
    }

    public String obtenerCorreo(String token) {
        return obtenerClaims(token).getSubject();
    }

    private Claims obtenerClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }
}
