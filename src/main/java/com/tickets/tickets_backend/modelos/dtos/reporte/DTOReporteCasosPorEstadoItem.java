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
public class DTOReporteCasosPorEstadoItem {
    @JsonProperty("id_estado_caso")
    private Long idEstadoCaso;

    @JsonProperty("nombre_estado")
    private String nombreEstado;

    @JsonProperty("tipo_caso")
    private String tipoCaso;
    // Valores: "INCIDENCIA", "SERVICIO"

    @JsonProperty("cantidad")
    private Long cantidad;

    public Long getIdEstadoCaso() {
        return idEstadoCaso;
    }

    public void setIdEstadoCaso(Long idEstadoCaso) {
        this.idEstadoCaso = idEstadoCaso;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(String tipoCaso) {
        this.tipoCaso = tipoCaso;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
