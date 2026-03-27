package com.tickets.tickets_backend.modelos.dtos.usuario.auth;

public class DTOCambiarContrasenaRequest {
    private String contrasenaActual;
    private String contrasenaNueva;

    public String getContrasenaActual() { return contrasenaActual; }
    public void setContrasenaActual(String contrasenaActual) { this.contrasenaActual = contrasenaActual; }

    public String getContrasenaNueva() { return contrasenaNueva; }
    public void setContrasenaNueva(String contrasenaNueva) { this.contrasenaNueva = contrasenaNueva; }
}
