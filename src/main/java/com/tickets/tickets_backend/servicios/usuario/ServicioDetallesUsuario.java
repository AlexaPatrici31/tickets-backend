package com.tickets.tickets_backend.servicios.usuario;

import com.tickets.tickets_backend.modelos.entidades.Usuario;
import com.tickets.tickets_backend.repositorios.ResponsableUsuarioRepository;
import com.tickets.tickets_backend.repositorios.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ServicioDetallesUsuario implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final ResponsableUsuarioRepository responsableUsuarioRepository;

    public ServicioDetallesUsuario(UsuarioRepository usuarioRepository,
                                   ResponsableUsuarioRepository responsableUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.responsableUsuarioRepository = responsableUsuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        return new UsuarioPrincipal(
                usuario,
                responsableUsuarioRepository.findByUsuario_IdUsuario(usuario.getIdUsuario())
        );
    }
}
