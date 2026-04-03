package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.ServicioComunitario;
import com.tickets.tickets_backend.modelos.enumeraciones.EstadoEjecucion;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicioComunitarioRepository extends JpaRepository<ServicioComunitario, Integer> {

    List<ServicioComunitario> findByTipoServicio(TipoServicio tipoServicio);

    List<ServicioComunitario> findByEstadoEjecucion(EstadoEjecucion estadoEjecucion);

    @Query("""
        SELECT s FROM ServicioComunitario s
        JOIN Caso c ON c.idCaso = s.idCaso
        WHERE c.idUsuarioSolicitante = :idUsuario
          AND c.activo = true
    """)
    List<ServicioComunitario> findByUsuario(@Param("idUsuario") Integer idUsuario);

    @Query("""
        SELECT s FROM ServicioComunitario s
        JOIN Caso c ON c.idCaso = s.idCaso
        WHERE c.idComunidad = :idComunidad
    """)
    List<ServicioComunitario> findByComunidad(@Param("idComunidad") Integer idComunidad);

    @Query("""
        SELECT s FROM ServicioComunitario s
        JOIN Caso c ON c.idCaso = s.idCaso
        WHERE c.idResponsableActual = :idResponsable
          AND c.activo = true
    """)
    List<ServicioComunitario> findByResponsable(@Param("idResponsable") Integer idResponsable);

    // Servicios por tipo
    @Query("""
    SELECT s.tipoServicio AS tipo, COUNT(s) AS total
    FROM ServicioComunitario s
    JOIN Caso c ON c.idCaso = s.idCaso
    WHERE c.idComunidad = :idComunidad
    GROUP BY s.tipoServicio
""")
    List<Object[]> countPorTipo(@Param("idComunidad") Integer idComunidad);

    // Servicios en ejecución de una comunidad
    @Query("""
    SELECT COUNT(s) FROM ServicioComunitario s
    JOIN Caso c ON c.idCaso = s.idCaso
    WHERE c.idComunidad = :idComunidad
      AND s.estadoEjecucion = 'ENPROCESO'
""")
    Long countEnEjecucion(@Param("idComunidad") Integer idComunidad);
}
