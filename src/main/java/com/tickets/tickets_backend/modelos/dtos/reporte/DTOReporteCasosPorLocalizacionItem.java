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
public class DTOReporteCasosPorLocalizacionItem {
    @JsonProperty("id_localizacion")
    private Long idLocalizacion;

    @JsonProperty("nombre_localizacion")
    private String nombreLocalizacion;

    @JsonProperty("tipo_localizacion")
    private String tipoLocalizacion;
    // Valores: "ZONA", "UBICACION"

    @JsonProperty("cantidad")
    private Long cantidad;

    public Long getIdLocalizacion() {
        return idLocalizacion;
    }

    public void setIdLocalizacion(Long idLocalizacion) {
        this.idLocalizacion = idLocalizacion;
    }

    public String getNombreLocalizacion() {
        return nombreLocalizacion;
    }

    public void setNombreLocalizacion(String nombreLocalizacion) {
        this.nombreLocalizacion = nombreLocalizacion;
    }

    public String getTipoLocalizacion() {
        return tipoLocalizacion;
    }

    public void setTipoLocalizacion(String tipoLocalizacion) {
        this.tipoLocalizacion = tipoLocalizacion;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
