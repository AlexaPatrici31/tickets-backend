package com.tickets.tickets_backend.servicios.historialCambioEstado;

import com.tickets.tickets_backend.modelos.dtos.historialCambioEstado.DTOHistorialCambioEstadoRegistro;
import com.tickets.tickets_backend.modelos.entidades.HistorialCambioEstado;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoEntidad;
import com.tickets.tickets_backend.repositorios.HistorialCambioEstadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioHistorialCambioEstado {

    private final HistorialCambioEstadoRepository historialRepository;

    public ServicioHistorialCambioEstado(HistorialCambioEstadoRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    // ── CREATE ─────────────────────────────────────────────────────────────────
    @Transactional
    public Map<String, Object> registrar(DTOHistorialCambioEstadoRegistro datos) {
        HistorialCambioEstado h = new HistorialCambioEstado();
        h.setTipoEntidad(datos.getTipoEntidad());
        h.setIdEntidad(datos.getIdEntidad());
        h.setTipoAccion(datos.getTipoAccion() != null ? datos.getTipoAccion() : "CAMBIO_ESTADO");
        h.setEstadoAnterior(datos.getEstadoAnterior());
        h.setEstadoNuevo(datos.getEstadoNuevo());
        h.setFechaCambio(LocalDateTime.now());
        h.setIdUsuarioResponsable(datos.getIdUsuarioResponsable());
        h.setNombreUsuarioResponsable(datos.getNombreUsuarioResponsable());
        h.setEmailUsuario(datos.getEmailUsuario());
        h = historialRepository.save(h);
        return construirResponse(h);
    }

    // ── READ: línea de tiempo de un caso (stepper) ─────────────────────────────
    public List<Map<String, Object>> obtenerPorCaso(Integer idCaso) {
        return historialRepository.findByCasoOrderByFecha(idCaso)
                .stream().map(this::construirResponse).toList();
    }

    // ── READ: último estado de un caso ────────────────────────────────────────
    public Map<String, Object> obtenerUltimoPorCaso(Integer idCaso) {
        return historialRepository.findUltimoByCaso(idCaso)
                .map(this::construirResponse)
                .orElseThrow(() -> new RuntimeException(
                        "No hay historial para el caso con id: " + idCaso));
    }

    // ── READ: historial genérico por entidad ───────────────────────────────────
    public List<Map<String, Object>> obtenerPorEntidad(TipoEntidad tipoEntidad, Integer idEntidad) {
        return historialRepository
                .findByTipoEntidadAndIdEntidadOrderByFechaCambioAsc(tipoEntidad, idEntidad)
                .stream().map(this::construirResponse).toList();
    }

    // ── READ: último registro genérico por entidad ─────────────────────────────
    public Map<String, Object> obtenerUltimoPorEntidad(TipoEntidad tipoEntidad, Integer idEntidad) {
        return historialRepository
                .findTopByTipoEntidadAndIdEntidadOrderByFechaCambioDesc(tipoEntidad, idEntidad)
                .map(this::construirResponse)
                .orElseThrow(() -> new RuntimeException(
                        "No hay historial para la entidad: " + tipoEntidad + " id: " + idEntidad));
    }

    // ── READ: historial por usuario ────────────────────────────────────────────
    public List<Map<String, Object>> obtenerPorUsuario(Integer idUsuario) {
        return historialRepository.findByIdUsuarioResponsableOrderByFechaCambioDesc(idUsuario)
                .stream().map(this::construirResponse).toList();
    }

    // ── Uso interno: registrar evento desde otro servicio ─────────────────────
    // Otros servicios (Caso, Incidencia, etc.) llamarán a este método directamente
    // para registrar cambios automáticos sin pasar por el controlador.
    @Transactional
    public void registrarEvento(TipoEntidad tipoEntidad, Integer idEntidad,
                                String tipoAccion, String estadoAnterior, String estadoNuevo,
                                Integer idUsuario, String nombreUsuario, String email) {
        HistorialCambioEstado h = new HistorialCambioEstado();
        h.setTipoEntidad(tipoEntidad);
        h.setIdEntidad(idEntidad);
        h.setTipoAccion(tipoAccion);
        h.setEstadoAnterior(estadoAnterior);
        h.setEstadoNuevo(estadoNuevo);
        h.setFechaCambio(LocalDateTime.now());
        h.setIdUsuarioResponsable(idUsuario);
        h.setNombreUsuarioResponsable(nombreUsuario);
        h.setEmailUsuario(email);
        historialRepository.save(h);
    }

    // ── Helpers ────────────────────────────────────────────────────────────────
    private Map<String, Object> construirResponse(HistorialCambioEstado h) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idHistorial",              h.getIdHistorial());
        response.put("tipoEntidad",              h.getTipoEntidad());
        response.put("tipoEntidadDisplay",       h.getTipoEntidad().getDisplayName());
        response.put("idEntidad",                h.getIdEntidad());
        response.put("tipoAccion",               h.getTipoAccion());
        response.put("estadoAnterior",           h.getEstadoAnterior());
        response.put("estadoNuevo",              h.getEstadoNuevo());
        response.put("fechaCambio",              h.getFechaCambio());
        response.put("idUsuarioResponsable",     h.getIdUsuarioResponsable());
        response.put("nombreUsuarioResponsable", h.getNombreUsuarioResponsable());
        response.put("emailUsuario",             h.getEmailUsuario());
        return response;
    }
}
