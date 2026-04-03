package com.tickets.tickets_backend.servicios.incidencia;

import com.tickets.tickets_backend.modelos.dtos.incidencia.DTOIncidenciaActualizar;
import com.tickets.tickets_backend.modelos.dtos.incidencia.DTOIncidenciaRegistro;
import com.tickets.tickets_backend.modelos.entidades.Caso;
import com.tickets.tickets_backend.modelos.entidades.Incidencia;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoCaso;
import com.tickets.tickets_backend.repositorios.CasoRepository;
import com.tickets.tickets_backend.repositorios.IncidenciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioIncidencia {

    private final IncidenciaRepository incidenciaRepository;
    private final CasoRepository casoRepository;

    public ServicioIncidencia(IncidenciaRepository incidenciaRepository,
                              CasoRepository casoRepository) {
        this.incidenciaRepository = incidenciaRepository;
        this.casoRepository       = casoRepository;
    }

    // ── CREATE (atómico: Caso + Incidencia) ────────────────────────────────────
    @Transactional
    public Map<String, Object> crear(DTOIncidenciaRegistro datos) {
        // 1. Crear el Caso padre
        Caso caso = new Caso();
        caso.setTipoCaso(TipoCaso.INCIDENCIA);
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

        // 2. Crear la Incidencia hija con el mismo idCaso
        Incidencia incidencia = new Incidencia();
        incidencia.setIdCaso(caso.getIdCaso());
        incidencia.setIdCategoria(datos.getIdCategoria());
        incidencia.setIdSubcategoria(datos.getIdSubcategoria());
        incidencia = incidenciaRepository.save(incidencia);

        return construirResponse(caso, incidencia);
    }

    // ── READ ───────────────────────────────────────────────────────────────────
    public Map<String, Object> obtenerPorId(Integer idCaso) {
        Incidencia incidencia = buscarIncidencia(idCaso);
        Caso caso = buscarCaso(idCaso);
        return construirResponse(caso, incidencia);
    }

    public List<Map<String, Object>> obtenerPorUsuario(Integer idUsuario) {
        return incidenciaRepository.findByUsuario(idUsuario)
                .stream()
                .map(i -> construirResponse(buscarCaso(i.getIdCaso()), i))
                .toList();
    }

    public List<Map<String, Object>> obtenerPorComunidad(Integer idComunidad) {
        return incidenciaRepository.findByComunidad(idComunidad)
                .stream()
                .map(i -> construirResponse(buscarCaso(i.getIdCaso()), i))
                .toList();
    }

    public List<Map<String, Object>> obtenerActivasByComunidad(Integer idComunidad) {
        return incidenciaRepository.findActivasByComunidad(idComunidad)
                .stream()
                .map(i -> construirResponse(buscarCaso(i.getIdCaso()), i))
                .toList();
    }

    public List<Map<String, Object>> obtenerPorCategoria(Integer idCategoria) {
        return incidenciaRepository.findByIdCategoria(idCategoria)
                .stream()
                .map(i -> construirResponse(buscarCaso(i.getIdCaso()), i))
                .toList();
    }

    public List<Map<String, Object>> obtenerPorSubcategoria(Integer idSubcategoria) {
        return incidenciaRepository.findByIdSubcategoria(idSubcategoria)
                .stream()
                .map(i -> construirResponse(buscarCaso(i.getIdCaso()), i))
                .toList();
    }

    // ── UPDATE ─────────────────────────────────────────────────────────────────
    @Transactional
    public Map<String, Object> actualizar(Integer idCaso, DTOIncidenciaActualizar datos) {
        Caso caso = buscarCaso(idCaso);
        Incidencia incidencia = buscarIncidencia(idCaso);

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

        // Actualizar campos propios de Incidencia
        if (datos.getIdCategoria()    != null) incidencia.setIdCategoria(datos.getIdCategoria());
        if (datos.getIdSubcategoria() != null) incidencia.setIdSubcategoria(datos.getIdSubcategoria());
        incidencia = incidenciaRepository.save(incidencia);

        return construirResponse(caso, incidencia);
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
    private Incidencia buscarIncidencia(Integer idCaso) {
        return incidenciaRepository.findById(idCaso)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada con idCaso: " + idCaso));
    }

    private Caso buscarCaso(Integer idCaso) {
        return casoRepository.findById(idCaso)
                .orElseThrow(() -> new RuntimeException("Caso no encontrado con id: " + idCaso));
    }

    private Map<String, Object> construirResponse(Caso caso, Incidencia incidencia) {
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
        // Campos propios de Incidencia
        response.put("idCategoria",    incidencia.getIdCategoria());
        response.put("idSubcategoria", incidencia.getIdSubcategoria());
        return response;
    }
}
