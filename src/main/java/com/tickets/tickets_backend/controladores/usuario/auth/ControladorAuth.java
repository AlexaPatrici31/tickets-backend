package com.tickets.tickets_backend.controladores.usuario.auth;

import com.tickets.tickets_backend.modelos.dtos.usuario.auth.DTOActualizarPerfilRequest;
import com.tickets.tickets_backend.modelos.dtos.usuario.auth.DTOCambiarContrasenaRequest;
import com.tickets.tickets_backend.modelos.dtos.usuario.auth.DTOLoginRequest;
import com.tickets.tickets_backend.modelos.dtos.usuario.auth.DTORecuperarContrasenaRequest;
import com.tickets.tickets_backend.modelos.dtos.usuario.auth.DTORefreshTokenRequest;
import com.tickets.tickets_backend.modelos.dtos.usuario.auth.DTORestablecerContrasenaRequest;
import com.tickets.tickets_backend.servicios.usuario.auth.ServicioAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Auth", description = "Operaciones de autenticación y perfil")
@RestController
@RequestMapping("/api/v1/auth")
public class ControladorAuth {

    private final ServicioAuth servicioAuth;

    public ControladorAuth(ServicioAuth servicioAuth) {
        this.servicioAuth = servicioAuth;
    }

    @Operation(summary = "Iniciar sesión")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DTOLoginRequest datos) {
        try {
            return servicioAuth.login(datos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Cerrar sesión", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        try {
            return servicioAuth.logout(authorizationHeader);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Renovar token", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody DTORefreshTokenRequest datos) {
        try {
            return servicioAuth.refreshToken(datos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Cambiar contraseña", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/cambiar-contrasena")
    public ResponseEntity<?> cambiarContrasena(@RequestBody DTOCambiarContrasenaRequest datos) {
        try {
            return servicioAuth.cambiarContrasena(datos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Solicitar recuperación de contraseña")
    @PostMapping("/recuperar-contrasena")
    public ResponseEntity<?> recuperarContrasena(@RequestBody DTORecuperarContrasenaRequest datos) {
        try {
            return servicioAuth.recuperarContrasena(datos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Restablecer contraseña")
    @PostMapping("/restablecer-contrasena")
    public ResponseEntity<?> restablecerContrasena(@RequestBody DTORestablecerContrasenaRequest datos) {
        try {
            return servicioAuth.restablecerContrasena(datos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Obtener perfil", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/perfil")
    public ResponseEntity<?> obtenerPerfil() {
        try {
            return servicioAuth.obtenerPerfil();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Actualizar perfil", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/perfil")
    public ResponseEntity<?> actualizarPerfil(@RequestBody DTOActualizarPerfilRequest datos) {
        try {
            return servicioAuth.actualizarPerfil(datos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
