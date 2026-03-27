package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.ServicioComunitario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioComunitarioRepository extends JpaRepository <ServicioComunitario, Integer> {
}
