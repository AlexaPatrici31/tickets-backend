package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {

    List<Incidencia> findByIdCategoria(Integer idCategoria);

    List<Incidencia> findByIdSubcategoria(Integer idSubcategoria);

    @Query("""
        SELECT i FROM Incidencia i
        JOIN Caso c ON c.idCaso = i.idCaso
        WHERE c.idUsuarioSolicitante = :idUsuario
          AND c.activo = true
        """)
    List<Incidencia> findByUsuario(@Param("idUsuario") Integer idUsuario);

    @Query("""
        SELECT i FROM Incidencia i
        JOIN Caso c ON c.idCaso = i.idCaso
        WHERE c.idComunidad = :idComunidad
        """)
    List<Incidencia> findByComunidad(@Param("idComunidad") Integer idComunidad);

    @Query("""
        SELECT i FROM Incidencia i
        JOIN Caso c ON c.idCaso = i.idCaso
        WHERE c.idComunidad = :idComunidad
          AND c.activo = true
          AND c.fechaCierre IS NULL
        """)
    List<Incidencia> findActivasByComunidad(@Param("idComunidad") Integer idComunidad);

    @Query("""
        SELECT ci.idCategoriaIncidencia,
               ci.nombre,
               CASE
                   WHEN ci.categoriaPadre IS NULL THEN 'CATEGORIA'
                   ELSE 'SUBCATEGORIA'
               END,
               COUNT(i)
        FROM Incidencia i
        JOIN CategoriaIncidencia ci
             ON ci.idCategoriaIncidencia = i.idCategoria
        JOIN Caso c
             ON c.idCaso = i.idCaso
        WHERE c.idComunidad = :idComunidad
        GROUP BY ci.idCategoriaIncidencia, ci.nombre, ci.categoriaPadre
        """)
    List<Object[]> countPorCategoria(@Param("idComunidad") Integer idComunidad);

    // Incidencias pendientes (estado inicial)
    // Incidencias pendientes (estado NO final)
    @Query("""
        SELECT COUNT(i)
        FROM Incidencia i, Caso c, EstadoCaso ec
        WHERE c.idCaso = i.idCaso
          AND ec.idEstadoCaso = c.idEstadoCasoActual
          AND c.idComunidad = :idComunidad
          AND ec.esFinal = false
          AND c.activo = true
        """)
    Long countPendientes(@Param("idComunidad") Integer idComunidad);
}

