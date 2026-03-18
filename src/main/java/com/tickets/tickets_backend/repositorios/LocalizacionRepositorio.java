package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.Localizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizacionRepositorio extends JpaRepository <Localizacion, Integer> {
}
