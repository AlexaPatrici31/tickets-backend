package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.Localizacion;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoLocalizacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalizacionRepository extends JpaRepository<Localizacion, Integer> {
    List<Localizacion> findByIdComunidad(Integer idComunidad);
    List<Localizacion> findByTipoLocalizacion(TipoLocalizacion tipoLocalizacion);
    List<Localizacion> findByLocalizacionPadre(Integer idPadre);
    List<Localizacion> findByLocalizacionPadreIsNull();
}
