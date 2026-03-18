package com.tickets.tickets_backend.modelos.dtos.historialCambioEstado;

import com.tickets.tickets_backend.modelos.enumeraciones.TipoEntidad;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DTOHistorialCambioEstadoRegistro {
    private TipoEntidad tipoEntidad;
    private Integer idEntidad;
    private String tipoAccion = "CAMBIO_ESTADO";
    private String estadoAnterior;
    private String estadoNuevo;
    private LocalDateTime fechaCambio;
    private Integer idUsuarioResponsable;
    private String nombreUsuarioResponsable;
    private String emailUsuario;

    public TipoEntidad getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(TipoEntidad tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }

    public Integer getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Integer idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(String estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public Integer getIdUsuarioResponsable() {
        return idUsuarioResponsable;
    }

    public void setIdUsuarioResponsable(Integer idUsuarioResponsable) {
        this.idUsuarioResponsable = idUsuarioResponsable;
    }

    public String getNombreUsuarioResponsable() {
        return nombreUsuarioResponsable;
    }

    public void setNombreUsuarioResponsable(String nombreUsuarioResponsable) {
        this.nombreUsuarioResponsable = nombreUsuarioResponsable;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}
