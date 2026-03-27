package com.tickets.tickets_backend.modelos.dtos.usuario;

public class DTOActualizarContrasenaUsuarioRequest {
    private String contrasenaActual;
    private String contrasenaNueva;

    public DTOActualizarContrasenaUsuarioRequest() {
    }

    public String getContrasenaActual() {
        return contrasenaActual;
    }

    public void setContrasenaActual(String contrasenaActual) {
        this.contrasenaActual = contrasenaActual;
    }

    public String getContrasenaNueva() {
        return contrasenaNueva;
    }

    public void setContrasenaNueva(String contrasenaNueva) {
        this.contrasenaNueva = contrasenaNueva;
    }
}
