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
public class DTOReporteIncidenciaPorCategoriaItem {
    @JsonProperty("id_categoria")
    private Long idCategoria;

    @JsonProperty("nombre_categoria")
    private String nombreCategoria;

    @JsonProperty("nivel_clasificacion")
    private String nivelClasificacion;
    // Valores: "CATEGORIA", "SUBCATEGORIA"

    @JsonProperty("cantidad")
    private Long cantidad;

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getNivelClasificacion() {
        return nivelClasificacion;
    }

    public void setNivelClasificacion(String nivelClasificacion) {
        this.nivelClasificacion = nivelClasificacion;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
