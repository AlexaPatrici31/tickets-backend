package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.Responsable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsableRepositorio extends JpaRepository <Responsable, Integer> {
}
