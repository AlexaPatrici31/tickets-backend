package com.tickets.tickets_backend.modelos.dtos.categoriaIncidencia;

import com.tickets.tickets_backend.modelos.entidades.CategoriaIncidencia;

public class DTOCategoriaIncidenciaRespuesta extends DTOCategoriaIncidenciaActualizar {
    private Integer idCategoriaIncidencia;
    private CategoriaIncidencia categoriaPadre;

    public Integer getIdCategoriaIncidencia() {
        return idCategoriaIncidencia;
    }

    public void setIdCategoriaIncidencia(Integer idCategoriaIncidencia) {
        this.idCategoriaIncidencia = idCategoriaIncidencia;
    }

    public CategoriaIncidencia getCategoriaPadre() {
        return categoriaPadre;
    }

    public void setCategoriaPadre(CategoriaIncidencia categoriaPadre) {
        this.categoriaPadre = categoriaPadre;
    }
}
