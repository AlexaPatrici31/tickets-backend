package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.EstadoCaso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoCasoRepositorio extends JpaRepository <EstadoCaso, Integer> {
}
