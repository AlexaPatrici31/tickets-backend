package com.tickets.tickets_backend.modelos.dtos.caso;

import lombok.Data;

@Data
public class DTOCambiarEstadoCaso {
    private Integer idNuevoEstado;
    private String estadoNuevo;       // nombre del estado para el historial
    private String motivoCambio;

    public Integer getIdNuevoEstado() {
        return idNuevoEstado;
    }

    public void setIdNuevoEstado(Integer idNuevoEstado) {
        this.idNuevoEstado = idNuevoEstado;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public String getMotivoCambio() {
        return motivoCambio;
    }

    public void setMotivoCambio(String motivoCambio) {
        this.motivoCambio = motivoCambio;
    }
}
