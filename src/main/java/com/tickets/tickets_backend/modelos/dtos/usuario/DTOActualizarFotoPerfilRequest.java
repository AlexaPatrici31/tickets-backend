package com.tickets.tickets_backend.modelos.dtos.usuario;

public class DTOActualizarFotoPerfilRequest {
    private String fotoPerfilUrl;

    public DTOActualizarFotoPerfilRequest() {
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }
}
