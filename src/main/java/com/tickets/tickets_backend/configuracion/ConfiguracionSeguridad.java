package com.tickets.tickets_backend.configuracion;

import com.tickets.tickets_backend.servicios.usuario.ServicioDetallesUsuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfiguracionSeguridad {

    private final UtilidadesJwt utilidadesJwt;
    private final ServicioDetallesUsuario servicioDetallesUsuario;
    private final ConfiguracionCors configuracionCors;

    public ConfiguracionSeguridad(UtilidadesJwt utilidadesJwt,
                                  ServicioDetallesUsuario servicioDetallesUsuario,
                                  ConfiguracionCors configuracionCors) {
        this.utilidadesJwt = utilidadesJwt;
        this.servicioDetallesUsuario = servicioDetallesUsuario;
        this.configuracionCors = configuracionCors;
    }

    @Bean
    public SecurityFilterChain crearCadenaDeSeguridad(HttpSecurity http) throws Exception {
        FiltroAutenticacionJwt filtroJwt =
                new FiltroAutenticacionJwt(utilidadesJwt, servicioDetallesUsuario);

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(configuracionCors.corsConfigurationSource()))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/api/v1/auth/refresh-token",
                                "/api/v1/auth/recuperar-contrasena",
                                "/api/v1/auth/restablecer-contrasena",
                                "/api/v1/usuario/bootstrap-admin",
                                "/api/v1/usuario/crearciudadano",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filtroJwt, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder codificadorContrasena() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(servicioDetallesUsuario);
        provider.setPasswordEncoder(codificadorContrasena());
        return provider;
    }


    @Bean
    public AuthenticationManager crearGestorAutenticacion(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
