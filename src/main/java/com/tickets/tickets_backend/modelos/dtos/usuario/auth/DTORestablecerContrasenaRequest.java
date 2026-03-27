package com.tickets.tickets_backend.modelos.dtos.usuario.auth;

public class DTORestablecerContrasenaRequest {
    private String token;
    private String contrasenaNueva;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getContrasenaNueva() { return contrasenaNueva; }
    public void setContrasenaNueva(String contrasenaNueva) { this.contrasenaNueva = contrasenaNueva; }
}
