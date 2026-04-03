package com.tickets.tickets_backend.servicios.servicioComunitario;

import com.tickets.tickets_backend.modelos.dtos.servicioComunitario.DTOServicioComunitarioActualizar;
import com.tickets.tickets_backend.modelos.dtos.servicioComunitario.DTOServicioComunitarioRegistro;
import com.tickets.tickets_backend.modelos.entidades.Caso;
import com.tickets.tickets_backend.modelos.entidades.ServicioComunitario;
import com.tickets.tickets_backend.modelos.enumeraciones.EstadoEjecucion;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoCaso;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoServicio;
import com.tickets.tickets_backend.repositorios.CasoRepository;
import com.tickets.tickets_backend.repositorios.ServicioComunitarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioServicioComunitario {

    private final ServicioComunitarioRepository servicioComunitarioRepository;
    private final CasoRepository casoRepository;

    public ServicioServicioComunitario(ServicioComunitarioRepository servicioComunitarioRepository,
                                       CasoRepository casoRepository) {
        this.servicioComunitarioRepository = servicioComunitarioRepository;
        this.casoRepository                = casoRepository;
    }

    // ── CREATE (atómico: Caso + ServicioComunitario) ───────────────────────────
    @Transactional
    public Map<String, Object> crear(DTOServicioComunitarioRegistro datos) {
        // 1. Crear el Caso padre
        Caso caso = new Caso();
        caso.setTipoCaso(TipoCaso.SERVICIO);
        caso.setCodigo(datos.getCodigo());
        caso.setIdComunidad(datos.getIdComunidad());
        caso.setIdUsuarioSolicitante(datos.getIdUsuarioSolicitante());
        caso.setIdEstadoCasoActual(datos.getIdEstadoCasoActual());
        caso.setIdUbicacion(datos.getIdUbicacion());
        caso.setPrioridad(datos.getPrioridad());
        caso.setDescripcion(datos.getDescripcion());
        caso.setVisibleComunidad(datos.getVisibleComunidad() != null ? datos.getVisibleComunidad() : true);
        caso.setTipoArchivoAdjunto(datos.getTipoArchivoAdjunto());
        caso.setUrlAdjunto(datos.getUrlAdjunto());
        caso.setNombreAdjunto(datos.getNombreAdjunto());
        caso.setFechaCreacion(LocalDateTime.now());
        caso.setFechaUltimaActualizacion(LocalDateTime.now());
        if (datos.getUrlAdjunto() != null) caso.setFechaAdjunto(LocalDateTime.now());
        caso.setActivo(true);
        caso = casoRepository.save(caso);

        // 2. Crear el ServicioComunitario hijo con el mismo idCaso
        ServicioComunitario servicio = new ServicioComunitario();
        servicio.setIdCaso(caso.getIdCaso());
        servicio.setTipoServicio(datos.getTipoServicio());
        servicio.setFechaSolicitud(datos.getFechaSolicitud() != null
                ? datos.getFechaSolicitud() : LocalDateTime.now());
        servicio.setFechaProgramadaInicio(datos.getFechaProgramadaInicio());
        servicio.setFechaProgramadaFin(datos.getFechaProgramadaFin());
        servicio.setFechaEjecucionRealInicio(datos.getFechaEjecucionRealInicio());
        servicio.setFechaEjecucionRealFin(datos.getFechaEjecucionRealFin());
        servicio.setEstadoEjecucion(datos.getEstadoEjecucion() != null
                ? datos.getEstadoEjecucion() : EstadoEjecucion.PENDIENTE);
        servicio.setObservacionesEjecucion(datos.getObservacionesEjecucion());
        servicio = servicioComunitarioRepository.save(servicio);

        return construirResponse(caso, servicio);
    }

    // ── READ ───────────────────────────────────────────────────────────────────
    public Map<String, Object> obtenerPorId(Integer idCaso) {
        ServicioComunitario servicio = buscarServicio(idCaso);
        Caso caso = buscarCaso(idCaso);
        return construirResponse(caso, servicio);
    }

    public List<Map<String, Object>> obtener() {
        return servicioComunitarioRepository.findAll()
                .stream()
                .map(s -> construirResponse(buscarCaso(s.getIdCaso()), s))
                .toList();
    }

    public List<Map<String, Object>> obtenerPorUsuario(Integer idUsuario) {
        return servicioComunitarioRepository.findByUsuario(idUsuario)
                .stream()
                .map(s -> construirResponse(buscarCaso(s.getIdCaso()), s))
                .toList();
    }

    public List<Map<String, Object>> obtenerPorComunidad(Integer idComunidad) {
        return servicioComunitarioRepository.findByComunidad(idComunidad)
                .stream()
                .map(s -> construirResponse(buscarCaso(s.getIdCaso()), s))
                .toList();
    }

    public List<Map<String, Object>> obtenerEnEjecucion() {
        return servicioComunitarioRepository.findByEstadoEjecucion(EstadoEjecucion.ENPROCESO)
                .stream()
                .map(s -> construirResponse(buscarCaso(s.getIdCaso()), s))
                .toList();
    }

    public List<Map<String, Object>> obtenerCompletados() {
        return servicioComunitarioRepository.findByEstadoEjecucion(EstadoEjecucion.COMPLETADA)
                .stream()
                .map(s -> construirResponse(buscarCaso(s.getIdCaso()), s))
                .toList();
    }

    public List<Map<String, Object>> obtenerPorTipo(TipoServicio tipoServicio) {
        return servicioComunitarioRepository.findByTipoServicio(tipoServicio)
                .stream()
                .map(s -> construirResponse(buscarCaso(s.getIdCaso()), s))
                .toList();
    }

    public List<Map<String, Object>> obtenerPorResponsable(Integer idResponsable) {
        return servicioComunitarioRepository.findByResponsable(idResponsable)
                .stream()
                .map(s -> construirResponse(buscarCaso(s.getIdCaso()), s))
                .toList();
    }

    // ── UPDATE ─────────────────────────────────────────────────────────────────
    @Transactional
    public Map<String, Object> actualizar(Integer idCaso, DTOServicioComunitarioActualizar datos) {
        Caso caso = buscarCaso(idCaso);
        ServicioComunitario servicio = buscarServicio(idCaso);

        // Actualizar campos del Caso padre
        if (datos.getPrioridad()          != null) caso.setPrioridad(datos.getPrioridad());
        if (datos.getDescripcion()        != null) caso.setDescripcion(datos.getDescripcion());
        if (datos.getIdUbicacion()        != null) caso.setIdUbicacion(datos.getIdUbicacion());
        if (datos.getVisibleComunidad()   != null) caso.setVisibleComunidad(datos.getVisibleComunidad());
        if (datos.getTipoArchivoAdjunto() != null) caso.setTipoArchivoAdjunto(datos.getTipoArchivoAdjunto());
        if (datos.getUrlAdjunto()         != null) { caso.setUrlAdjunto(datos.getUrlAdjunto()); caso.setFechaAdjunto(LocalDateTime.now()); }
        if (datos.getNombreAdjunto()      != null) caso.setNombreAdjunto(datos.getNombreAdjunto());
        caso.setFechaUltimaActualizacion(LocalDateTime.now());
        caso = casoRepository.save(caso);

        // Actualizar campos propios del ServicioComunitario
        if (datos.getTipoServicio()              != null) servicio.setTipoServicio(datos.getTipoServicio());
        if (datos.getFechaSolicitud()            != null) servicio.setFechaSolicitud(datos.getFechaSolicitud());
        if (datos.getFechaProgramadaInicio()     != null) servicio.setFechaProgramadaInicio(datos.getFechaProgramadaInicio());
        if (datos.getFechaProgramadaFin()        != null) servicio.setFechaProgramadaFin(datos.getFechaProgramadaFin());
        if (datos.getFechaEjecucionRealInicio()  != null) servicio.setFechaEjecucionRealInicio(datos.getFechaEjecucionRealInicio());
        if (datos.getFechaEjecucionRealFin()     != null) servicio.setFechaEjecucionRealFin(datos.getFechaEjecucionRealFin());
        if (datos.getEstadoEjecucion()           != null) servicio.setEstadoEjecucion(datos.getEstadoEjecucion());
        if (datos.getObservacionesEjecucion()    != null) servicio.setObservacionesEjecucion(datos.getObservacionesEjecucion());
        servicio = servicioComunitarioRepository.save(servicio);

        return construirResponse(caso, servicio);
    }

    @Transactional
    public Map<String, Object> actualizarEstadoEjecucion(Integer idCaso, EstadoEjecucion nuevoEstado,
                                                         String observaciones) {
        ServicioComunitario servicio = buscarServicio(idCaso);
        Caso caso = buscarCaso(idCaso);

        servicio.setEstadoEjecucion(nuevoEstado);
        if (observaciones != null) servicio.setObservacionesEjecucion(observaciones);

        if (nuevoEstado == EstadoEjecucion.ENPROCESO && servicio.getFechaEjecucionRealInicio() == null) {
            servicio.setFechaEjecucionRealInicio(LocalDateTime.now());
        }
        if (nuevoEstado == EstadoEjecucion.COMPLETADA) {
            servicio.setFechaEjecucionRealFin(LocalDateTime.now());
            caso.setFechaCierre(LocalDateTime.now());
            caso.setActivo(false);
            casoRepository.save(caso);
        }

        servicio = servicioComunitarioRepository.save(servicio);
        return construirResponse(caso, servicio);
    }

    // ── DELETE (soft) ──────────────────────────────────────────────────────────
    @Transactional
    public void eliminar(Integer idCaso) {
        Caso caso = buscarCaso(idCaso);
        caso.setActivo(false);
        caso.setFechaUltimaActualizacion(LocalDateTime.now());
        casoRepository.save(caso);
    }

    // ── Helpers ────────────────────────────────────────────────────────────────
    private ServicioComunitario buscarServicio(Integer idCaso) {
        return servicioComunitarioRepository.findById(idCaso)
                .orElseThrow(() -> new RuntimeException("ServicioComunitario no encontrado con idCaso: " + idCaso));
    }

    private Caso buscarCaso(Integer idCaso) {
        return casoRepository.findById(idCaso)
                .orElseThrow(() -> new RuntimeException("Caso no encontrado con id: " + idCaso));
    }

    private Map<String, Object> construirResponse(Caso caso, ServicioComunitario servicio) {
        Map<String, Object> response = new LinkedHashMap<>();
        // Campos del Caso
        response.put("idCaso",                   caso.getIdCaso());
        response.put("tipoCaso",                 caso.getTipoCaso());
        response.put("codigo",                   caso.getCodigo());
        response.put("idComunidad",              caso.getIdComunidad());
        response.put("idUsuarioSolicitante",     caso.getIdUsuarioSolicitante());
        response.put("idEstadoCasoActual",       caso.getIdEstadoCasoActual());
        response.put("idUbicacion",              caso.getIdUbicacion());
        response.put("idResponsableActual",      caso.getIdResponsableActual());
        response.put("prioridad",                caso.getPrioridad());
        response.put("descripcion",              caso.getDescripcion());
        response.put("fechaCreacion",            caso.getFechaCreacion());
        response.put("fechaPrimerRespuesta",     caso.getFechaPrimerRespuesta());
        response.put("fechaAsignacion",          caso.getFechaAsignacion());
        response.put("fechaUltimaActualizacion", caso.getFechaUltimaActualizacion());
        response.put("fechaCierre",              caso.getFechaCierre());
        response.put("visibleComunidad",         caso.getVisibleComunidad());
        response.put("tipoArchivoAdjunto",       caso.getTipoArchivoAdjunto());
        response.put("urlAdjunto",               caso.getUrlAdjunto());
        response.put("nombreAdjunto",            caso.getNombreAdjunto());
        response.put("fechaAdjunto",             caso.getFechaAdjunto());
        response.put("activo",                   caso.getActivo());
        // Campos propios del ServicioComunitario
        response.put("tipoServicio",             servicio.getTipoServicio());
        response.put("fechaSolicitud",           servicio.getFechaSolicitud());
        response.put("fechaProgramadaInicio",    servicio.getFechaProgramadaInicio());
        response.put("fechaProgramadaFin",       servicio.getFechaProgramadaFin());
        response.put("fechaEjecucionRealInicio", servicio.getFechaEjecucionRealInicio());
        response.put("fechaEjecucionRealFin",    servicio.getFechaEjecucionRealFin());
        response.put("estadoEjecucion",          servicio.getEstadoEjecucion());
        response.put("observacionesEjecucion",   servicio.getObservacionesEjecucion());
        return response;
    }
}
