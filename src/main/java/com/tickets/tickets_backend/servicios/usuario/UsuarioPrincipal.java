package com.tickets.tickets_backend.servicios.usuario;

import com.tickets.tickets_backend.modelos.entidades.Usuario;
import com.tickets.tickets_backend.modelos.enumeraciones.EstadoUsuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioPrincipal implements UserDetails {

    private final int idUsuario;
    private final String nombre;
    private final String apellido;
    private final String correo;
    private final String contrasena;
    private final List<SimpleGrantedAuthority> autoridades;
    private final boolean activo;

    public UsuarioPrincipal(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.correo = usuario.getCorreo();
        this.contrasena = usuario.getContrasena();
        this.autoridades = List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getTipoUsuario().name()));
        this.activo = usuario.getEstado() == EstadoUsuario.ACTIVO;
    }

    // ==================== GETTERS ADICIONALES ====================

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    // ==================== MÉTODOS DE UserDetails ====================

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return autoridades;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }
}
