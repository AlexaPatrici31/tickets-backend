package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.Responsable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponsableRepository extends JpaRepository <Responsable, Integer> {

    List<Responsable> findByIdComunidad(Integer idComunidad);

    List<Responsable> findByTipoResponsable(String tipoResponsable);

    List<Responsable> findByActivoTrue();

}
