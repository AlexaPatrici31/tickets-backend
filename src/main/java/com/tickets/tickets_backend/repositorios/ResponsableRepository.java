package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.Responsable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResponsableRepository extends JpaRepository <Responsable, Integer> {

    List<Responsable> findByIdComunidad(Integer idComunidad);

    List<Responsable> findByTipoResponsable(String tipoResponsable);

    List<Responsable> findByActivoTrue();

    // Agregar a ResponsableRepository.java
    @Query("SELECT COUNT(r) FROM Responsable r WHERE r.idComunidad = :idComunidad AND r.activo = true")
    Long countActivosByComunidad(@Param("idComunidad") Integer idComunidad);
}
