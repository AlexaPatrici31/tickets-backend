package com.tickets.tickets_backend.configuracion;

import com.tickets.tickets_backend.servicios.usuario.ServicioDetallesUsuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FiltroAutenticacionJwt extends OncePerRequestFilter {

    private final UtilidadesJwt utilidadesJwt;
    private final ServicioDetallesUsuario servicioDetallesUsuario;

    public FiltroAutenticacionJwt(UtilidadesJwt utilidadesJwt,
                                  ServicioDetallesUsuario servicioDetallesUsuario) {
        this.utilidadesJwt = utilidadesJwt;
        this.servicioDetallesUsuario = servicioDetallesUsuario;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest solicitud,
                                    HttpServletResponse respuesta,
                                    FilterChain cadena) throws ServletException, IOException {

        String path = solicitud.getServletPath();

        if (esRutaPublica(path)) {
            cadena.doFilter(solicitud, respuesta);
            return;
        }

        String encabezadoAutorizacion = solicitud.getHeader("Authorization");

        if (encabezadoAutorizacion == null || !encabezadoAutorizacion.startsWith("Bearer ")) {
            cadena.doFilter(solicitud, respuesta);
            return;
        }

        String token = encabezadoAutorizacion.substring(7).trim();

        if (token.isBlank()) {
            cadena.doFilter(solicitud, respuesta);
            return;
        }

        try {
            String correoUsuario = utilidadesJwt.obtenerCorreo(token);

            if (correoUsuario != null
                    && SecurityContextHolder.getContext().getAuthentication() == null
                    && utilidadesJwt.esAccessTokenValido(token)) {

                UserDetails detallesUsuario = servicioDetallesUsuario.loadUserByUsername(correoUsuario);

                UsernamePasswordAuthenticationToken autenticacion =
                        new UsernamePasswordAuthenticationToken(
                                detallesUsuario,
                                null,
                                detallesUsuario.getAuthorities()
                        );

                autenticacion.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(solicitud)
                );

                SecurityContextHolder.getContext().setAuthentication(autenticacion);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        cadena.doFilter(solicitud, respuesta);
    }

    private boolean esRutaPublica(String path) {
        return path.equals("/api/v1/usuario/bootstrap-admin")
                || path.equals("/api/v1/usuario/crearciudadano")
                || path.equals("/api/v1/auth/login")
                || path.equals("/api/v1/auth/refresh-token")
                || path.equals("/api/v1/auth/recuperar-contrasena")
                || path.equals("/api/v1/auth/restablecer-contrasena")
                || path.equals("/swagger-ui.html")
                || path.equals("/error")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/webjars");
    }
}

