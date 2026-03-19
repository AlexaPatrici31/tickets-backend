package com.tickets.tickets_backend.modelos.dtos.reporte;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DTOReporteActividadComunidadItem {
    @JsonProperty("id_caso")
    private Long idCaso;

    @JsonProperty("codigo")
    private String codigo;

    @JsonProperty("tipo_caso")
    private String tipoCaso;
    // Valores: "INCIDENCIA", "SERVICIO"

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("prioridad")
    private String prioridad;
    // Valores: "BAJA", "MEDIA", "ALTA", "URGENTE"

    @JsonProperty("nombre_estado_actual")
    private String nombreEstadoActual;

    @JsonProperty("fecha_ultima_actividad")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaUltimaActividad;

    public Long getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(Long idCaso) {
        this.idCaso = idCaso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(String tipoCaso) {
        this.tipoCaso = tipoCaso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getNombreEstadoActual() {
        return nombreEstadoActual;
    }

    public void setNombreEstadoActual(String nombreEstadoActual) {
        this.nombreEstadoActual = nombreEstadoActual;
    }

    public LocalDateTime getFechaUltimaActividad() {
        return fechaUltimaActividad;
    }

    public void setFechaUltimaActividad(LocalDateTime fechaUltimaActividad) {
        this.fechaUltimaActividad = fechaUltimaActividad;
    }
}
