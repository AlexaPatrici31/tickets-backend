package com.tickets.tickets_backend.modelos.dtos.usuario;

import lombok.Data;

@Data
public class DTOUsuarioRespuesta extends DTOUsuarioActualizar{
    private Integer idUsuario;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
