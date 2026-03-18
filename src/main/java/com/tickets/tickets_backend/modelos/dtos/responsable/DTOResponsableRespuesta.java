package com.tickets.tickets_backend.modelos.dtos.responsable;

import lombok.Data;

@Data
public class DTOResponsableRespuesta extends DTOResponsableActualizar {
    private Integer idResponsable;

    public Integer getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Integer idResponsable) {
        this.idResponsable = idResponsable;
    }
}
