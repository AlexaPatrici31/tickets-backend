package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.EstadoCaso;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoCaso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstadoCasoRepository extends JpaRepository <EstadoCaso, Integer> {

    boolean existsByTipoCasoAndOrden(TipoCaso tipoCaso, Integer orden);

    boolean existsByTipoCasoAndOrdenAndIdEstadoCasoNot(TipoCaso tipoCaso, Integer orden, Integer idEstadoCaso);

    List<EstadoCaso> findByTipoCaso(TipoCaso tipoCaso);

    List<EstadoCaso> findByTipoCasoOrderByOrdenAsc(TipoCaso tipoCaso);

    List<EstadoCaso> findByEsFinalTrue();

    List<EstadoCaso> findByActivoTrue();
}
