package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.CategoriaIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClasificacionIncidenciaRepositorio extends JpaRepository <CategoriaIncidencia, Integer> {
}
