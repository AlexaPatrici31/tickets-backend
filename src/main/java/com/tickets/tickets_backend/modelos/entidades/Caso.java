package com.tickets.tickets_backend.modelos.entidades;

import com.tickets.tickets_backend.modelos.enumeraciones.Prioridad;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoArchivoAdjunto;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoCaso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Table(name = "casos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCaso")
    private Integer idCaso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoCaso tipoCaso;

    @Column(nullable = false, length = 50)
    private String codigo;

    @Column(name = "idComunidad")
    private Integer idComunidad;

    @Column(name = "idUsuarioSolicitante")
    private Integer idUsuarioSolicitante;

    @Column(name = "idEstadoCasoActual")
    private Integer idEstadoCasoActual;

    @Column(name = "idUbicacion")
    private Integer idUbicacion;

    @Column(name = "idResponsableActual")
    private Integer idResponsableActual;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Prioridad prioridad;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime fechaPrimerRespuesta;

    @Column(nullable = false)
    private LocalDateTime fechaAsignacion;

    @Column(nullable = false)
    private LocalDateTime fechaUltimaActualizacion;

    @Column(nullable = false)
    private LocalDateTime fechaCierre;

    @Column (nullable = false)
    private Boolean visibleComunidad = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoArchivoAdjunto tipoArchivoAdjunto;

    @Column(nullable = false, length = 500)
    private String urlAdjunto;

    @Column(nullable = false, length = 150)
    private String nombreAdjunto;

    @Column(nullable = false)
    private LocalDateTime fechaAdjunto;

    @Column(nullable = false)
    private Boolean activo = true;

    public Integer getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(Integer idCaso) {
        this.idCaso = idCaso;
    }

    public TipoCaso getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(TipoCaso tipoCaso) {
        this.tipoCaso = tipoCaso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getIdComunidad() {
        return idComunidad;
    }

    public void setIdComunidad(Integer idComunidad) {
        this.idComunidad = idComunidad;
    }

    public Integer getIdUsuarioSolicitante() {
        return idUsuarioSolicitante;
    }

    public void setIdUsuarioSolicitante(Integer idUsuarioSolicitante) {
        this.idUsuarioSolicitante = idUsuarioSolicitante;
    }

    public Integer getIdEstadoCasoActual() {
        return idEstadoCasoActual;
    }

    public void setIdEstadoCasoActual(Integer idEstadoCasoActual) {
        this.idEstadoCasoActual = idEstadoCasoActual;
    }

    public Integer getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public Integer getIdResponsableActual() {
        return idResponsableActual;
    }

    public void setIdResponsableActual(Integer idResponsableActual) {
        this.idResponsableActual = idResponsableActual;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public LocalDateTime getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(LocalDateTime fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Boolean getVisibleComunidad() {
        return visibleComunidad;
    }

    public void setVisibleComunidad(Boolean visibleComunidad) {
        this.visibleComunidad = visibleComunidad;
    }

    public TipoArchivoAdjunto getTipoArchivoAdjunto() {
        return tipoArchivoAdjunto;
    }

    public void setTipoArchivoAdjunto(TipoArchivoAdjunto tipoArchivoAdjunto) {
        this.tipoArchivoAdjunto = tipoArchivoAdjunto;
    }

    public String getUrlAdjunto() {
        return urlAdjunto;
    }

    public void setUrlAdjunto(String urlAdjunto) {
        this.urlAdjunto = urlAdjunto;
    }

    public String getNombreAdjunto() {
        return nombreAdjunto;
    }

    public void setNombreAdjunto(String nombreAdjunto) {
        this.nombreAdjunto = nombreAdjunto;
    }

    public LocalDateTime getFechaAdjunto() {
        return fechaAdjunto;
    }

    public void setFechaAdjunto(LocalDateTime fechaAdjunto) {
        this.fechaAdjunto = fechaAdjunto;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
