package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidenciaRepositorio extends JpaRepository <Incidencia, Integer> {
}
