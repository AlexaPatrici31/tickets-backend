package com.tickets.tickets_backend.modelos.dtos.servicioComunitario;

import com.tickets.tickets_backend.modelos.enumeraciones.EstadoEjecucion;
import lombok.Data;

@Data
public class DTOCambiarEstadoEjecucion {
    private EstadoEjecucion estadoEjecucion;
    private String observacionesEjecucion;

    public EstadoEjecucion getEstadoEjecucion() {
        return estadoEjecucion;
    }

    public void setEstadoEjecucion(EstadoEjecucion estadoEjecucion) {
        this.estadoEjecucion = estadoEjecucion;
    }

    public String getObservacionesEjecucion() {
        return observacionesEjecucion;
    }

    public void setObservacionesEjecucion(String observacionesEjecucion) {
        this.observacionesEjecucion = observacionesEjecucion;
    }
}
