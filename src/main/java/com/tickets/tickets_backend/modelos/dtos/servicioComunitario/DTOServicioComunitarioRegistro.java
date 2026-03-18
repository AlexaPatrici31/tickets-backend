package com.tickets.tickets_backend.modelos.dtos.servicioComunitario;

import com.tickets.tickets_backend.modelos.dtos.caso.DTOCasoRegistro;
import com.tickets.tickets_backend.modelos.enumeraciones.EstadoEjecucion;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoServicio;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class DTOServicioComunitarioRegistro extends DTOCasoRegistro {
    private TipoServicio tipoServicio;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaProgramadaInicio;
    private LocalDateTime fechaProgramadaFin;
    private LocalDateTime fechaEjecucionRealInicio;
    private LocalDateTime fechaEjecucionRealFin;
    private EstadoEjecucion estadoEjecucion;
    private String observacionesEjecucion;

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDateTime getFechaProgramadaInicio() {
        return fechaProgramadaInicio;
    }

    public void setFechaProgramadaInicio(LocalDateTime fechaProgramadaInicio) {
        this.fechaProgramadaInicio = fechaProgramadaInicio;
    }

    public LocalDateTime getFechaProgramadaFin() {
        return fechaProgramadaFin;
    }

    public void setFechaProgramadaFin(LocalDateTime fechaProgramadaFin) {
        this.fechaProgramadaFin = fechaProgramadaFin;
    }

    public LocalDateTime getFechaEjecucionRealInicio() {
        return fechaEjecucionRealInicio;
    }

    public void setFechaEjecucionRealInicio(LocalDateTime fechaEjecucionRealInicio) {
        this.fechaEjecucionRealInicio = fechaEjecucionRealInicio;
    }

    public LocalDateTime getFechaEjecucionRealFin() {
        return fechaEjecucionRealFin;
    }

    public void setFechaEjecucionRealFin(LocalDateTime fechaEjecucionRealFin) {
        this.fechaEjecucionRealFin = fechaEjecucionRealFin;
    }

    public EstadoEjecucion getEstadoEjecucion() {
        return estadoEjecucion;
    }

    public void setEstadoEjecucion(EstadoEjecucion estadoEjecucion) {
        this.estadoEjecucion = estadoEjecucion;
    }

    public String getObservacionesEjecucion() {
        return observacionesEjecucion;
    }

    public void setObservacionesEjecucion(String observacionesEjecucion) {
        this.observacionesEjecucion = observacionesEjecucion;
    }
}
