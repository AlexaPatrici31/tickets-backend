package com.tickets.tickets_backend.servicios.responsable;

import com.tickets.tickets_backend.modelos.dtos.responsable.DTOActualizarResponsableRequest;
import com.tickets.tickets_backend.modelos.dtos.responsable.DTOCrearResponsableRequest;
import com.tickets.tickets_backend.modelos.entidades.Responsable;
import com.tickets.tickets_backend.modelos.entidades.ResponsableUsuario;
import com.tickets.tickets_backend.modelos.entidades.Usuario;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoResponsable;
import com.tickets.tickets_backend.repositorios.ResponsableRepository;
import com.tickets.tickets_backend.repositorios.ResponsableUsuarioRepository;
import com.tickets.tickets_backend.repositorios.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServicioResponsable {

    private final ResponsableRepository responsableRepository;
    private final ResponsableUsuarioRepository responsableUsuarioRepository;
    private final UsuarioRepository usuarioRepository;

    public ServicioResponsable(ResponsableRepository responsableRepository,
                               ResponsableUsuarioRepository responsableUsuarioRepository,
                               UsuarioRepository usuarioRepository) {
        this.responsableRepository = responsableRepository;
        this.responsableUsuarioRepository = responsableUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Map<String, Object> crear(DTOCrearResponsableRequest datos) {
        validarDatosCrear(datos);

        Responsable responsable = new Responsable();
        responsable.setIdComunidad(datos.getIdComunidad());
        responsable.setTipoResponsable(datos.getTipoResponsable());
        responsable.setDescripcion(datos.getDescripcion());
        responsable.setActivo(true);

        responsable = responsableRepository.save(responsable);

        sincronizarUsuarios(responsable, datos.getIdsUsuarios());

        return construirResponse(responsable);
    }

    public Page<Map<String, Object>> obtener(Pageable pageable) {
        return responsableRepository.findAll(pageable)
                .map(this::construirResponse);
    }

    public Map<String, Object> obtenerPorId(Integer id) {
        Responsable responsable = buscarResponsable(id);
        return construirResponse(responsable);
    }

    @Transactional
    public Map<String, Object> actualizar(Integer id, DTOActualizarResponsableRequest datos) {
        Responsable responsable = buscarResponsable(id);

        responsable.setTipoResponsable(datos.getTipoResponsable());
        responsable.setDescripcion(datos.getDescripcion());

        responsable = responsableRepository.save(responsable);

        sincronizarUsuarios(responsable, datos.getIdsUsuarios());

        return construirResponse(responsable);
    }

    public Map<String, Object> activar(Integer id) {
        Responsable responsable = buscarResponsable(id);
        responsable.setActivo(true);
        responsable = responsableRepository.save(responsable);
        return construirResponse(responsable);
    }

    public Map<String, Object> desactivar(Integer id) {
        Responsable responsable = buscarResponsable(id);
        responsable.setActivo(false);
        responsable = responsableRepository.save(responsable);
        return construirResponse(responsable);
    }

    @Transactional
    public void eliminar(Integer id) {
        Responsable responsable = buscarResponsable(id);

        List<ResponsableUsuario> vinculos = responsableUsuarioRepository.findByResponsable_IdResponsable(id);
        responsableUsuarioRepository.deleteAll(vinculos);

        responsableRepository.delete(responsable);
    }

    public List<Map<String, Object>> obtenerPorComunidad(Integer idComunidad) {
        return responsableRepository.findByIdComunidad(idComunidad)
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerPorTipo(String tipoResponsable) {
        return responsableRepository.findByTipoResponsable(tipoResponsable)
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerActivos() {
        return responsableRepository.findByActivoTrue()
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerCasosAsignados(Integer id) {
        buscarResponsable(id);
        return List.of();
    }

    public List<Map<String, Object>> obtenerUsuarios(Integer id) {
        buscarResponsable(id);

        return responsableUsuarioRepository.findByResponsable_IdResponsable(id)
                .stream()
                .filter(ResponsableUsuario::getActivo)
                .map(ResponsableUsuario::getUsuario)
                .map(this::construirUsuarioResponse)
                .toList();
    }

    private Responsable buscarResponsable(Integer id) {
        return responsableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Responsable no encontrado con id: " + id));
    }

    private Usuario buscarUsuario(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));
    }

    private void validarDatosCrear(DTOCrearResponsableRequest datos) {
        if (datos.getIdComunidad() == null) {
            throw new RuntimeException("El idComunidad es obligatorio");
        }

        if (datos.getTipoResponsable() == null) {
            throw new RuntimeException("El tipoResponsable es obligatorio");
        }

        validarReglaUsuarios(datos.getTipoResponsable(), datos.getIdsUsuarios());
    }

    private void validarReglaUsuarios(TipoResponsable tipoResponsable, List<Integer> idsUsuarios) {
        List<Integer> ids = idsUsuarios == null ? List.of() : idsUsuarios.stream().distinct().toList();

        if (tipoResponsable == TipoResponsable.USUARIO && ids.size() > 1) {
            throw new RuntimeException("Un responsable de tipo USUARIO solo puede tener un usuario vinculado");
        }

        if (tipoResponsable == TipoResponsable.CUADRILLA && ids.isEmpty()) {
            throw new RuntimeException("Una cuadrilla debe tener al menos un usuario vinculado");
        }
    }

    private void sincronizarUsuarios(Responsable responsable, List<Integer> idsUsuarios) {
        List<Integer> idsNormalizados = idsUsuarios == null
                ? new ArrayList<>()
                : idsUsuarios.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());

        validarReglaUsuarios(responsable.getTipoResponsable(), idsNormalizados);

        List<ResponsableUsuario> existentes =
                responsableUsuarioRepository.findByResponsable_IdResponsable(responsable.getIdResponsable());

        Set<Integer> idsExistentes = existentes.stream()
                .map(v -> v.getUsuario().getIdUsuario())
                .collect(Collectors.toSet());

        for (ResponsableUsuario vinculo : existentes) {
            Integer idUsuario = vinculo.getUsuario().getIdUsuario();
            boolean debeSeguir = idsNormalizados.contains(idUsuario);

            if (!debeSeguir && Boolean.TRUE.equals(vinculo.getActivo())) {
                vinculo.setActivo(false);
                responsableUsuarioRepository.save(vinculo);
            }

            if (debeSeguir && Boolean.FALSE.equals(vinculo.getActivo())) {
                vinculo.setActivo(true);
                responsableUsuarioRepository.save(vinculo);
            }
        }

        for (Integer idUsuario : idsNormalizados) {
            if (!idsExistentes.contains(idUsuario)) {
                Usuario usuario = buscarUsuario(idUsuario);

                if (!Objects.equals(usuario.getIdComunidad(), responsable.getIdComunidad())) {
                    throw new RuntimeException("El usuario con id " + idUsuario + " no pertenece a la misma comunidad del responsable");
                }

                ResponsableUsuario nuevo = new ResponsableUsuario();
                nuevo.setResponsable(responsable);
                nuevo.setUsuario(usuario);
                nuevo.setActivo(true);

                responsableUsuarioRepository.save(nuevo);
            }
        }
    }

    private Map<String, Object> construirResponse(Responsable responsable) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idResponsable", responsable.getIdResponsable());
        response.put("idComunidad", responsable.getIdComunidad());
        response.put("tipoResponsable", responsable.getTipoResponsable());
        response.put("descripcion", responsable.getDescripcion());
        response.put("activo", responsable.getActivo());

        List<Integer> idsUsuarios = responsableUsuarioRepository.findByResponsable_IdResponsable(responsable.getIdResponsable())
                .stream()
                .filter(ResponsableUsuario::getActivo)
                .map(v -> v.getUsuario().getIdUsuario())
                .toList();

        response.put("idsUsuarios", idsUsuarios);
        return response;
    }

    private Map<String, Object> construirUsuarioResponse(Usuario usuario) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idUsuario", usuario.getIdUsuario());
        response.put("idComunidad", usuario.getIdComunidad());
        response.put("nombre", usuario.getNombre());
        response.put("apellido", usuario.getApellido());
        response.put("correo", usuario.getCorreo());
        response.put("telefono", usuario.getTelefono());
        response.put("tipoUsuario", usuario.getTipoUsuario());
        response.put("estado", usuario.getEstado());
        return response;
    }
}
