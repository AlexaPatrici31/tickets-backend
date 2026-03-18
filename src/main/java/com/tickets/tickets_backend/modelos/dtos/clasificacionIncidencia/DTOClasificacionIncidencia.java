package com.tickets.tickets_backend.modelos.dtos.clasificacionIncidencia;

import com.tickets.tickets_backend.modelos.enumeraciones.NivelClasificacion;
import lombok.Data;

@Data
public class DTOClasificacionIncidencia {
    private String nombre;
    private String descripcion;
    private String colorHex;
    private String icono;
    private NivelClasificacion nivelClasificacion;
    private Boolean activo;

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

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public NivelClasificacion getNivelClasificacion() {
        return nivelClasificacion;
    }

    public void setNivelClasificacion(NivelClasificacion nivelClasificacion) {
        this.nivelClasificacion = nivelClasificacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
