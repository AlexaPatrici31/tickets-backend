package com.tickets.tickets_backend.modelos.dtos.responsable;

import com.tickets.tickets_backend.modelos.enumeraciones.TipoResponsable;

import java.util.List;

public class DTOActualizarResponsableRequest {

    private TipoResponsable tipoResponsable;
    private String descripcion;
    private List<Integer> idsUsuarios;

    public DTOActualizarResponsableRequest() {
    }

    public TipoResponsable getTipoResponsable() {
        return tipoResponsable;
    }

    public void setTipoResponsable(TipoResponsable tipoResponsable) {
        this.tipoResponsable = tipoResponsable;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Integer> getIdsUsuarios() {
        return idsUsuarios;
    }

    public void setIdsUsuarios(List<Integer> idsUsuarios) {
        this.idsUsuarios = idsUsuarios;
    }
}

