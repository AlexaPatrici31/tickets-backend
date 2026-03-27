package com.tickets.tickets_backend.modelos.dtos.usuario.auth;

public class DTOMensajeResponse {
    private String mensaje;

    public DTOMensajeResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
