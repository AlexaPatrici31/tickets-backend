package com.tickets.tickets_backend.servicios.caso;

import com.tickets.tickets_backend.modelos.dtos.caso.*;
import com.tickets.tickets_backend.modelos.entidades.Caso;
import com.tickets.tickets_backend.modelos.entidades.ChatCaso;
import com.tickets.tickets_backend.modelos.entidades.HistorialCambioEstado;
import com.tickets.tickets_backend.modelos.enumeraciones.Prioridad;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoEntidad;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoCaso;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoMensaje;
import com.tickets.tickets_backend.repositorios.CasoRepository;
import com.tickets.tickets_backend.repositorios.ChatCasoRepository;
import com.tickets.tickets_backend.repositorios.HistorialCambioEstadoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioCaso {

    private final CasoRepository casoRepository;
    private final HistorialCambioEstadoRepository historialRepository;
    private final ChatCasoRepository chatCasoRepository;

    public ServicioCaso(CasoRepository casoRepository,
                        HistorialCambioEstadoRepository historialRepository,
                        ChatCasoRepository chatCasoRepository) {
        this.casoRepository    = casoRepository;
        this.historialRepository = historialRepository;
        this.chatCasoRepository = chatCasoRepository;
    }

    // ── CREATE ─────────────────────────────────────────────────────────────────
    @Transactional
    public Map<String, Object> crear(DTOCasoRegistro datos) {
        Caso caso = new Caso();
        caso.setTipoCaso(datos.getTipoCaso());
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

        registrarHistorial(TipoEntidad.CASO, caso.getIdCaso(),
                "CREACION", null,
                datos.getIdEstadoCasoActual() != null ? datos.getIdEstadoCasoActual().toString() : "INICIAL",
                datos.getIdUsuarioSolicitante(), null, null);

        registrarEventoSistema(caso.getIdCaso(), "Caso creado.");

        return construirCasoResponse(caso);
    }

    // ── READ ───────────────────────────────────────────────────────────────────
    public Map<String, Object> obtenerPorId(Integer idCaso) {
        return construirCasoResponse(buscarCaso(idCaso));
    }

    public Map<String, Object> obtenerDetalle(Integer idCaso) {
        return construirCasoResponse(buscarCaso(idCaso));
    }

    public Page<Map<String, Object>> obtenerPorComunidad(Integer idComunidad, Pageable pageable) {
        return casoRepository.findByIdComunidad(idComunidad, pageable)
                .map(this::construirCasoResponse);
    }

    public List<Map<String, Object>> obtenerPorUsuario(Integer idUsuarioSolicitante) {
        return casoRepository.findByIdUsuarioSolicitanteAndActivoTrue(idUsuarioSolicitante)
                .stream().map(this::construirCasoResponse).toList();
    }

    public List<Map<String, Object>> obtenerVisiblesComunidad(Integer idComunidad) {
        return casoRepository.findVisiblesByComunidad(idComunidad)
                .stream().map(this::construirCasoResponse).toList();
    }

    public List<Map<String, Object>> obtenerPorTipo(TipoCaso tipoCaso) {
        return casoRepository.findByTipoCaso(tipoCaso)
                .stream().map(this::construirCasoResponse).toList();
    }

    public List<Map<String, Object>> obtenerPorEstado(Integer idEstadoCasoActual) {
        return casoRepository.findByIdEstadoCasoActual(idEstadoCasoActual)
                .stream().map(this::construirCasoResponse).toList();
    }

    public List<Map<String, Object>> obtenerPorPrioridad(Prioridad prioridad) {
        return casoRepository.findByPrioridad(prioridad)
                .stream().map(this::construirCasoResponse).toList();
    }

    public List<Map<String, Object>> obtenerPorLocalizacion(Integer idUbicacion) {
        return casoRepository.findByIdUbicacion(idUbicacion)
                .stream().map(this::construirCasoResponse).toList();
    }

    public List<Map<String, Object>> obtenerActivos() {
        return casoRepository.findActivos()
                .stream().map(this::construirCasoResponse).toList();
    }

    public List<Map<String, Object>> obtenerCerrados() {
        return casoRepository.findCerrados()
                .stream().map(this::construirCasoResponse).toList();
    }

    // ── UPDATE ─────────────────────────────────────────────────────────────────
    @Transactional
    public Map<String, Object> actualizar(Integer idCaso, DTOCasoActualizar datos) {
        Caso caso = buscarCaso(idCaso);
        if (datos.getPrioridad()          != null) caso.setPrioridad(datos.getPrioridad());
        if (datos.getDescripcion()        != null) caso.setDescripcion(datos.getDescripcion());
        if (datos.getIdUbicacion()        != null) caso.setIdUbicacion(datos.getIdUbicacion());
        if (datos.getVisibleComunidad()   != null) caso.setVisibleComunidad(datos.getVisibleComunidad());
        if (datos.getTipoArchivoAdjunto() != null) caso.setTipoArchivoAdjunto(datos.getTipoArchivoAdjunto());
        if (datos.getUrlAdjunto()         != null) { caso.setUrlAdjunto(datos.getUrlAdjunto()); caso.setFechaAdjunto(LocalDateTime.now()); }
        if (datos.getNombreAdjunto()      != null) caso.setNombreAdjunto(datos.getNombreAdjunto());
        caso.setFechaUltimaActualizacion(LocalDateTime.now());
        return construirCasoResponse(casoRepository.save(caso));
    }

    @Transactional
    public Map<String, Object> asignarResponsable(Integer idCaso, DTOAsignarResponsable datos) {
        Caso caso = buscarCaso(idCaso);
        caso.setIdResponsableActual(datos.getIdResponsableActual());
        caso.setFechaAsignacion(LocalDateTime.now());
        caso.setFechaUltimaActualizacion(LocalDateTime.now());
        registrarEventoSistema(idCaso, "Responsable asignado: " + datos.getIdResponsableActual());
        return construirCasoResponse(casoRepository.save(caso));
    }

    @Transactional
    public Map<String, Object> cambiarEstado(Integer idCaso, DTOCambiarEstadoCaso datos,
                                             Integer idUsuarioResponsable,
                                             String nombreUsuario, String emailUsuario) {
        Caso caso = buscarCaso(idCaso);
        String estadoAnterior = caso.getIdEstadoCasoActual() != null
                ? caso.getIdEstadoCasoActual().toString() : null;

        caso.setIdEstadoCasoActual(datos.getIdNuevoEstado());
        caso.setFechaUltimaActualizacion(LocalDateTime.now());

        if (datos.getMotivoCambio() != null
                && datos.getMotivoCambio().equalsIgnoreCase("CERRADO")) {
            caso.setFechaCierre(LocalDateTime.now());
            caso.setActivo(false);
        }

        caso = casoRepository.save(caso);

        registrarHistorial(TipoEntidad.CASO, idCaso,
                "CAMBIO_ESTADO", estadoAnterior,
                datos.getEstadoNuevo() != null ? datos.getEstadoNuevo() : datos.getIdNuevoEstado().toString(),
                idUsuarioResponsable, nombreUsuario, emailUsuario);

        registrarEventoSistema(idCaso, "Estado: " + datos.getEstadoNuevo());

        return construirCasoResponse(caso);
    }

    // ── DELETE ─────────────────────────────────────────────────────────────────
    @Transactional
    public void eliminar(Integer idCaso) {
        Caso caso = buscarCaso(idCaso);
        caso.setActivo(false);
        caso.setFechaUltimaActualizacion(LocalDateTime.now());
        casoRepository.save(caso);
    }

    // ── Helpers ────────────────────────────────────────────────────────────────
    private Caso buscarCaso(Integer idCaso) {
        return casoRepository.findById(idCaso)
                .orElseThrow(() -> new RuntimeException("Caso no encontrado con id: " + idCaso));
    }

    private void registrarHistorial(TipoEntidad tipoEntidad, Integer idEntidad,
                                    String tipoAccion, String estadoAnterior, String estadoNuevo,
                                    Integer idUsuarioResponsable, String nombreUsuario, String email) {
        HistorialCambioEstado h = new HistorialCambioEstado();
        h.setTipoEntidad(tipoEntidad);
        h.setIdEntidad(idEntidad);
        h.setTipoAccion(tipoAccion);
        h.setEstadoAnterior(estadoAnterior);
        h.setEstadoNuevo(estadoNuevo);
        h.setFechaCambio(LocalDateTime.now());
        h.setIdUsuarioResponsable(idUsuarioResponsable);
        h.setNombreUsuarioResponsable(nombreUsuario);
        h.setEmailUsuario(email);
        historialRepository.save(h);
    }

    private void registrarEventoSistema(Integer idCaso, String mensaje) {
        ChatCaso evento = new ChatCaso();
        evento.setIdCaso(idCaso);
        evento.setIdUsuarioEmisor(null);
        evento.setTipoMensaje(TipoMensaje.EVENTOSISTEMA);
        evento.setContenido(mensaje.length() > 50 ? mensaje.substring(0, 50) : mensaje);
        evento.setEsInterno(false);
        evento.setFechaMensaje(LocalDateTime.now());
        evento.setLeido(false);
        chatCasoRepository.save(evento);
    }

    private Map<String, Object> construirCasoResponse(Caso caso) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idCaso",                  caso.getIdCaso());
        response.put("tipoCaso",                caso.getTipoCaso());
        response.put("codigo",                  caso.getCodigo());
        response.put("idComunidad",             caso.getIdComunidad());
        response.put("idUsuarioSolicitante",    caso.getIdUsuarioSolicitante());
        response.put("idEstadoCasoActual",      caso.getIdEstadoCasoActual());
        response.put("idUbicacion",             caso.getIdUbicacion());
        response.put("idResponsableActual",     caso.getIdResponsableActual());
        response.put("prioridad",               caso.getPrioridad());
        response.put("descripcion",             caso.getDescripcion());
        response.put("fechaCreacion",           caso.getFechaCreacion());
        response.put("fechaPrimerRespuesta",    caso.getFechaPrimerRespuesta());
        response.put("fechaAsignacion",         caso.getFechaAsignacion());
        response.put("fechaUltimaActualizacion",caso.getFechaUltimaActualizacion());
        response.put("fechaCierre",             caso.getFechaCierre());
        response.put("visibleComunidad",        caso.getVisibleComunidad());
        response.put("tipoArchivoAdjunto",      caso.getTipoArchivoAdjunto());
        response.put("urlAdjunto",              caso.getUrlAdjunto());
        response.put("nombreAdjunto",           caso.getNombreAdjunto());
        response.put("fechaAdjunto",            caso.getFechaAdjunto());
        response.put("activo",                  caso.getActivo());
        return response;
    }
}
