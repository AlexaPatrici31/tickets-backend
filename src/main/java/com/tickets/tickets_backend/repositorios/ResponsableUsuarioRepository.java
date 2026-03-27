package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.ResponsableUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResponsableUsuarioRepository extends JpaRepository<ResponsableUsuario, Integer> {

    boolean existsByResponsable_IdResponsableAndUsuario_IdUsuario(Integer idResponsable, Integer idUsuario);

    boolean existsByResponsable_IdResponsableAndUsuario_IdUsuarioAndActivoTrue(Integer idResponsable, Integer idUsuario);

    Optional<ResponsableUsuario> findByResponsable_IdResponsableAndUsuario_IdUsuario(Integer idResponsable, Integer idUsuario);

    Optional<ResponsableUsuario> findByResponsable_IdResponsableAndUsuario_IdUsuarioAndActivoTrue(Integer idResponsable, Integer idUsuario);

    List<ResponsableUsuario> findByResponsable_IdResponsable(Integer idResponsable);

    List<ResponsableUsuario> findByResponsable_IdResponsableAndActivoTrue(Integer idResponsable);

    List<ResponsableUsuario> findByUsuario_IdUsuario(Integer idUsuario);

    List<ResponsableUsuario> findByUsuario_IdUsuarioAndActivoTrue(Integer idUsuario);
}
