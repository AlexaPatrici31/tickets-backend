package com.tickets.tickets_backend.modelos.dtos.usuario;

import com.tickets.tickets_backend.modelos.enumeraciones.TipoUsuario;

public class DTOCambiarTipoUsuarioRequest {
    private TipoUsuario tipoUsuario;

    public DTOCambiarTipoUsuarioRequest() {
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
