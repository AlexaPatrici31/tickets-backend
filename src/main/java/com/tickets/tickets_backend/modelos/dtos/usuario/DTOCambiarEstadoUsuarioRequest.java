package com.tickets.tickets_backend.modelos.dtos.usuario;

import com.tickets.tickets_backend.modelos.enumeraciones.EstadoUsuario;

public class DTOCambiarEstadoUsuarioRequest {
    private EstadoUsuario estado;

    public DTOCambiarEstadoUsuarioRequest() {
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }
}
