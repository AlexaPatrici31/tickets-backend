package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.HistorialCambioEstado;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HistorialCambioEstadoRepository extends JpaRepository<HistorialCambioEstado, Integer> {

    // Todos los registros de una entidad específica, ordenados cronológicamente
    List<HistorialCambioEstado> findByTipoEntidadAndIdEntidadOrderByFechaCambioAsc(
            TipoEntidad tipoEntidad, Integer idEntidad);

    // El último registro (más reciente) de una entidad
    Optional<HistorialCambioEstado> findTopByTipoEntidadAndIdEntidadOrderByFechaCambioDesc(
            TipoEntidad tipoEntidad, Integer idEntidad);

    // Filtrar por tipo de acción
    List<HistorialCambioEstado> findByTipoEntidadAndIdEntidadAndTipoAccionOrderByFechaCambioAsc(
            TipoEntidad tipoEntidad, Integer idEntidad, String tipoAccion);

    // Historial de un caso específico (alias para CASO)
    @Query("""
        SELECT h FROM HistorialCambioEstado h
        WHERE h.tipoEntidad = 'CASO'
          AND h.idEntidad = :idCaso
        ORDER BY h.fechaCambio ASC
    """)
    List<HistorialCambioEstado> findByCasoOrderByFecha(@Param("idCaso") Integer idCaso);

    // Último estado de un caso (para el bottom sheet de cambio de estado)
    @Query("""
        SELECT h FROM HistorialCambioEstado h
        WHERE h.tipoEntidad = 'CASO'
          AND h.idEntidad = :idCaso
        ORDER BY h.fechaCambio DESC
        LIMIT 1
    """)
    Optional<HistorialCambioEstado> findUltimoByCaso(@Param("idCaso") Integer idCaso);

    // Historial por usuario responsable
    List<HistorialCambioEstado> findByIdUsuarioResponsableOrderByFechaCambioDesc(
            Integer idUsuarioResponsable);

    // Historial en un rango de fechas
    @Query("""
        SELECT h FROM HistorialCambioEstado h
        WHERE h.tipoEntidad = :tipoEntidad
          AND h.idEntidad   = :idEntidad
          AND h.fechaCambio BETWEEN :desde AND :hasta
        ORDER BY h.fechaCambio ASC
    """)
    List<HistorialCambioEstado> findByEntidadAndFechaRango(
            @Param("tipoEntidad") TipoEntidad tipoEntidad,
            @Param("idEntidad")   Integer idEntidad,
            @Param("desde")       LocalDateTime desde,
            @Param("hasta")       LocalDateTime hasta);
}
