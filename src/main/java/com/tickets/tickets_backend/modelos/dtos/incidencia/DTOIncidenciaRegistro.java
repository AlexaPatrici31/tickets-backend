package com.tickets.tickets_backend.modelos.dtos.incidencia;

import lombok.Data;

@Data
public class DTOIncidenciaRegistro {
    private Boolean esAnonima;

    public Boolean getEsAnonima() {
        return esAnonima;
    }

    public void setEsAnonima(Boolean esAnonima) {
        this.esAnonima = esAnonima;
    }
}
