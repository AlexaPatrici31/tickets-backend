package com.tickets.tickets_backend.modelos.dtos.categoriaIncidencia;


import com.tickets.tickets_backend.modelos.enumeraciones.NivelClasificacion;

public class DTOCrearCategoriaIncidenciaRequest {
    private Integer idCategoriaPadre;
    private String nombre;
    private String colorHex;
    private String icono;
    private NivelClasificacion nivelClasificacion;

    public DTOCrearCategoriaIncidenciaRequest() {
    }

    public Integer getIdCategoriaPadre() {
        return idCategoriaPadre;
    }

    public void setIdCategoriaPadre(Integer idCategoriaPadre) {
        this.idCategoriaPadre = idCategoriaPadre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
