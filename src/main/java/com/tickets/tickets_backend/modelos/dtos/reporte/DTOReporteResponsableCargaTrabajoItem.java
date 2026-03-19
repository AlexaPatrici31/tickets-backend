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
public class DTOReporteResponsableCargaTrabajoItem {
    @JsonProperty("id_responsable")
    private Long idResponsable;

    @JsonProperty("nombre_responsable")
    private String nombreResponsable;

    @JsonProperty("tipo_responsable")
    private String tipoResponsable;
    // Valores: "USUARIO", "CUADRILLA"

    @JsonProperty("casos_asignados")
    private Long casosAsignados;

    @JsonProperty("casos_en_proceso")
    private Long casosEnProceso;

    @JsonProperty("casos_cerrados")
    private Long casosCerrados;

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

    public Long getCasosAsignados() {
        return casosAsignados;
    }

    public void setCasosAsignados(Long casosAsignados) {
        this.casosAsignados = casosAsignados;
    }

    public Long getCasosEnProceso() {
        return casosEnProceso;
    }

    public void setCasosEnProceso(Long casosEnProceso) {
        this.casosEnProceso = casosEnProceso;
    }

    public Long getCasosCerrados() {
        return casosCerrados;
    }

    public void setCasosCerrados(Long casosCerrados) {
        this.casosCerrados = casosCerrados;
    }
}
