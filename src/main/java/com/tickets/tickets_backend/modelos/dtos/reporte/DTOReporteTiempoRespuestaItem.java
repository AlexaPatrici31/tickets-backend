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
public class DTOReporteTiempoRespuestaItem {
    @JsonProperty("id_caso")
    private Long idCaso;

    @JsonProperty("codigo")
    private String codigo;

    @JsonProperty("fecha_creacion")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaCreacion;

    @JsonProperty("fecha_primer_respuesta")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaPrimerRespuesta;

    @JsonProperty("tiempo_respuesta_horas")
    private Double tiempoRespuestaHoras;

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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaPrimerRespuesta() {
        return fechaPrimerRespuesta;
    }

    public void setFechaPrimerRespuesta(LocalDateTime fechaPrimerRespuesta) {
        this.fechaPrimerRespuesta = fechaPrimerRespuesta;
    }

    public Double getTiempoRespuestaHoras() {
        return tiempoRespuestaHoras;
    }

    public void setTiempoRespuestaHoras(Double tiempoRespuestaHoras) {
        this.tiempoRespuestaHoras = tiempoRespuestaHoras;
    }
}
