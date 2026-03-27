package com.tickets.tickets_backend.configuracion;

import java.util.List;

public final class RutasPublicas {

    public static final List<String> ENDPOINTS = List.of(
            // AUTH
            "/api/v1/auth/login",
            "/api/v1/auth/refresh-token",
            "/api/v1/auth/recuperar-contrasena",
            "/api/v1/auth/restablecer-contrasena",

            // USUARIO
            "/api/v1/usuario/bootstrap-admin",
            "/api/v1/usuario/crearciudadano",

            // SWAGGER / OPENAPI
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/configuration/**",

            // OTROS
            "/error"
    );

    private RutasPublicas() {
    }
}
