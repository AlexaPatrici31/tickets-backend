package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.Caso;
import com.tickets.tickets_backend.modelos.enumeraciones.Prioridad;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoCaso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CasoRepository extends JpaRepository<Caso, Integer>,
        JpaSpecificationExecutor<Caso> {

    Page<Caso> findByIdComunidad(Integer idComunidad, Pageable pageable);

    List<Caso> findByIdUsuarioSolicitanteAndActivoTrue(Integer idUsuarioSolicitante);

    boolean existsByIdEstadoCasoActual(Integer idEstadoCasoActual);

    @Query("""
        SELECT c FROM Caso c
        WHERE c.idComunidad = :idComunidad
          AND c.visibleComunidad = true
          AND c.activo = true
        ORDER BY c.fechaCreacion DESC
    """)
    List<Caso> findVisiblesByComunidad(@Param("idComunidad") Integer idComunidad);

    List<Caso> findByTipoCaso(TipoCaso tipoCaso);

    List<Caso> findByIdEstadoCasoActual(Integer idEstadoCasoActual);

    List<Caso> findByPrioridad(Prioridad prioridad);

    List<Caso> findByIdUbicacion(Integer idUbicacion);

    @Query("SELECT c FROM Caso c WHERE c.activo = true AND c.fechaCierre IS NULL")
    List<Caso> findActivos();

    @Query("SELECT c FROM Caso c WHERE c.activo = false OR c.fechaCierre IS NOT NULL")
    List<Caso> findCerrados();

    @Query("SELECT c FROM Caso c WHERE c.idResponsableActual = :idResponsable AND c.activo = true")
    List<Caso> findAsignadosByResponsable(@Param("idResponsable") Integer idResponsable);

    @Query("SELECT c FROM Caso c WHERE c.idComunidad = :idComunidad ORDER BY c.fechaUltimaActualizacion DESC")
    List<Caso> findRecentesByComunidad(@Param("idComunidad") Integer idComunidad, Pageable pageable);

    Optional<Caso> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    // Dashboard: totales por comunidad
    @Query("SELECT COUNT(c) FROM Caso c WHERE c.idComunidad = :idComunidad")
    Long countByComunidad(@Param("idComunidad") Integer idComunidad);

    @Query("SELECT COUNT(c) FROM Caso c WHERE c.idComunidad = :idComunidad AND c.activo = true")
    Long countAbiertos(@Param("idComunidad") Integer idComunidad);

    @Query("SELECT COUNT(c) FROM Caso c WHERE c.idComunidad = :idComunidad AND c.activo = false")
    Long countCerrados(@Param("idComunidad") Integer idComunidad);

    // Por tipo
    @Query("SELECT c.tipoCaso AS tipo, COUNT(c) AS total FROM Caso c WHERE c.idComunidad = :idComunidad GROUP BY c.tipoCaso")
    List<Object[]> countPorTipoByComunidad(@Param("idComunidad") Integer idComunidad);

    // Por prioridad
    @Query("SELECT c.prioridad AS prioridad, COUNT(c) AS total FROM Caso c WHERE c.idComunidad = :idComunidad GROUP BY c.prioridad")
    List<Object[]> countPorPrioridadByComunidad(@Param("idComunidad") Integer idComunidad);

    @Query("""
    SELECT ec.idEstadoCaso, ec.nombre, c.tipoCaso, COUNT(c)
    FROM Caso c JOIN EstadoCaso ec ON ec.idEstadoCaso = c.idEstadoCasoActual
    WHERE c.idComunidad = :idComunidad
    GROUP BY ec.idEstadoCaso, ec.nombre, c.tipoCaso
""")
    List<Object[]> countPorEstadoByComunidad(@Param("idComunidad") Integer idComunidad);

    @Query("""
    SELECT l.idLocalizacion, l.nombre, l.tipoLocalizacion, COUNT(c)
    FROM Caso c JOIN Localizacion l ON l.idLocalizacion = c.idUbicacion
    WHERE c.idComunidad = :idComunidad
    GROUP BY l.idLocalizacion, l.nombre, l.tipoLocalizacion
""")
    List<Object[]> countPorLocalizacionByComunidad(@Param("idComunidad") Integer idComunidad);


    // Actividad reciente (últimos N casos actualizados)
    @Query("""
    SELECT c.idCaso, c.codigo, c.tipoCaso, c.descripcion,
           c.prioridad, ec.nombre, c.fechaUltimaActualizacion
    FROM Caso c JOIN EstadoCaso ec ON ec.idEstadoCaso = c.idEstadoCasoActual
    WHERE c.idComunidad = :idComunidad
    ORDER BY c.fechaUltimaActualizacion DESC
    LIMIT :limite
""")
    List<Object[]> actividadReciente(@Param("idComunidad") Integer idComunidad,
                                     @Param("limite") int limite);

    // Tiempos de respuesta (incidencias con fechaPrimerRespuesta)
    @Query("""
    SELECT c.idCaso, c.codigo, c.fechaCreacion, c.fechaPrimerRespuesta,
           FUNCTION('TIMESTAMPDIFF', HOUR, c.fechaCreacion, c.fechaPrimerRespuesta)
    FROM Caso c
    WHERE c.idComunidad = :idComunidad
      AND c.tipoCaso = 'INCIDENCIA'
      AND c.fechaPrimerRespuesta IS NOT NULL
""")
    List<Object[]> tiemposRespuesta(@Param("idComunidad") Integer idComunidad);

    // Tiempos de cierre
    @Query("""
    SELECT c.idCaso, c.codigo, c.fechaCreacion, c.fechaCierre,
           FUNCTION('TIMESTAMPDIFF', HOUR, c.fechaCreacion, c.fechaCierre)
    FROM Caso c
    WHERE c.idComunidad = :idComunidad
      AND c.fechaCierre IS NOT NULL
      AND c.activo = false
""")
    List<Object[]> tiemposCierre(@Param("idComunidad") Integer idComunidad);

    @Query("""
        SELECT r.idResponsable,
               r.descripcion,
               r.tipoResponsable,
               COUNT(c) AS casosAsignados,
               SUM(CASE WHEN c.activo = true  THEN 1 ELSE 0 END) AS casosEnProceso,
               SUM(CASE WHEN c.activo = false THEN 1 ELSE 0 END) AS casosCerrados
        FROM Caso c
        JOIN Responsable r ON r.idResponsable = c.idResponsableActual
        WHERE c.idComunidad = :idComunidad
        GROUP BY r.idResponsable, r.descripcion, r.tipoResponsable
    """)
    List<Object[]> cargaPorResponsable(@Param("idComunidad") Integer idComunidad);
}

