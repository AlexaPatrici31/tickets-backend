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
public class DTOReporteServicioPorTipoItem {
    @JsonProperty("tipo_servicio")
    private String tipoServicio;
    // Valores: "LIMPIEZA", "MANTENIMIENTO", "SEGURIDAD", "APOYO_COMUNITARIO", "OTRO"

    @JsonProperty("cantidad")
    private Long cantidad;

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
