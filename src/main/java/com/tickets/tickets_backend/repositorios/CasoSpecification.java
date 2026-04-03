package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.dtos.reporte.DTOFiltroReporte;
import com.tickets.tickets_backend.modelos.entidades.Caso;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CasoSpecification {

    public static Specification<Caso> conFiltros(DTOFiltroReporte filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();

            // Siempre filtra por comunidad
            predicados.add(cb.equal(root.get("idComunidad"), filtro.getIdComunidad()));

            if (filtro.getFechaInicio() != null) {
                predicados.add(cb.greaterThanOrEqualTo(
                        root.get("fechaCreacion"),
                        filtro.getFechaInicio().atStartOfDay()));
            }

            if (filtro.getFechaFin() != null) {
                predicados.add(cb.lessThanOrEqualTo(
                        root.get("fechaCreacion"),
                        filtro.getFechaFin().atTime(23, 59, 59)));
            }

            if (filtro.getTipoCaso() != null && !filtro.getTipoCaso().isBlank()) {
                predicados.add(cb.equal(root.get("tipoCaso"), filtro.getTipoCaso()));
            }

            if (filtro.getPrioridad() != null && !filtro.getPrioridad().isBlank()) {
                predicados.add(cb.equal(root.get("prioridad"), filtro.getPrioridad()));
            }

            if (filtro.getIdEstado() != null) {
                predicados.add(cb.equal(root.get("idEstadoCasoActual"), filtro.getIdEstado()));
            }

            if (filtro.getIdResponsable() != null) {
                predicados.add(cb.equal(root.get("idResponsableActual"), filtro.getIdResponsable()));
            }

            if (filtro.getIdLocalizacion() != null) {
                predicados.add(cb.equal(root.get("idUbicacion"), filtro.getIdLocalizacion()));
            }

            return cb.and(predicados.toArray(new Predicate[0]));
        };
    }
}
