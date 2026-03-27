package com.tickets.tickets_backend.modelos.dtos.categoriaIncidencia;

public class DTOActualizarCategoriaIncidenciaRequest {
    private String nombre;
    private String colorHex;
    private String icono;

    public DTOActualizarCategoriaIncidenciaRequest() {
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
}
