package com.tickets.tickets_backend.modelos.dtos.responsable;

import com.tickets.tickets_backend.modelos.dtos.usuario.DTOUsuarioActualizar;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoResponsable;
import lombok.Data;

@Data
public class DTOResponsableActualizar extends DTOUsuarioActualizar {
    private TipoResponsable tipoResponsable;
    private String nombre;
    private String  descripcion;
    private String telefonoContacto;
    private String emailContacto;
    private Boolean activo;

    public TipoResponsable getTipoResponsable() {
        return tipoResponsable;
    }

    public void setTipoResponsable(TipoResponsable tipoResponsable) {
        this.tipoResponsable = tipoResponsable;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
