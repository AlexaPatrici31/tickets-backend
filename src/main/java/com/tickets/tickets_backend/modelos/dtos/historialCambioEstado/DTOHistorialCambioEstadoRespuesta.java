package com.tickets.tickets_backend.modelos.dtos.historialCambioEstado;

public class DTOHistorialCambioEstadoRespuesta extends DTOHistorialCambioEstadoActualizar{
    private Integer idHistorial;

    public Integer getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }
}
