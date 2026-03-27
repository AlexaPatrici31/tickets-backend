package com.tickets.tickets_backend.servicios.usuario.auth;

import com.tickets.tickets_backend.configuracion.UtilidadesJwt;
import com.tickets.tickets_backend.modelos.dtos.usuario.auth.DTOActualizarPerfilRequest;
import com.tickets.tickets_backend.modelos.dtos.usuario.auth.DTOCambiarContrasenaRequest;
import com.tickets.tickets_backend.modelos.dtos.usuario.auth.DTOLoginRequest;
import com.tickets.tickets_backend.modelos.dtos.usuario.auth.DTORecuperarContrasenaRequest;
import com.tickets.tickets_backend.modelos.dtos.usuario.auth.DTORefreshTokenRequest;
import com.tickets.tickets_backend.modelos.dtos.usuario.auth.DTORestablecerContrasenaRequest;
import com.tickets.tickets_backend.modelos.entidades.Usuario;
import com.tickets.tickets_backend.modelos.enumeraciones.EstadoUsuario;
import com.tickets.tickets_backend.repositorios.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ServicioAuth {

    private final AuthenticationManager autenticacionManager;
    private final UsuarioRepository usuarioRepository;
    private final UtilidadesJwt utilidadesJwt;
    private final PasswordEncoder passwordEncoder;

    public ServicioAuth(AuthenticationManager autenticacionManager,
                        UsuarioRepository usuarioRepository,
                        UtilidadesJwt utilidadesJwt,
                        PasswordEncoder passwordEncoder) {
        this.autenticacionManager = autenticacionManager;
        this.usuarioRepository = usuarioRepository;
        this.utilidadesJwt = utilidadesJwt;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Map<String, Object>> login(DTOLoginRequest datos) {
        var authToken = new UsernamePasswordAuthenticationToken(
                datos.getCorreo(),
                datos.getContrasena()
        );

        autenticacionManager.authenticate(authToken);

        Usuario usuario = usuarioRepository.findByCorreo(datos.getCorreo())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No existe ningún usuario con correo " + datos.getCorreo()
                ));

        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
            throw new RuntimeException("El usuario no está habilitado para iniciar sesión");
        }

        usuario.setUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario);

        String token = utilidadesJwt.generarAccessToken(
                usuario.getIdUsuario(),
                usuario.getIdComunidad(),
                usuario.getCorreo(),
                usuario.getTipoUsuario().name()
        );

        String refreshToken = utilidadesJwt.generarRefreshToken(
                usuario.getIdUsuario(),
                usuario.getCorreo()
        );

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("token", token);
        response.put("refreshToken", refreshToken);
        response.put("usuario", construirUsuarioResponse(usuario));

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, Object>> logout(String authorizationHeader) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("mensaje", "Sesión cerrada correctamente");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, Object>> refreshToken(DTORefreshTokenRequest datos) {
        if (datos.getRefreshToken() == null || datos.getRefreshToken().isBlank()) {
            throw new RuntimeException("El refreshToken es obligatorio");
        }

        if (!utilidadesJwt.esRefreshTokenValido(datos.getRefreshToken())) {
            throw new RuntimeException("El refreshToken es inválido o expiró");
        }

        String correo = utilidadesJwt.extraerCorreoDeRefreshToken(datos.getRefreshToken());

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No existe ningún usuario con correo " + correo
                ));

        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
            throw new RuntimeException("El usuario no está habilitado");
        }

        String nuevoToken = utilidadesJwt.generarAccessToken(
                usuario.getIdUsuario(),
                usuario.getIdComunidad(),
                usuario.getCorreo(),
                usuario.getTipoUsuario().name()
        );

        String nuevoRefreshToken = utilidadesJwt.generarRefreshToken(
                usuario.getIdUsuario(),
                usuario.getCorreo()
        );

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("token", nuevoToken);
        response.put("refreshToken", nuevoRefreshToken);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, Object>> cambiarContrasena(DTOCambiarContrasenaRequest datos) {
        if (datos.getContrasenaActual() == null || datos.getContrasenaActual().isBlank()) {
            throw new RuntimeException("La contraseña actual es obligatoria");
        }

        if (datos.getContrasenaNueva() == null || datos.getContrasenaNueva().isBlank()) {
            throw new RuntimeException("La contraseña nueva es obligatoria");
        }

        String correo = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No existe ningún usuario autenticado con correo " + correo
                ));

        if (!passwordEncoder.matches(datos.getContrasenaActual(), usuario.getContrasena())) {
            throw new RuntimeException("La contraseña actual es incorrecta");
        }

        usuario.setContrasena(passwordEncoder.encode(datos.getContrasenaNueva()));
        usuarioRepository.save(usuario);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("mensaje", "Contraseña actualizada correctamente");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, Object>> recuperarContrasena(DTORecuperarContrasenaRequest datos) {
        if (datos.getCorreo() != null && !datos.getCorreo().isBlank()) {
            usuarioRepository.findByCorreo(datos.getCorreo()).ifPresent(usuario -> {
                String tokenRecuperacion = utilidadesJwt.generarTokenRecuperacion(
                        usuario.getIdUsuario(),
                        usuario.getCorreo()
                );

                System.out.println("==================================================");
                System.out.println("TOKEN TEMPORAL DE RECUPERACIÓN");
                System.out.println("Correo: " + usuario.getCorreo());
                System.out.println("IdUsuario: " + usuario.getIdUsuario());
                System.out.println("Token: " + tokenRecuperacion);
                System.out.println("==================================================");
            });
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("mensaje", "Si el correo existe, se enviará un token temporal de recuperación");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, Object>> restablecerContrasena(DTORestablecerContrasenaRequest datos) {
        if (datos.getToken() == null || datos.getToken().isBlank()) {
            throw new RuntimeException("El token es obligatorio");
        }

        if (datos.getContrasenaNueva() == null || datos.getContrasenaNueva().isBlank()) {
            throw new RuntimeException("La contraseña nueva es obligatoria");
        }

        if (!utilidadesJwt.esTokenRecuperacionValido(datos.getToken())) {
            throw new RuntimeException("El token de recuperación es inválido o expiró");
        }

        String correo = utilidadesJwt.extraerCorreoDeTokenRecuperacion(datos.getToken());

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No existe ningún usuario con correo " + correo
                ));

        usuario.setContrasena(passwordEncoder.encode(datos.getContrasenaNueva()));
        usuarioRepository.save(usuario);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("mensaje", "Contraseña restablecida correctamente");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, Object>> obtenerPerfil() {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No existe ningún usuario autenticado con correo " + correo
                ));

        return ResponseEntity.ok(construirUsuarioResponse(usuario));
    }

    public ResponseEntity<Map<String, Object>> actualizarPerfil(DTOActualizarPerfilRequest datos) {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No existe ningún usuario autenticado con correo " + correo
                ));

        usuario.setNombre(datos.getNombre());
        usuario.setApellido(datos.getApellido());
        usuario.setTelefono(datos.getTelefono());

        usuario = usuarioRepository.save(usuario);

        return ResponseEntity.ok(construirUsuarioResponse(usuario));
    }

    private Map<String, Object> construirUsuarioResponse(Usuario usuario) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idUsuario", usuario.getIdUsuario());
        response.put("idComunidad", usuario.getIdComunidad());
        response.put("tipoUsuario", usuario.getTipoUsuario().name());
        response.put("nombre", usuario.getNombre());
        response.put("apellido", usuario.getApellido());
        response.put("tipoDocumento", usuario.getTipoDocumento());
        response.put("numeroDocumento", usuario.getNumeroDocumento());
        response.put("correo", usuario.getCorreo());
        response.put("telefono", usuario.getTelefono());
        response.put("fotoPerfilUrl", usuario.getFotoPerfilUrl());
        response.put("fechaRegistro", usuario.getFechaRegistro());
        response.put("ultimoAcceso", usuario.getUltimoAcceso());
        response.put("estado", usuario.getEstado());
        return response;
    }
}
