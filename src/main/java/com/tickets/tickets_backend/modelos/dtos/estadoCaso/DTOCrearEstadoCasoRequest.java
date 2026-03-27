package com.tickets.tickets_backend.modelos.dtos.estadoCaso;

import com.tickets.tickets_backend.modelos.enumeraciones.TipoCaso;

public class DTOCrearEstadoCasoRequest {
    private TipoCaso tipoCaso;
    private String nombre;
    private String descripcion;
    private Integer orden;
    private Boolean esFinal;

    public DTOCrearEstadoCasoRequest() {
    }

    public TipoCaso getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(TipoCaso tipoCaso) {
        this.tipoCaso = tipoCaso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Boolean getEsFinal() {
        return esFinal;
    }

    public void setEsFinal(Boolean esFinal) {
        this.esFinal = esFinal;
    }
}
