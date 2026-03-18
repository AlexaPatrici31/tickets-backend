package com.tickets.tickets_backend.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class ConfiguracionCors {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuracion = new CorsConfiguration();
        configuracion.setAllowedOrigins(List.of("http://localhost:5174"));
        configuracion.setAllowedOrigins(List.of("*"));          // Orígenes permitidos
        configuracion.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        configuracion.setAllowedHeaders(List.of("*"));          // Cabeceras entrantes válidas
        configuracion.setExposedHeaders(List.of("Authorization")); // Cabeceras expuestas al cliente
        configuracion.setAllowCredentials(true);

        // UrlBasedCorsConfigurationSource de servlets implementa CorsConfigurationSource
        UrlBasedCorsConfigurationSource fuente = new UrlBasedCorsConfigurationSource();
        fuente.registerCorsConfiguration("/**", configuracion); // Aplica a todas las rutas
        return fuente;                                          // Devuelve la fuente correcta
    }
}
