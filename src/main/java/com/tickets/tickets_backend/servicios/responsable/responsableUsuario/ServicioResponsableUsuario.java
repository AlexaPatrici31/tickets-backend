package com.tickets.tickets_backend.servicios.responsable.responsableUsuario;

import com.tickets.tickets_backend.modelos.entidades.Responsable;
import com.tickets.tickets_backend.modelos.entidades.ResponsableUsuario;
import com.tickets.tickets_backend.modelos.entidades.Usuario;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoResponsable;
import com.tickets.tickets_backend.repositorios.ResponsableRepository;
import com.tickets.tickets_backend.repositorios.ResponsableUsuarioRepository;
import com.tickets.tickets_backend.repositorios.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioResponsableUsuario {

    private final ResponsableUsuarioRepository responsableUsuarioRepository;
    private final ResponsableRepository responsableRepository;
    private final UsuarioRepository usuarioRepository;

    public ServicioResponsableUsuario(ResponsableUsuarioRepository responsableUsuarioRepository,
                                      ResponsableRepository responsableRepository,
                                      UsuarioRepository usuarioRepository) {
        this.responsableUsuarioRepository = responsableUsuarioRepository;
        this.responsableRepository = responsableRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Map<String, Object> vincular(Integer idResponsable, Integer idUsuario) {
        Responsable responsable = buscarResponsable(idResponsable);
        Usuario usuario = buscarUsuario(idUsuario);

        validarMismaComunidad(responsable, usuario);
        validarCapacidadSegunTipo(responsable, idUsuario);

        ResponsableUsuario relacionExistente = responsableUsuarioRepository
                .findByResponsable_IdResponsableAndUsuario_IdUsuario(idResponsable, idUsuario)
                .orElse(null);

        if (relacionExistente != null && Boolean.TRUE.equals(relacionExistente.getActivo())) {
            throw new RuntimeException("El vínculo entre responsable y usuario ya existe");
        }

        if (relacionExistente != null) {
            relacionExistente.setActivo(true);
            responsableUsuarioRepository.save(relacionExistente);
        } else {
            ResponsableUsuario responsableUsuario = new ResponsableUsuario();
            responsableUsuario.setResponsable(responsable);
            responsableUsuario.setUsuario(usuario);
            responsableUsuario.setActivo(true);
            responsableUsuarioRepository.save(responsableUsuario);
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("mensaje", "Vínculo creado correctamente");
        response.put("idResponsable", idResponsable);
        response.put("idUsuario", idUsuario);
        return response;
    }

    public void desvincular(Integer idResponsable, Integer idUsuario) {
        ResponsableUsuario responsableUsuario = responsableUsuarioRepository
                .findByResponsable_IdResponsableAndUsuario_IdUsuarioAndActivoTrue(idResponsable, idUsuario)
                .orElseThrow(() -> new RuntimeException("No existe vínculo activo entre responsable y usuario"));

        responsableUsuario.setActivo(false);
        responsableUsuarioRepository.save(responsableUsuario);
    }

    public List<Map<String, Object>> obtenerUsuariosDeResponsable(Integer idResponsable) {
        buscarResponsable(idResponsable);

        return responsableUsuarioRepository.findByResponsable_IdResponsableAndActivoTrue(idResponsable)
                .stream()
                .map(relacion -> construirUsuarioResponse(relacion.getUsuario()))
                .toList();
    }

    public List<Map<String, Object>> obtenerResponsablesDeUsuario(Integer idUsuario) {
        buscarUsuario(idUsuario);

        return responsableUsuarioRepository.findByUsuario_IdUsuarioAndActivoTrue(idUsuario)
                .stream()
                .map(relacion -> construirResponsableResponse(relacion.getResponsable()))
                .toList();
    }

    public Map<String, Object> existeVinculo(Integer idResponsable, Integer idUsuario) {
        boolean existe = responsableUsuarioRepository
                .existsByResponsable_IdResponsableAndUsuario_IdUsuarioAndActivoTrue(idResponsable, idUsuario);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idResponsable", idResponsable);
        response.put("idUsuario", idUsuario);
        response.put("existe", existe);
        return response;
    }

    private Responsable buscarResponsable(Integer idResponsable) {
        return responsableRepository.findById(idResponsable)
                .orElseThrow(() -> new RuntimeException("Responsable no encontrado con id: " + idResponsable));
    }

    private Usuario buscarUsuario(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));
    }

    private void validarMismaComunidad(Responsable responsable, Usuario usuario) {
        if (responsable.getIdComunidad() == null || usuario.getIdComunidad() == null) {
            throw new RuntimeException("Responsable y usuario deben tener comunidad asignada");
        }

        if (!responsable.getIdComunidad().equals(usuario.getIdComunidad())) {
            throw new RuntimeException("Responsable y usuario deben pertenecer a la misma comunidad");
        }
    }

    private void validarCapacidadSegunTipo(Responsable responsable, Integer idUsuarioNuevo) {
        if (responsable.getTipoResponsable() == TipoResponsable.USUARIO) {
            List<ResponsableUsuario> vinculadosActivos =
                    responsableUsuarioRepository.findByResponsable_IdResponsableAndActivoTrue(responsable.getIdResponsable());

            boolean yaTieneOtroUsuario = vinculadosActivos.stream()
                    .anyMatch(relacion -> !relacion.getUsuario().getIdUsuario().equals(idUsuarioNuevo));

            if (yaTieneOtroUsuario) {
                throw new RuntimeException("Un responsable de tipo USUARIO solo puede tener un usuario vinculado");
            }
        }
    }

    private Map<String, Object> construirUsuarioResponse(Usuario usuario) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idUsuario", usuario.getIdUsuario());
        response.put("idComunidad", usuario.getIdComunidad());
        response.put("tipoUsuario", usuario.getTipoUsuario());
        response.put("nombre", usuario.getNombre());
        response.put("apellido", usuario.getApellido());
        response.put("correo", usuario.getCorreo());
        response.put("telefono", usuario.getTelefono());
        response.put("estado", usuario.getEstado());
        return response;
    }

    private Map<String, Object> construirResponsableResponse(Responsable responsable) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idResponsable", responsable.getIdResponsable());
        response.put("idComunidad", responsable.getIdComunidad());
        response.put("tipoResponsable", responsable.getTipoResponsable());
        response.put("descripcion", responsable.getDescripcion());
        response.put("activo", responsable.getActivo());
        return response;
    }
}
