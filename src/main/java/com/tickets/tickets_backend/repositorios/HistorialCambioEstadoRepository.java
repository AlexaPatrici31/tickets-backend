package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.HistorialCambioEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HistorialCambioEstadoRepository extends JpaRepository <HistorialCambioEstado, Integer>,
        JpaSpecificationExecutor<HistorialCambioEstado> {
}
