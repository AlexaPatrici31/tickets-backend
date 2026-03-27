package com.tickets.tickets_backend.modelos.dtos.Localizacion;

import com.tickets.tickets_backend.modelos.enumeraciones.TipoLocalizacion;

import java.math.BigDecimal;

public class DTOCrearLocalizacionRequest {
    private Integer idComunidad;
    private Integer idLocalizacionPadre;
    private TipoLocalizacion tipoLocalizacion;
    private String nombre;
    private String direccion;
    private String referencia;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String descripcion;

    public DTOCrearLocalizacionRequest() {
    }

    public Integer getIdComunidad() {
        return idComunidad;
    }

    public void setIdComunidad(Integer idComunidad) {
        this.idComunidad = idComunidad;
    }

    public Integer getIdLocalizacionPadre() {
        return idLocalizacionPadre;
    }

    public void setIdLocalizacionPadre(Integer idLocalizacionPadre) {
        this.idLocalizacionPadre = idLocalizacionPadre;
    }

    public TipoLocalizacion getTipoLocalizacion() {
        return tipoLocalizacion;
    }

    public void setTipoLocalizacion(TipoLocalizacion tipoLocalizacion) {
        this.tipoLocalizacion = tipoLocalizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
