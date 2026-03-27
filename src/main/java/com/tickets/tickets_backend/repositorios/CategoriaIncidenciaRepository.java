package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.CategoriaIncidencia;
import com.tickets.tickets_backend.modelos.enumeraciones.NivelClasificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaIncidenciaRepository extends JpaRepository<CategoriaIncidencia, Integer> {
    List<CategoriaIncidencia> findByCategoriaPadreIsNull();
    List<CategoriaIncidencia> findByCategoriaPadre(Integer idCategoriaPadre);
    List<CategoriaIncidencia> findByNivelClasificacion(NivelClasificacion nivelClasificacion);
    List<CategoriaIncidencia> findByActivoTrue();
}


