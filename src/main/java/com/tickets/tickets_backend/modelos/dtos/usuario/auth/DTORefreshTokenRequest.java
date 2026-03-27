package com.tickets.tickets_backend.modelos.dtos.usuario.auth;

public class DTORefreshTokenRequest {
    private String refreshToken;

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}
