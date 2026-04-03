package com.tickets.tickets_backend.servicios.reporte;

import com.tickets.tickets_backend.modelos.dtos.reporte.DTOFiltroReporte;
import com.tickets.tickets_backend.modelos.dtos.reporte.DTOReporteActividadComunidadItem;
import com.tickets.tickets_backend.modelos.dtos.reporte.DTOReporteCasosPorComunidadItem;
import com.tickets.tickets_backend.modelos.dtos.reporte.DTOReporteCasosPorEstadoItem;
import com.tickets.tickets_backend.modelos.dtos.reporte.DTOReporteCasosPorLocalizacionItem;
import com.tickets.tickets_backend.modelos.dtos.reporte.DTOReporteCasosPorPrioridadItem;
import com.tickets.tickets_backend.modelos.dtos.reporte.DTOReporteCasosPorTipoItem;
import com.tickets.tickets_backend.modelos.dtos.reporte.DTOReporteIncidenciaPorCategoriaItem;
import com.tickets.tickets_backend.modelos.dtos.reporte.DTOReporteResponsableCargaTrabajoItem;
import com.tickets.tickets_backend.modelos.dtos.reporte.DTOReporteServicioPorTipoItem;
import com.tickets.tickets_backend.modelos.dtos.reporte.DTOReporteTiempoRespuestaItem;
import com.tickets.tickets_backend.repositorios.CasoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServicioReporte {

    private final CasoRepository casoRepository;

    public ServicioReporte(CasoRepository casoRepository) {
        this.casoRepository = casoRepository;
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // MÉTODO QUE USA EL DASHBOARD DEL CONTROLADOR
    // ─────────────────────────────────────────────────────────────────────────────

    public Map<String, Object> dashboardComunidad(Integer idComunidad) {
        Map<String, Object> resultado = new LinkedHashMap<>();
        resultado.put("resumen", resumenComunidad(idComunidad));
        resultado.put("porEstado", casosPorEstado(idComunidad));
        resultado.put("porPrioridad", casosPorPrioridad(idComunidad));
        resultado.put("porLocalizacion", casosPorLocalizacion(idComunidad));
        resultado.put("cargaResponsable", cargaResponsable(idComunidad));
        resultado.put("actividadReciente", actividadComunidad(idComunidad));
        resultado.put("tiemposRespuesta", tiemposRespuesta(idComunidad));
        resultado.put("tiemposCierre", tiemposCierre(idComunidad));
        return resultado;
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // RESUMEN PRINCIPAL
    // ─────────────────────────────────────────────────────────────────────────────

    public Map<String, Object> resumenComunidad(Integer idComunidad) {
        Long total = casoRepository.countByComunidad(idComunidad);
        Long abiertos = casoRepository.countAbiertos(idComunidad);
        Long cerrados = casoRepository.countCerrados(idComunidad);

        Map<String, Object> mapa = new LinkedHashMap<>();
        mapa.put("total", total);
        mapa.put("abiertos", abiertos);
        mapa.put("cerrados", cerrados);
        return mapa;
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // CASOS POR ESTADO
    // ─────────────────────────────────────────────────────────────────────────────

    public List<DTOReporteCasosPorEstadoItem> casosPorEstado(Integer idComunidad) {
        return casoRepository.countPorEstadoByComunidad(idComunidad)
                .stream()
                .map(row -> {
                    DTOReporteCasosPorEstadoItem dto = new DTOReporteCasosPorEstadoItem();
                    dto.setIdEstadoCaso(toLong(row[0])); // ajusta al nombre real del campo en el DTO
                    dto.setNombreEstado((String) row[1]);
                    dto.setTipoCaso(row[2] != null ? row[2].toString() : null);
                    dto.setCantidad(toLong(row[3]));
                    return dto;
                })
                .toList();
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // CASOS POR PRIORIDAD
    // ─────────────────────────────────────────────────────────────────────────────

    public List<DTOReporteCasosPorPrioridadItem> casosPorPrioridad(Integer idComunidad) {
        return casoRepository.countPorPrioridadByComunidad(idComunidad)
                .stream()
                .map(row -> {
                    DTOReporteCasosPorPrioridadItem dto = new DTOReporteCasosPorPrioridadItem();
                    dto.setPrioridad(row[0] != null ? row[0].toString() : null);
                    dto.setCantidad(toLong(row[1]));
                    return dto;
                })
                .toList();
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // CASOS POR LOCALIZACIÓN
    // ─────────────────────────────────────────────────────────────────────────────

    public List<DTOReporteCasosPorLocalizacionItem> casosPorLocalizacion(Integer idComunidad) {
        return casoRepository.countPorLocalizacionByComunidad(idComunidad)
                .stream()
                .map(row -> {
                    DTOReporteCasosPorLocalizacionItem dto = new DTOReporteCasosPorLocalizacionItem();
                    dto.setIdLocalizacion(toLong(row[0]));
                    dto.setNombreLocalizacion((String) row[1]);
                    dto.setTipoLocalizacion(row[2] != null ? row[2].toString() : null);
                    dto.setCantidad(toLong(row[3]));
                    return dto;
                })
                .toList();
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // CARGA DE TRABAJO POR RESPONSABLE
    // ─────────────────────────────────────────────────────────────────────────────

    public List<DTOReporteResponsableCargaTrabajoItem> cargaPorResponsable(Integer idComunidad) {
        return casoRepository.cargaPorResponsable(idComunidad)
                .stream()
                .map(row -> {
                    DTOReporteResponsableCargaTrabajoItem dto = new DTOReporteResponsableCargaTrabajoItem();
                    dto.setIdResponsable(toLong(row[0]));
                    dto.setNombreResponsable((String) row[1]);
                    dto.setTipoResponsable(row[2] != null ? row[2].toString() : null);
                    dto.setCasosAsignados(toLong(row[3]));
                    dto.setCasosEnProceso(toLong(row[4]));
                    dto.setCasosCerrados(toLong(row[5]));
                    return dto;
                })
                .toList();
    }

    // Alias EXACTO que llama el controlador
    public List<DTOReporteResponsableCargaTrabajoItem> cargaResponsable(Integer idComunidad) {
        return cargaPorResponsable(idComunidad);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // ACTIVIDAD RECIENTE
    // ─────────────────────────────────────────────────────────────────────────────

    public List<DTOReporteActividadComunidadItem> actividadComunidad(Integer idComunidad) {
        int limite = 10; // últimos 10 casos
        return casoRepository.actividadReciente(idComunidad, limite)
                .stream()
                .map(row -> {
                    DTOReporteActividadComunidadItem dto = new DTOReporteActividadComunidadItem();
                    dto.setIdCaso(toLong(row[0]));
                    dto.setCodigo((String) row[1]);
                    dto.setTipoCaso(row[2] != null ? row[2].toString() : null);
                    dto.setDescripcion((String) row[3]);
                    dto.setPrioridad(row[4] != null ? row[4].toString() : null);
                    dto.setNombreEstadoActual((String) row[5]);
                    dto.setFechaUltimaActividad(toLocalDateTime(row[6]));
                    return dto;
                })
                .toList();
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // TIEMPOS DE RESPUESTA Y CIERRE
    // ─────────────────────────────────────────────────────────────────────────────

    public List<DTOReporteTiempoRespuestaItem> tiemposPrimeraRespuesta(Integer idComunidad) {
        return casoRepository.tiemposRespuesta(idComunidad)
                .stream()
                .map(row -> {
                    DTOReporteTiempoRespuestaItem dto = new DTOReporteTiempoRespuestaItem();
                    dto.setIdCaso(toLong(row[0]));
                    dto.setCodigo((String) row[1]);
                    dto.setFechaCreacion(toLocalDateTime(row[2]));
                    dto.setFechaPrimerRespuesta(toLocalDateTime(row[3]));
                    dto.setTiempoRespuestaHoras(toDouble(row[4]));
                    return dto;
                })
                .toList();
    }

    // Alias EXACTO que usa el controlador
    public List<DTOReporteTiempoRespuestaItem> tiemposRespuesta(Integer idComunidad) {
        return tiemposPrimeraRespuesta(idComunidad);
    }

    public List<DTOReporteTiempoRespuestaItem> tiemposCierre(Integer idComunidad) {
        return casoRepository.tiemposCierre(idComunidad)
                .stream()
                .map(row -> {
                    DTOReporteTiempoRespuestaItem dto = new DTOReporteTiempoRespuestaItem();
                    dto.setIdCaso(toLong(row[0]));
                    dto.setCodigo((String) row[1]);
                    dto.setFechaCreacion(toLocalDateTime(row[2]));
                    dto.setFechaPrimerRespuesta(toLocalDateTime(row[3])); // reutilizamos campo
                    dto.setTiempoRespuestaHoras(toDouble(row[4]));
                    return dto;
                })
                .toList();
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // CASOS POR COMUNIDAD
    // ─────────────────────────────────────────────────────────────────────────────

    // Versión usada por /casos/por-comunidad sin parámetros (admin general)
    public List<DTOReporteCasosPorComunidadItem> casosPorComunidad() {
        DTOFiltroReporte filtro = new DTOFiltroReporte();
        filtro.setIdComunidad(null); // todas las comunidades
        return casosPorComunidad(filtro);
    }

    // Versión que usa DTOFiltroReporte (para filtros más complejos, PDF, etc.)
    public List<DTOReporteCasosPorComunidadItem> casosPorComunidad(DTOFiltroReporte filtro) {
        return casoRepository.findAll()
                .stream()
                .filter(caso -> filtro.getIdComunidad() == null
                        || filtro.getIdComunidad().equals(caso.getIdComunidad()))
                .collect(Collectors.groupingBy(
                        c -> c.getIdComunidad(),
                        Collectors.counting()))
                .entrySet()
                .stream()
                .map(entry -> {
                    DTOReporteCasosPorComunidadItem dto = new DTOReporteCasosPorComunidadItem();
                    dto.setIdComunidad(entry.getKey().longValue());
                    dto.setCantidad(entry.getValue());
                    return dto;
                })
                .toList();
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // MÉTODOS QUE ESPERA EL CONTROLADOR PERO AÚN SIN LÓGICA DETALLADA
    // ─────────────────────────────────────────────────────────────────────────────

    // Casos por tipo (INCIDENCIA / SERVICIO)
    public List<DTOReporteCasosPorTipoItem> casosPorTipo(Integer idComunidad) {
        // TODO: implementar usando casoRepository.countPorTipoByComunidad(idComunidad)
        return List.of();
    }

    // Incidencias por categoría
    public List<DTOReporteIncidenciaPorCategoriaItem> incidenciasPorCategoria(Integer idComunidad) {
        // TODO: implementar cuando tengas categorías de incidencia
        return List.of();
    }

    // Servicios por tipo
    public List<DTOReporteServicioPorTipoItem> serviciosPorTipo(Integer idComunidad) {
        // TODO: implementar cuando definas el reporte de servicios por tipo
        return List.of();
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // HELPERS DE CASTEO
    // ─────────────────────────────────────────────────────────────────────────────

    private Long toLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Long l) {
            return l;
        }
        if (value instanceof Integer i) {
            return i.longValue();
        }
        if (value instanceof Number n) {
            return n.longValue();
        }
        return Long.parseLong(value.toString());
    }

    private Double toDouble(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Double d) {
            return d;
        }
        if (value instanceof Number n) {
            return n.doubleValue();
        }
        return Double.parseDouble(value.toString());
    }

    private LocalDateTime toLocalDateTime(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDateTime ldt) {
            return ldt;
        }
        if (value instanceof java.sql.Timestamp ts) {
            return ts.toLocalDateTime();
        }
        if (value instanceof java.util.Date date) {
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        }
        // Si llega como String ISO-8601
        return LocalDateTime.parse(value.toString());
    }
}


