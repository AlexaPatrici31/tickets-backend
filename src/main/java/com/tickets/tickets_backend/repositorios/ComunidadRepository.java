package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.Comunidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComunidadRepository extends JpaRepository <Comunidad, Integer> {
    List<Comunidad> findByActivoTrue();
}
