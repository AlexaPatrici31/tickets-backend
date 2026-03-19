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
public class DTOReporteCasosPorResponsableItem {
    @JsonProperty("id_responsable")
    private Long idResponsable;

    @JsonProperty("nombre_responsable")
    private String nombreResponsable;

    @JsonProperty("tipo_responsable")
    private String tipoResponsable;
    // Valores: "USUARIO", "CUADRILLA"

    @JsonProperty("cantidad")
    private Long cantidad;

    public Long getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Long idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public String getTipoResponsable() {
        return tipoResponsable;
    }

    public void setTipoResponsable(String tipoResponsable) {
        this.tipoResponsable = tipoResponsable;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
