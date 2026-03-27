package com.tickets.tickets_backend.configuracion;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class UtilidadesJwt {

    @Value("${security.jwt.access-secret}")
    private String claveSecretaAccess;

    @Value("${security.jwt.refresh-secret}")
    private String claveSecretaRefresh;

    @Value("${security.jwt.recovery-secret}")
    private String claveSecretaRecuperacion;

    @Value("${security.jwt.access-expiration-ms}")
    private long duracionAccessMs;

    @Value("${security.jwt.refresh-expiration-ms}")
    private long duracionRefreshMs;

    @Value("${security.jwt.recovery-expiration-ms}")
    private long duracionRecuperacionMs;

    private Key keyAccess;
    private Key keyRefresh;
    private Key keyRecuperacion;

    @PostConstruct
    public void init() {
        validarConfiguracion(claveSecretaAccess, "security.jwt.access-secret");
        validarConfiguracion(claveSecretaRefresh, "security.jwt.refresh-secret");
        validarConfiguracion(claveSecretaRecuperacion, "security.jwt.recovery-secret");

        this.keyAccess = Keys.hmacShaKeyFor(claveSecretaAccess.getBytes(StandardCharsets.UTF_8));
        this.keyRefresh = Keys.hmacShaKeyFor(claveSecretaRefresh.getBytes(StandardCharsets.UTF_8));
        this.keyRecuperacion = Keys.hmacShaKeyFor(claveSecretaRecuperacion.getBytes(StandardCharsets.UTF_8));
    }

    public String generarAccessToken(Integer idUsuario, Integer idComunidad, String correoUsuario, String rol) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("idUsuario", idUsuario);
        claims.put("idComunidad", idComunidad);
        claims.put("rol", rol);
        claims.put("tipo", "ACCESS");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(correoUsuario)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + duracionAccessMs))
                .signWith(keyAccess, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generarRefreshToken(Integer idUsuario, String correoUsuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("idUsuario", idUsuario);
        claims.put("tipo", "REFRESH");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(correoUsuario)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + duracionRefreshMs))
                .signWith(keyRefresh, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generarTokenRecuperacion(Integer idUsuario, String correoUsuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("idUsuario", idUsuario);
        claims.put("tipo", "RECUPERACION");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(correoUsuario)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + duracionRecuperacionMs))
                .signWith(keyRecuperacion, SignatureAlgorithm.HS256)
                .compact();
    }

    public String obtenerCorreo(String accessToken) {
        Claims claims = extraerClaimsAccess(accessToken);
        validarTipoToken(claims, "ACCESS");
        return claims.getSubject();
    }

    public Integer obtenerIdUsuario(String accessToken) {
        Claims claims = extraerClaimsAccess(accessToken);
        validarTipoToken(claims, "ACCESS");
        return claims.get("idUsuario", Integer.class);
    }

    public Integer obtenerIdComunidad(String accessToken) {
        Claims claims = extraerClaimsAccess(accessToken);
        validarTipoToken(claims, "ACCESS");
        return claims.get("idComunidad", Integer.class);
    }

    public String obtenerRol(String accessToken) {
        Claims claims = extraerClaimsAccess(accessToken);
        validarTipoToken(claims, "ACCESS");
        return claims.get("rol", String.class);
    }

    public String extraerCorreoDeRefreshToken(String refreshToken) {
        Claims claims = extraerClaimsRefresh(refreshToken);
        validarTipoToken(claims, "REFRESH");
        return claims.getSubject();
    }

    public Integer extraerIdUsuarioDeRefreshToken(String refreshToken) {
        Claims claims = extraerClaimsRefresh(refreshToken);
        validarTipoToken(claims, "REFRESH");
        return claims.get("idUsuario", Integer.class);
    }

    public String extraerCorreoDeTokenRecuperacion(String tokenRecuperacion) {
        Claims claims = extraerClaimsRecuperacion(tokenRecuperacion);
        validarTipoToken(claims, "RECUPERACION");
        return claims.getSubject();
    }

    public Integer extraerIdUsuarioDeTokenRecuperacion(String tokenRecuperacion) {
        Claims claims = extraerClaimsRecuperacion(tokenRecuperacion);
        validarTipoToken(claims, "RECUPERACION");
        return claims.get("idUsuario", Integer.class);
    }

    public boolean esAccessTokenValido(String accessToken) {
        try {
            Claims claims = extraerClaimsAccess(accessToken);
            validarTipoToken(claims, "ACCESS");
            return !estaExpirado(claims);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean esRefreshTokenValido(String refreshToken) {
        try {
            Claims claims = extraerClaimsRefresh(refreshToken);
            validarTipoToken(claims, "REFRESH");
            return !estaExpirado(claims);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean esTokenRecuperacionValido(String tokenRecuperacion) {
        try {
            Claims claims = extraerClaimsRecuperacion(tokenRecuperacion);
            validarTipoToken(claims, "RECUPERACION");
            return !estaExpirado(claims);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean esAccessTokenValidoParaUsuario(String accessToken, String correoUsuario) {
        try {
            String correoExtraido = obtenerCorreo(accessToken);
            return correoExtraido.equals(correoUsuario) && esAccessTokenValido(accessToken);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Date obtenerFechaExpiracionAccessToken(String accessToken) {
        Claims claims = extraerClaimsAccess(accessToken);
        validarTipoToken(claims, "ACCESS");
        return claims.getExpiration();
    }

    public Date obtenerFechaExpiracionRefreshToken(String refreshToken) {
        Claims claims = extraerClaimsRefresh(refreshToken);
        validarTipoToken(claims, "REFRESH");
        return claims.getExpiration();
    }

    public Date obtenerFechaExpiracionTokenRecuperacion(String tokenRecuperacion) {
        Claims claims = extraerClaimsRecuperacion(tokenRecuperacion);
        validarTipoToken(claims, "RECUPERACION");
        return claims.getExpiration();
    }

    private Claims extraerClaimsAccess(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(keyAccess)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Claims extraerClaimsRefresh(String refreshToken) {
        return Jwts.parserBuilder()
                .setSigningKey(keyRefresh)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
    }

    private Claims extraerClaimsRecuperacion(String tokenRecuperacion) {
        return Jwts.parserBuilder()
                .setSigningKey(keyRecuperacion)
                .build()
                .parseClaimsJws(tokenRecuperacion)
                .getBody();
    }

    private boolean estaExpirado(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    private void validarTipoToken(Claims claims, String tipoEsperado) {
        String tipo = claims.get("tipo", String.class);
        if (tipo == null || !tipo.equals(tipoEsperado)) {
            throw new JwtException("Tipo de token inválido");
        }
    }

    private void validarConfiguracion(String valor, String propiedad) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException("La propiedad " + propiedad + " es obligatoria");
        }
    }
}
