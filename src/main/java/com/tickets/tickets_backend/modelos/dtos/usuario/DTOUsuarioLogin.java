package com.tickets.tickets_backend.modelos.dtos.usuario;

import lombok.Data;

@Data
public class DTOUsuarioLogin {
    private String email;
    private String claveHash;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClaveHash() {
        return claveHash;
    }

    public void setClaveHash(String claveHash) {
        this.claveHash = claveHash;
    }
}
