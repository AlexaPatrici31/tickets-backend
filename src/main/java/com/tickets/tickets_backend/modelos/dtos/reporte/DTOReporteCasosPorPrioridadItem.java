package com.tickets.tickets_backend.modelos.dtos.reporte;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DTOReporteCasosPorPrioridadItem {
    @JsonProperty("prioridad")
    private String prioridad;
    // Valores: "BAJA", "MEDIA", "ALTA", "URGENTE"

    @JsonProperty("cantidad")
    private Long cantidad;

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
