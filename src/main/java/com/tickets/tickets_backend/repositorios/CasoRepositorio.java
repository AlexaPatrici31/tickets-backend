package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.Caso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CasoRepositorio extends JpaRepository <Caso, Integer> {
}
