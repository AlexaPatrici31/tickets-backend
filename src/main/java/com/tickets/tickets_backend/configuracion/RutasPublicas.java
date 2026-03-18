package com.tickets.tickets_backend.configuracion;

import java.util.List;

public class RutasPublicas {

    public static final List<String> ENDPOINTS = List.of(
            // --- AUTHENTICACIÓN ---
            "/api/v1/auth/login",

            // --- USUARIOS ---
            "/api/v1/usuario/listar",

            // --- ADMIN_GENERAL ---
            "/api/v1/admin-general/registro",

            // --- DOCUMENTACIÓN (SWAGGER) ---
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html"
    );
}
