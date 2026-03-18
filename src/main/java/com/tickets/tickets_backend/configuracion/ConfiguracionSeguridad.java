package com.tickets.tickets_backend.configuracion;

import com.tickets.tickets_backend.servicios.usuario.ServicioDetallesUsuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class ConfiguracionSeguridad {

    private final UtilidadesJwt utilidadesJwt;
    private final ServicioDetallesUsuario servicioDetallesUsuario;

    public ConfiguracionSeguridad(UtilidadesJwt utilidadesJwt,
                                  ServicioDetallesUsuario servicioDetallesUsuario) {
        this.utilidadesJwt = utilidadesJwt;
        this.servicioDetallesUsuario = servicioDetallesUsuario;
    }

    @Bean
    public SecurityFilterChain crearCadenaDeSeguridad(HttpSecurity http) throws Exception {
        FiltroAutenticacionJwt filtroJwt = new FiltroAutenticacionJwt(utilidadesJwt, servicioDetallesUsuario);

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Permitir rutas públicas
                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/configuration/**",
                                "/api/v1/admin-general/registro"

                        ).permitAll()

                        .requestMatchers("/api/v1/historial-cambio-estado/**")
                        .hasAnyRole("ADMIN_GENERAL", "  ADMIN_COMUNIDAD")

                        .requestMatchers("/api/v1/reportes/**")
                        .hasAnyRole("ADMIN_GENERAL", "ADMIN_COMUNIDAD", "USUARIO_GENERAL", "RESPONSABLE")

                        .requestMatchers("/api/v1/admin-general/**").hasRole("ADMIN_GENERAL")
                        .requestMatchers("/api/v1/admin-comunidad/**").hasAnyRole("ADMIN_GENERAL", "ADMIN_COMUNIDAD")
                        .requestMatchers("/api/v1/usuario-general/**").hasAnyRole("ADMIN_GENERAL", "ADMIN_COMUNIDAD", "USUARIO_GENERAL")
                        .requestMatchers("/api/v1/responsable/**").hasAnyRole("ADMIN_GENERAL", "RESPONSABLE")

                        // Todo lo demás requiere estar autenticado
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filtroJwt, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean("corsConfigGlobal")
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder codificadorContrasena() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager crearGestorAutenticacion(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

