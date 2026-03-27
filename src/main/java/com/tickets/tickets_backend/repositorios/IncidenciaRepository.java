package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidenciaRepository extends JpaRepository <Incidencia, Integer> {
}
