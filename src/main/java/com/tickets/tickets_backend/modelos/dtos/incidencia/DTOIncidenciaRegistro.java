package com.tickets.tickets_backend.modelos.dtos.incidencia;

import com.tickets.tickets_backend.modelos.dtos.caso.DTOCasoRegistro;
import lombok.Data;

@Data
public class DTOIncidenciaRegistro extends DTOCasoRegistro {
    private Integer idCategoria;
    private Integer idSubcategoria;

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(Integer idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }
}
