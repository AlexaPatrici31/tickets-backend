package com.tickets.tickets_backend.servicios.usuario;

import com.tickets.tickets_backend.modelos.entidades.Usuario;
import com.tickets.tickets_backend.repositorios.UsuarioRepositorio;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioDetallesUsuario implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    public ServicioDetallesUsuario(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    /**
     * Se invoca durante la autenticación para cargar
     * usuario + contraseña + autoridades desde la BD.
     */
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        // Prefijo ROLE_ obligatorio para que Spring Security lo entienda como rol
        List<SimpleGrantedAuthority> autoridades = List.of(
                new SimpleGrantedAuthority("ROLE_" + usuario.getTipoUsuario().name())
        );

        return new com.tickets.tickets_backend.servicios.usuario.UsuarioPrincipal(usuario);
    }
}
