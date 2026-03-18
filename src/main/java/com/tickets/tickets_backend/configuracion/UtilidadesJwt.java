package com.tickets.tickets_backend.configuracion;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class UtilidadesJwt {

    private static final String CLAVE_SECRETA = "claveultrasecretaticketsingesoftware";
    private static final long DURACION_MS = 1000 * 60 * 60; // 1 hora

    private final Key key = Keys.hmacShaKeyFor(CLAVE_SECRETA.getBytes());

    /**
     * NUEVO: Genera un token JWT con el correo y el ROL del usuario
     */
    public String generarToken(String correoUsuario, String rol) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", rol); //  Agregar el rol al token

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(correoUsuario)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + DURACION_MS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el nombre de usuario (subject) del token
     */
    public String obtenerNombreUsuario(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     *  NUEVO: Extrae el rol del token
     */
    public String obtenerRol(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("rol", String.class);
    }

    /**
     * Verifica que el token sea válido (firma y expiración)
     */
    public boolean esTokenValido(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
