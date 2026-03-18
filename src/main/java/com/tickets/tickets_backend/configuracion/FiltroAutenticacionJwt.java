package com.tickets.tickets_backend.configuracion;

import com.tickets.tickets_backend.servicios.usuario.ServicioDetallesUsuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FiltroAutenticacionJwt extends OncePerRequestFilter {

    private final com.tickets.tickets_backend.configuracion.UtilidadesJwt utilidadesJwt;
    private final ServicioDetallesUsuario servicioDetallesUsuario;

    public FiltroAutenticacionJwt(com.tickets.tickets_backend.configuracion.UtilidadesJwt utilidadesJwt,
                                  ServicioDetallesUsuario servicioDetallesUsuario) {
        this.utilidadesJwt = utilidadesJwt;
        this.servicioDetallesUsuario = servicioDetallesUsuario;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest solicitud,
                                    HttpServletResponse respuesta,
                                    FilterChain cadena) throws ServletException, IOException {

        String path = solicitud.getServletPath();

        // Validar rutas públicas considerando parámetros dinámicos (ej. /obtener/5)
        boolean esRutaPublica = com.tickets.tickets_backend.configuracion.RutasPublicas.ENDPOINTS.stream()
                .anyMatch(ruta -> {
                    if (ruta.contains("{id}")) {
                        // Coincide solo con el prefijo antes de {id}
                        String prefijo = ruta.replace("{id}", "");
                        return path.startsWith(prefijo);
                    }
                    // Coincidencia exacta
                    return path.equalsIgnoreCase(ruta) || path.startsWith(ruta);
                });

        if (esRutaPublica) {
            cadena.doFilter(solicitud, respuesta);
            return;
        }

        String encabezado = solicitud.getHeader("Authorization");
        String token = null;
        String correoUsuario = null;

        if (encabezado != null && encabezado.startsWith("Bearer ")) {
            token = encabezado.substring(7);
            if (utilidadesJwt.esTokenValido(token)) {
                correoUsuario = utilidadesJwt.obtenerNombreUsuario(token);
            }
        }

        if (correoUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var detallesUsuario = servicioDetallesUsuario.loadUserByUsername(correoUsuario);
            var autenticacion = new UsernamePasswordAuthenticationToken(
                    detallesUsuario, null, detallesUsuario.getAuthorities()
            );
            autenticacion.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(solicitud)
            );
            SecurityContextHolder.getContext().setAuthentication(autenticacion);
        }

        cadena.doFilter(solicitud, respuesta);
    }
}
