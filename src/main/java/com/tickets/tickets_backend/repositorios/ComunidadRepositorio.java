package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.Comunidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComunidadRepositorio extends JpaRepository <Comunidad, Integer> {
}
