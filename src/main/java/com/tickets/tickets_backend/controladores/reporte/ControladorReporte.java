package com.tickets.tickets_backend.controladores.reporte;

import com.tickets.tickets_backend.modelos.dtos.reporte.*;
import com.tickets.tickets_backend.servicios.reporte.ServicioReporte;
import com.tickets.tickets_backend.servicios.reporte.ServicioReportePDF;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Tag(name = "Reporte", description = "Estadísticas, métricas y exportación PDF")
@RestController
@RequestMapping("/api/v1/reporte")
public class ControladorReporte {

    private final ServicioReporte    servicioReporte;
    private final ServicioReportePDF servicioReportePDF;

    public ControladorReporte(ServicioReporte servicioReporte,
                              ServicioReportePDF servicioReportePDF) {
        this.servicioReporte    = servicioReporte;
        this.servicioReportePDF = servicioReportePDF;
    }

    // ── Dashboard comunidad ────────────────────────────────────────────────────
    @Operation(summary = "Dashboard de métricas de la comunidad")
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/comunidad/{id}/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioReporte.dashboardComunidad(id));
    }

    // ── Actividad reciente ─────────────────────────────────────────────────────
    @Operation(summary = "Últimos 10 casos actualizados de la comunidad")
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/comunidad/{id}/actividad")
    public ResponseEntity<List<DTOReporteActividadComunidadItem>> actividad(
            @PathVariable Integer id) {
        return ResponseEntity.ok(servicioReporte.actividadComunidad(id));
    }

    // ── Resumen totales ────────────────────────────────────────────────────────
    @Operation(summary = "Total, abiertos y cerrados de la comunidad")
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/casos/resumen")
    public ResponseEntity<Map<String, Object>> resumenCasos(
            @RequestParam Integer idComunidad) {
        // Usa el método existente en ServicioReporte
        return ResponseEntity.ok(servicioReporte.resumenComunidad(idComunidad));
    }

    // ── Por tipo ───────────────────────────────────────────────────────────────
    @Operation(summary = "Casos agrupados por tipo (INCIDENCIA / SERVICIO)")
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/casos/por-tipo")
    public ResponseEntity<List<DTOReporteCasosPorTipoItem>> casosPorTipo(
            @RequestParam Integer idComunidad) {
        return ResponseEntity.ok(servicioReporte.casosPorTipo(idComunidad));
    }

    // ── Por estado ─────────────────────────────────────────────────────────────
    @Operation(summary = "Casos agrupados por estado")
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/casos/por-estado")
    public ResponseEntity<List<DTOReporteCasosPorEstadoItem>> casosPorEstado(
            @RequestParam Integer idComunidad) {
        return ResponseEntity.ok(servicioReporte.casosPorEstado(idComunidad));
    }

    // ── Por prioridad ──────────────────────────────────────────────────────────
    @Operation(summary = "Casos agrupados por prioridad")
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/casos/por-prioridad")
    public ResponseEntity<List<DTOReporteCasosPorPrioridadItem>> casosPorPrioridad(
            @RequestParam Integer idComunidad) {
        return ResponseEntity.ok(servicioReporte.casosPorPrioridad(idComunidad));
    }

    // ── Por localización ───────────────────────────────────────────────────────
    @Operation(summary = "Casos agrupados por localización/zona")
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/casos/por-localizacion")
    public ResponseEntity<List<DTOReporteCasosPorLocalizacionItem>> casosPorLocalizacion(
            @RequestParam Integer idComunidad) {
        return ResponseEntity.ok(servicioReporte.casosPorLocalizacion(idComunidad));
    }

    // ── Por responsable / carga trabajo ───────────────────────────────────────
    @Operation(summary = "Carga de trabajo por responsable")
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/casos/por-responsable")
    public ResponseEntity<List<DTOReporteResponsableCargaTrabajoItem>> cargaPorResponsable(
            @RequestParam Integer idComunidad) {
        return ResponseEntity.ok(servicioReporte.cargaResponsable(idComunidad));
    }

    // Ruta alternativa usada desde home del responsable y modal de asignación
    @Operation(summary = "Carga de trabajo — ruta alternativa")
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @GetMapping("/responsable/carga-trabajo")
    public ResponseEntity<List<DTOReporteResponsableCargaTrabajoItem>> cargaTrabajoGeneral(
            @RequestParam Integer idComunidad) {
        return ResponseEntity.ok(servicioReporte.cargaResponsable(idComunidad));
    }

    // ── Incidencias por categoría ──────────────────────────────────────────────
    @Operation(summary = "Incidencias agrupadas por categoría")
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/incidencia/por-categoria")
    public ResponseEntity<List<DTOReporteIncidenciaPorCategoriaItem>> incidenciasPorCategoria(
            @RequestParam Integer idComunidad) {
        return ResponseEntity.ok(servicioReporte.incidenciasPorCategoria(idComunidad));
    }

    // ── Tiempos de primera respuesta ───────────────────────────────────────────
    @Operation(summary = "Tiempos de primera respuesta en incidencias")
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/incidencia/tiempos-respuesta")
    public ResponseEntity<List<DTOReporteTiempoRespuestaItem>> tiemposRespuesta(
            @RequestParam Integer idComunidad) {
        return ResponseEntity.ok(servicioReporte.tiemposRespuesta(idComunidad));
    }

    // ── Tiempos de cierre ──────────────────────────────────────────────────────
    @Operation(summary = "Tiempos de cierre de casos")
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/incidencia/tiempos-cierre")
    public ResponseEntity<List<DTOReporteTiempoRespuestaItem>> tiemposCierre(
            @RequestParam Integer idComunidad) {
        return ResponseEntity.ok(servicioReporte.tiemposCierre(idComunidad));
    }

    // ── Servicios por tipo ─────────────────────────────────────────────────────
    @Operation(summary = "Servicios comunitarios agrupados por tipo")
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/servicio-comunitario/por-tipo")
    public ResponseEntity<List<DTOReporteServicioPorTipoItem>> serviciosPorTipo(
            @RequestParam Integer idComunidad) {
        return ResponseEntity.ok(servicioReporte.serviciosPorTipo(idComunidad));
    }

    // ── Casos por comunidad (admin general) ────────────────────────────────────
    @Operation(summary = "Casos totales agrupados por comunidad — solo admin general")
    @PreAuthorize("hasRole('ADMINGENERAL')")
    @GetMapping("/casos/por-comunidad")
    public ResponseEntity<List<DTOReporteCasosPorComunidadItem>> casosPorComunidad() {
        // el ServicioReporte tiene casosPorComunidad(Integer), usamos null
        // para obtener todas las comunidades
        return ResponseEntity.ok(servicioReporte.casosPorComunidad(null));
    }

    // ══════════════════════════════════════════════════════════════════════════
    // EXPORTACIÓN PDF CON FILTROS
    // ══════════════════════════════════════════════════════════════════════════

    @Operation(summary = "Exportar reporte PDF con filtros opcionales",
            description = """
               Todos los parámetros son opcionales excepto el id de la comunidad (en la ruta).
               Combínalos libremente:
               - fechaInicio / fechaFin  → rango de creación (formato: yyyy-MM-dd)
               - tipoCaso               → INCIDENCIA | SERVICIO
               - prioridad              → BAJA | MEDIA | ALTA | URGENTE
               - idEstado               → ID del EstadoCaso
               - idResponsable          → ID del Responsable asignado
               - idLocalizacion         → ID de la Localización/Zona
               - nombreComunidad        → texto para el encabezado del PDF
               """)
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/comunidad/{id}/exportar-pdf")
    public ResponseEntity<byte[]> exportarPDF(
            @PathVariable Integer id,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaInicio,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaFin,

            @RequestParam(required = false) String tipoCaso,
            @RequestParam(required = false) String prioridad,
            @RequestParam(required = false) Integer idEstado,
            @RequestParam(required = false) Integer idResponsable,
            @RequestParam(required = false) Integer idLocalizacion,
            @RequestParam(required = false, defaultValue = "Comunidad") String nombreComunidad) {

        DTOFiltroReporte filtro = new DTOFiltroReporte();
        filtro.setIdComunidad(id);
        filtro.setFechaInicio(fechaInicio);
        filtro.setFechaFin(fechaFin);
        filtro.setTipoCaso(tipoCaso);
        filtro.setPrioridad(prioridad);
        filtro.setIdEstado(idEstado);
        filtro.setIdResponsable(idResponsable);
        filtro.setIdLocalizacion(idLocalizacion);
        filtro.setNombreComunidad(nombreComunidad);

        byte[] pdf = servicioReportePDF.generarReporteConFiltros(filtro);

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition",
                        "attachment; filename=\"reporte-comunidad-" + id + ".pdf\"")
                .body(pdf);
    }
}
