package com.tickets.tickets_backend.modelos.entidades;

import com.tickets.tickets_backend.modelos.enumeraciones.EstadoEjecucion;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoServicio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "serviciosComunitarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioComunitario {
    @Id
    @Column(name = "idCaso")
    private Integer idCaso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoServicio tipoServicio;

    @Column(nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column(nullable = false)
    private LocalDateTime fechaProgramadaInicio;

    @Column(nullable = false)
    private LocalDateTime fechaProgramadaFin;

    @Column(nullable = false)
    private LocalDateTime fechaEjecucionRealInicio;

    @Column(nullable = false)
    private LocalDateTime fechaEjecucionRealFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoEjecucion estadoEjecucion;

    @Column(nullable = false, length = 150)
    private String observacionesEjecucion;

    public Integer getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(Integer idCaso) {
        this.idCaso = idCaso;
    }

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
