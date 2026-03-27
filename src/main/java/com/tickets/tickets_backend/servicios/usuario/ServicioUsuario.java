package com.tickets.tickets_backend.servicios.usuario;

import com.tickets.tickets_backend.configuracion.UtilidadesJwt;
import com.tickets.tickets_backend.modelos.dtos.usuario.*;
import com.tickets.tickets_backend.modelos.entidades.Usuario;
import com.tickets.tickets_backend.modelos.enumeraciones.EstadoUsuario;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoUsuario;
import com.tickets.tickets_backend.repositorios.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioUsuario {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilidadesJwt utilidadesJwt;

    public ServicioUsuario(UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder,
                           UtilidadesJwt utilidadesJwt) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.utilidadesJwt = utilidadesJwt;
    }

    public Map<String, Object> bootstrapAdmin(DTOCrearUsuarioRequest datos) {
        boolean yaExisteAdminGeneral = usuarioRepository.existsByTipoUsuario(TipoUsuario.ADMINGENERAL);

        if (yaExisteAdminGeneral) {
            throw new RuntimeException("El bootstrap-admin ya fue ejecutado");
        }

        validarCorreoUnico(datos.getCorreo());

        Usuario usuario = construirUsuario(datos);
        usuario.setTipoUsuario(TipoUsuario.ADMINGENERAL);
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setIdComunidad(null);

        usuario = usuarioRepository.save(usuario);

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
        return response;
    }

    public Map<String, Object> crear(DTOCrearUsuarioRequest datos) {
        validarCorreoUnico(datos.getCorreo());

        if (datos.getTipoUsuario() == null) {
            throw new RuntimeException("El tipo de usuario es obligatorio");
        }

        if (datos.getTipoUsuario() != TipoUsuario.ADMINGENERAL && datos.getIdComunidad() == null) {
            throw new RuntimeException("La comunidad es obligatoria para este tipo de usuario");
        }

        Usuario usuario = construirUsuario(datos);
        usuario.setTipoUsuario(datos.getTipoUsuario());
        usuario.setEstado(EstadoUsuario.ACTIVO);

        if (datos.getTipoUsuario() == TipoUsuario.ADMINGENERAL) {
            usuario.setIdComunidad(null);
        }

        usuario = usuarioRepository.save(usuario);

        return construirUsuarioResponse(usuario);
    }

    public Map<String, Object> crearAdminComunidad(DTOCrearUsuarioRequest datos) {
        datos.setTipoUsuario(TipoUsuario.ADMINCOMUNIDAD);
        return crear(datos);
    }

    public Map<String, Object> crearResponsable(DTOCrearUsuarioRequest datos) {
        datos.setTipoUsuario(TipoUsuario.RESPONSABLE);
        return crear(datos);
    }

    public Map<String, Object> crearCiudadano(DTOCrearUsuarioRequest datos) {
        validarCorreoUnico(datos.getCorreo());

        if (datos.getIdComunidad() == null) {
            throw new RuntimeException("La comunidad es obligatoria");
        }

        Usuario usuario = construirUsuario(datos);
        usuario.setTipoUsuario(TipoUsuario.USUARIOGENERAL);
        usuario.setEstado(EstadoUsuario.ACTIVO);

        usuario = usuarioRepository.save(usuario);

        return construirUsuarioResponse(usuario);
    }

    public Page<Map<String, Object>> obtener(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(this::construirUsuarioResponse);
    }

    public Map<String, Object> obtenerPorId(Integer id) {
        Usuario usuario = buscarUsuario(id);
        return construirUsuarioResponse(usuario);
    }

    public Map<String, Object> actualizar(Integer id, DTOActualizarUsuarioRequest datos) {
        Usuario usuario = buscarUsuario(id);

        usuario.setNombre(datos.getNombre());
        usuario.setApellido(datos.getApellido());
        usuario.setTelefono(datos.getTelefono());
        usuario.setTipoDocumento(datos.getTipoDocumento());
        usuario.setNumeroDocumento(datos.getNumeroDocumento());

        usuario = usuarioRepository.save(usuario);

        return construirUsuarioResponse(usuario);
    }

    public Map<String, Object> cambiarEstado(Integer id, DTOCambiarEstadoUsuarioRequest datos) {
        Usuario usuario = buscarUsuario(id);
        usuario.setEstado(datos.getEstado());
        usuario = usuarioRepository.save(usuario);
        return construirUsuarioResponse(usuario);
    }

    public Map<String, Object> activar(Integer id) {
        Usuario usuario = buscarUsuario(id);
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario = usuarioRepository.save(usuario);
        return construirUsuarioResponse(usuario);
    }

    public Map<String, Object> suspender(Integer id) {
        Usuario usuario = buscarUsuario(id);
        usuario.setEstado(EstadoUsuario.SUSPENDIDO);
        usuario = usuarioRepository.save(usuario);
        return construirUsuarioResponse(usuario);
    }

    public Map<String, Object> eliminarLogico(Integer id) {
        Usuario usuario = buscarUsuario(id);
        usuario.setEstado(EstadoUsuario.ELIMINADO);
        usuario = usuarioRepository.save(usuario);
        return construirUsuarioResponse(usuario);
    }

    public Map<String, Object> actualizarFoto(Integer id, DTOActualizarFotoPerfilRequest datos) {
        Usuario usuario = buscarUsuario(id);
        usuario.setFotoPerfilUrl(datos.getFotoPerfilUrl());
        usuario = usuarioRepository.save(usuario);
        return construirUsuarioResponse(usuario);
    }

    public Map<String, Object> cambiarContrasena(Integer id, DTOActualizarContrasenaUsuarioRequest datos) {
        Usuario usuario = buscarUsuario(id);

        if (!passwordEncoder.matches(datos.getContrasenaActual(), usuario.getContrasena())) {
            throw new RuntimeException("La contraseña actual es incorrecta");
        }

        usuario.setContrasena(passwordEncoder.encode(datos.getContrasenaNueva()));
        usuarioRepository.save(usuario);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("mensaje", "Contraseña actualizada correctamente");
        return response;
    }

    public Map<String, Object> cambiarTipo(Integer id, DTOCambiarTipoUsuarioRequest datos) {
        Usuario usuario = buscarUsuario(id);

        if (usuario.getTipoUsuario() == TipoUsuario.ADMINGENERAL
                && datos.getTipoUsuario() != TipoUsuario.ADMINGENERAL
                && usuarioRepository.countByTipoUsuario(TipoUsuario.ADMINGENERAL) <= 1) {
            throw new RuntimeException("No se puede degradar al último ADMINGENERAL del sistema");
        }

        usuario.setTipoUsuario(datos.getTipoUsuario());

        if (datos.getTipoUsuario() == TipoUsuario.ADMINGENERAL) {
            usuario.setIdComunidad(null);
        }

        usuario = usuarioRepository.save(usuario);
        return construirUsuarioResponse(usuario);
    }

    public Map<String, Object> cambiarComunidad(Integer id, DTOCambiarComunidadUsuarioRequest datos) {
        Usuario usuario = buscarUsuario(id);

        if (usuario.getTipoUsuario() == TipoUsuario.ADMINGENERAL) {
            throw new RuntimeException("Un ADMINGENERAL no debe pertenecer a una comunidad");
        }

        usuario.setIdComunidad(datos.getIdComunidad());
        usuario = usuarioRepository.save(usuario);
        return construirUsuarioResponse(usuario);
    }

    public void eliminarFisico(Integer id) {
        Usuario usuario = buscarUsuario(id);
        usuarioRepository.delete(usuario);
    }

    public Page<Map<String, Object>> obtenerPorComunidad(Integer idComunidad, Pageable pageable) {
        return usuarioRepository.findByIdComunidad(idComunidad, pageable)
                .map(this::construirUsuarioResponse);
    }

    public List<Map<String, Object>> obtenerPorTipo(TipoUsuario tipoUsuario) {
        return usuarioRepository.findByTipoUsuario(tipoUsuario)
                .stream()
                .map(this::construirUsuarioResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerPorEstado(EstadoUsuario estado) {
        return usuarioRepository.findByEstado(estado)
                .stream()
                .map(this::construirUsuarioResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerCasos(Integer idUsuario) {
        buscarUsuario(idUsuario);
        return List.of();
    }

    public List<Map<String, Object>> obtenerResponsabilidades(Integer idUsuario) {
        buscarUsuario(idUsuario);
        return List.of();
    }

    public List<Map<String, Object>> obtenerHistorialCambios(Integer idUsuario) {
        buscarUsuario(idUsuario);
        return List.of();
    }

    private Usuario construirUsuario(DTOCrearUsuarioRequest datos) {
        Usuario usuario = new Usuario();
        usuario.setNombre(datos.getNombre());
        usuario.setApellido(datos.getApellido());
        usuario.setTipoDocumento(datos.getTipoDocumento());
        usuario.setNumeroDocumento(datos.getNumeroDocumento());
        usuario.setCorreo(datos.getCorreo());
        usuario.setTelefono(datos.getTelefono());
        usuario.setContrasena(passwordEncoder.encode(datos.getContrasena()));
        usuario.setIdComunidad(datos.getIdComunidad());
        usuario.setFotoPerfilUrl(datos.getFotoPerfilUrl());
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setUltimoAcceso(LocalDateTime.now());
        return usuario;
    }

    private Usuario buscarUsuario(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    private void validarCorreoUnico(String correo) {
        if (usuarioRepository.existsByCorreo(correo)) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }
    }

    private Map<String, Object> construirUsuarioResponse(Usuario usuario) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idUsuario", usuario.getIdUsuario());
        response.put("idComunidad", usuario.getIdComunidad());
        response.put("tipoUsuario", usuario.getTipoUsuario());
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
