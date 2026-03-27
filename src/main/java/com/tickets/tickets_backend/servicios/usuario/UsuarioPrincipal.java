package com.tickets.tickets_backend.servicios.usuario;

import com.tickets.tickets_backend.modelos.entidades.ResponsableUsuario;
import com.tickets.tickets_backend.modelos.entidades.Usuario;
import com.tickets.tickets_backend.modelos.enumeraciones.EstadoUsuario;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoUsuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioPrincipal implements UserDetails {

    private final Integer idUsuario;
    private final Integer idComunidad;
    private final String nombre;
    private final String apellido;
    private final String correo;
    private final String contrasena;
    private final TipoUsuario tipoUsuario;
    private final EstadoUsuario estado;
    private final List<Integer> idsResponsable;
    private final Collection<? extends GrantedAuthority> authorities;

    public UsuarioPrincipal(Usuario usuario, List<ResponsableUsuario> relacionesResponsable) {
        this.idUsuario = usuario.getIdUsuario();
        this.idComunidad = usuario.getIdComunidad();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.correo = usuario.getCorreo();
        this.contrasena = usuario.getContrasena();
        this.tipoUsuario = usuario.getTipoUsuario();
        this.estado = usuario.getEstado();
        this.idsResponsable = relacionesResponsable.stream()
                .filter(rel -> rel.getResponsable() != null && Boolean.TRUE.equals(rel.getResponsable().getActivo()))
                .map(rel -> rel.getResponsable().getIdResponsable())
                .toList();
        this.authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + usuario.getTipoUsuario().name())
        );
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public Integer getIdComunidad() {
        return idComunidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public List<Integer> getIdsResponsable() {
        return idsResponsable;
    }

    public boolean esAdminGeneral() {
        return tipoUsuario == TipoUsuario.ADMINGENERAL;
    }

    public boolean esAdminComunidad() {
        return tipoUsuario == TipoUsuario.ADMINCOMUNIDAD;
    }

    public boolean esResponsable() {
        return tipoUsuario == TipoUsuario.RESPONSABLE;
    }

    public boolean esUsuarioGeneral() {
        return tipoUsuario == TipoUsuario.USUARIOGENERAL;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return estado != EstadoUsuario.ELIMINADO;
    }

    @Override
    public boolean isAccountNonLocked() {
        return estado != EstadoUsuario.SUSPENDIDO && estado != EstadoUsuario.ELIMINADO;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return estado != EstadoUsuario.ELIMINADO;
    }

    @Override
    public boolean isEnabled() {
        return estado == EstadoUsuario.ACTIVO;
    }
}
