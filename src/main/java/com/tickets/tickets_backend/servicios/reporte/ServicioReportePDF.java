package com.tickets.tickets_backend.servicios.reporte;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.tickets.tickets_backend.modelos.dtos.reporte.*;
import com.tickets.tickets_backend.modelos.entidades.Caso;
import com.tickets.tickets_backend.repositorios.CasoRepository;
import com.tickets.tickets_backend.repositorios.CasoSpecification;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioReportePDF {

    private final ServicioReporte servicioReporte;
    private final CasoRepository  casoRepository;

    private static final DeviceRgb AZUL_OSCURO = new DeviceRgb(0x15, 0x65, 0xC0);
    private static final DeviceRgb AZUL_CLARO  = new DeviceRgb(0xBB, 0xDE, 0xFB);
    private static final DeviceRgb GRIS_FONDO  = new DeviceRgb(0xF5, 0xF5, 0xF5);

    private static final DateTimeFormatter FMT_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter FMT_SOLO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ServicioReportePDF(ServicioReporte servicioReporte,
                              CasoRepository casoRepository) {
        this.servicioReporte = servicioReporte;
        this.casoRepository  = casoRepository;
    }

    // ── Punto de entrada principal ─────────────────────────────────────────────
    public byte[] generarReporteConFiltros(DTOFiltroReporte filtro) {

        // 1. Obtener casos filtrados
        List<Caso> casos = casoRepository.findAll(
                CasoSpecification.conFiltros(filtro));

        // 2. Construir PDF
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter   writer   = new PdfWriter(baos);
        PdfDocument pdf      = new PdfDocument(writer);
        Document    document = new Document(pdf);
        document.setMargins(36, 36, 36, 36);

        // Secciones
        agregarEncabezado(document,
                filtro.getNombreComunidad() != null ? filtro.getNombreComunidad() : "Comunidad");
        agregarResumenFiltros(document, filtro);
        agregarTotalesDesdeLista(document, casos);
        agregarDistribucionTipo(document, casos);
        agregarDistribucionPrioridad(document, casos);
        agregarDistribucionEstado(document, casos);
        agregarCargaResponsable(document, filtro.getIdComunidad());
        agregarDetalleCasos(document, casos);
        agregarPiePagina(document);

        document.close();
        return baos.toByteArray();
    }

    // ── Encabezado ─────────────────────────────────────────────────────────────
    private void agregarEncabezado(Document doc, String nombreComunidad) {
        doc.add(new Paragraph("TICKETS")
                .setFontColor(AZUL_OSCURO)
                .setFontSize(22)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));

        doc.add(new Paragraph("Reporte de Gestión de Incidencias Comunitarias")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.DARK_GRAY));

        doc.add(new Paragraph("Comunidad: " + nombreComunidad)
                .setFontSize(11)
                .setTextAlignment(TextAlignment.CENTER));

        doc.add(new Paragraph("Generado el: " + LocalDateTime.now().format(FMT_FECHA))
                .setFontSize(9)
                .setFontColor(ColorConstants.GRAY)
                .setTextAlignment(TextAlignment.CENTER));

        doc.add(new Paragraph("\n"));
    }

    // ── Resumen de filtros aplicados ───────────────────────────────────────────
    private void agregarResumenFiltros(Document doc, DTOFiltroReporte filtro) {
        StringBuilder sb = new StringBuilder("Filtros aplicados:  ");
        boolean hayFiltro = false;

        if (filtro.getFechaInicio() != null) {
            sb.append("Desde: ").append(filtro.getFechaInicio().format(FMT_SOLO_FECHA)).append("   ");
            hayFiltro = true;
        }
        if (filtro.getFechaFin() != null) {
            sb.append("Hasta: ").append(filtro.getFechaFin().format(FMT_SOLO_FECHA)).append("   ");
            hayFiltro = true;
        }
        if (filtro.getTipoCaso() != null && !filtro.getTipoCaso().isBlank()) {
            sb.append("Tipo: ").append(filtro.getTipoCaso()).append("   ");
            hayFiltro = true;
        }
        if (filtro.getPrioridad() != null && !filtro.getPrioridad().isBlank()) {
            sb.append("Prioridad: ").append(filtro.getPrioridad()).append("   ");
            hayFiltro = true;
        }
        if (filtro.getIdEstado() != null) {
            sb.append("Estado ID: ").append(filtro.getIdEstado()).append("   ");
            hayFiltro = true;
        }
        if (filtro.getIdResponsable() != null) {
            sb.append("Responsable ID: ").append(filtro.getIdResponsable()).append("   ");
            hayFiltro = true;
        }
        if (filtro.getIdLocalizacion() != null) {
            sb.append("Localización ID: ").append(filtro.getIdLocalizacion()).append("   ");
            hayFiltro = true;
        }
        if (!hayFiltro) {
            sb.append("Ninguno — reporte completo de la comunidad");
        }

        doc.add(new Paragraph(sb.toString())
                .setFontSize(9)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setBackgroundColor(AZUL_CLARO)
                .setPadding(6)
                .setMarginBottom(10));
    }

    // ── Totales calculados desde la lista filtrada ─────────────────────────────
    private void agregarTotalesDesdeLista(Document doc, List<Caso> casos) {
        long total    = casos.size();
        long abiertos = casos.stream().filter(c -> Boolean.TRUE.equals(c.getActivo())).count();
        long cerrados = total - abiertos;

        agregarSeccion(doc, "Resumen");
        Table tabla = new Table(UnitValue.createPercentArray(new float[]{65, 35}))
                .setWidth(UnitValue.createPercentValue(100));
        agregarFila(tabla, "Total de casos en el filtro", String.valueOf(total),  true);
        agregarFila(tabla, "Casos abiertos",               String.valueOf(abiertos), false);
        agregarFila(tabla, "Casos cerrados",               String.valueOf(cerrados), true);
        doc.add(tabla);
    }

    // ── Distribución por tipo ──────────────────────────────────────────────────
    private void agregarDistribucionTipo(Document doc, List<Caso> casos) {
        if (casos.isEmpty()) return;
        agregarSeccion(doc, "Casos por Tipo");
        Map<String, Long> agrupado = agrupar(casos,
                c -> c.getTipoCaso() != null ? c.getTipoCaso().name() : "Sin tipo");
        agregarTablaAgrupada(doc, agrupado, "Tipo", "Cantidad");
    }

    // ── Distribución por prioridad ─────────────────────────────────────────────
    private void agregarDistribucionPrioridad(Document doc, List<Caso> casos) {
        if (casos.isEmpty()) return;
        agregarSeccion(doc, "Casos por Prioridad");
        Map<String, Long> agrupado = agrupar(casos,
                c -> c.getPrioridad() != null ? c.getPrioridad().name() : "Sin prioridad");
        agregarTablaAgrupada(doc, agrupado, "Prioridad", "Cantidad");
    }

    // ── Distribución por estado ────────────────────────────────────────────────
    private void agregarDistribucionEstado(Document doc, List<Caso> casos) {
        if (casos.isEmpty()) return;
        agregarSeccion(doc, "Casos por Estado");
        Map<String, Long> agrupado = agrupar(casos,
                c -> c.getIdEstadoCasoActual() != null
                        ? "Estado #" + c.getIdEstadoCasoActual()
                        : "Sin estado");
        agregarTablaAgrupada(doc, agrupado, "Estado", "Cantidad");
    }

    // ── Carga de responsables (siempre desde BD, no depende de filtro lista) ───
    private void agregarCargaResponsable(Document doc, Integer idComunidad) {
        if (idComunidad == null) return;
        List<DTOReporteResponsableCargaTrabajoItem> carga =
                servicioReporte.cargaResponsable(idComunidad);
        if (carga.isEmpty()) return;

        agregarSeccion(doc, "Carga por Responsable");
        Table tabla = new Table(UnitValue.createPercentArray(new float[]{35, 20, 15, 15, 15}))
                .setWidth(UnitValue.createPercentValue(100));

        agregarCeldaCabecera(tabla, "Responsable");
        agregarCeldaCabecera(tabla, "Tipo");
        agregarCeldaCabecera(tabla, "Asignados");
        agregarCeldaCabecera(tabla, "En proceso");
        agregarCeldaCabecera(tabla, "Cerrados");

        boolean alt = false;
        for (DTOReporteResponsableCargaTrabajoItem item : carga) {
            DeviceRgb bg = alt ? GRIS_FONDO : null;
            agregarCelda(tabla, item.getNombreResponsable(), bg);
            agregarCelda(tabla, item.getTipoResponsable(), bg);
            agregarCelda(tabla, String.valueOf(item.getCasosAsignados()), bg);
            agregarCelda(tabla, String.valueOf(item.getCasosEnProceso()), bg);
            agregarCelda(tabla, String.valueOf(item.getCasosCerrados()), bg);
            alt = !alt;
        }
        doc.add(tabla);
    }

    // ── Detalle de casos ───────────────────────────────────────────────────────
    private void agregarDetalleCasos(Document doc, List<Caso> casos) {
        agregarSeccion(doc, "Detalle de Casos (" + casos.size() + ")");

        if (casos.isEmpty()) {
            doc.add(new Paragraph("No se encontraron casos con los filtros aplicados.")
                    .setFontSize(10)
                    .setFontColor(ColorConstants.GRAY));
            return;
        }

        Table tabla = new Table(UnitValue.createPercentArray(new float[]{18, 10, 13, 13, 46}))
                .setWidth(UnitValue.createPercentValue(100));

        agregarCeldaCabecera(tabla, "Código");
        agregarCeldaCabecera(tabla, "Tipo");
        agregarCeldaCabecera(tabla, "Prioridad");
        agregarCeldaCabecera(tabla, "Fecha");
        agregarCeldaCabecera(tabla, "Descripción");

        boolean alt = false;
        for (Caso c : casos) {
            DeviceRgb bg = alt ? GRIS_FONDO : null;
            agregarCelda(tabla, c.getCodigo(), bg);
            agregarCelda(tabla, c.getTipoCaso() != null ? c.getTipoCaso().name() : "—", bg);
            agregarCelda(tabla, c.getPrioridad() != null ? c.getPrioridad().name() : "—", bg);
            agregarCelda(tabla,
                    c.getFechaCreacion() != null
                            ? c.getFechaCreacion().format(FMT_SOLO_FECHA) : "—", bg);
            agregarCelda(tabla,
                    c.getDescripcion() != null ? c.getDescripcion() : "—", bg);
            alt = !alt;
        }
        doc.add(tabla);
    }

    // ── Pie de página ──────────────────────────────────────────────────────────
    private void agregarPiePagina(Document doc) {
        doc.add(new Paragraph("\n"));
        doc.add(new Paragraph("— Fin del reporte —")
                .setFontSize(9)
                .setFontColor(ColorConstants.GRAY)
                .setTextAlignment(TextAlignment.CENTER));
    }

    // ── Helpers de construcción ────────────────────────────────────────────────

    private void agregarSeccion(Document doc, String titulo) {
        doc.add(new Paragraph(titulo)
                .setFontSize(13)
                .setBold()
                .setFontColor(AZUL_OSCURO)
                .setMarginTop(14)
                .setMarginBottom(4));
    }

    private void agregarTablaAgrupada(Document doc, Map<String, Long> datos,
                                      String col1, String col2) {
        Table tabla = new Table(UnitValue.createPercentArray(new float[]{65, 35}))
                .setWidth(UnitValue.createPercentValue(100));
        agregarCabecera(tabla, col1, col2);
        boolean alt = false;
        for (Map.Entry<String, Long> e : datos.entrySet()) {
            agregarFila(tabla, e.getKey(), e.getValue().toString(), alt);
            alt = !alt;
        }
        doc.add(tabla);
    }

    private void agregarCabecera(Table tabla, String c1, String c2) {
        agregarCeldaCabecera(tabla, c1);
        agregarCeldaCabecera(tabla, c2);
    }

    private void agregarCeldaCabecera(Table tabla, String texto) {
        tabla.addHeaderCell(new Cell()
                .add(new Paragraph(texto).setBold().setFontColor(ColorConstants.WHITE).setFontSize(10))
                .setBackgroundColor(AZUL_OSCURO)
                .setPadding(5));
    }

    private void agregarFila(Table tabla, String c1, String c2, boolean fondo) {
        DeviceRgb bg = fondo ? GRIS_FONDO : null;
        agregarCelda(tabla, c1, bg);
        agregarCelda(tabla, c2, bg);
    }

    private void agregarCelda(Table tabla, String texto, DeviceRgb fondo) {
        Cell celda = new Cell()
                .add(new Paragraph(texto != null ? texto : "—").setFontSize(9))
                .setPadding(4);
        if (fondo != null) celda.setBackgroundColor(fondo);
        tabla.addCell(celda);
    }

    private Map<String, Long> agrupar(List<Caso> casos,
                                      java.util.function.Function<Caso, String> clasificador) {
        return casos.stream().collect(
                Collectors.groupingBy(clasificador, LinkedHashMap::new, Collectors.counting()));
    }
}

