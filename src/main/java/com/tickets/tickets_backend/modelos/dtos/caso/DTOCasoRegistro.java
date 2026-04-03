package com.tickets.tickets_backend.modelos.dtos.caso;

import com.tickets.tickets_backend.modelos.enumeraciones.Prioridad;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoArchivoAdjunto;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoCaso;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DTOCasoRegistro {
    private TipoCaso tipoCaso;
    private String codigo;
    private Integer idComunidad;
    private Integer idUsuarioSolicitante;
    private Integer idEstadoCasoActual;
    private Integer idUbicacion;
    private Integer idResponsableActual;
    private Prioridad prioridad;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaPrimerRespuesta;
    private LocalDateTime fechaAsignacion;
    private LocalDateTime fechaUltimaActualizacion;
    private LocalDateTime fechaCierre;
    private Boolean visibleComunidad;
    private TipoArchivoAdjunto tipoArchivoAdjunto;
    private String urlAdjunto;
    private String nombreAdjunto;
    private LocalDateTime fechaAdjunto;
    private Boolean activo;

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
