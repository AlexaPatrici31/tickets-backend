package com.tickets.tickets_backend.modelos.dtos.caso;

import lombok.Data;

@Data
public class DTOAsignarResponsable {
    private Integer idResponsableActual;

    public Integer getIdResponsableActual() {
        return idResponsableActual;
    }

    public void setIdResponsableActual(Integer idResponsableActual) {
        this.idResponsableActual = idResponsableActual;
    }
}
