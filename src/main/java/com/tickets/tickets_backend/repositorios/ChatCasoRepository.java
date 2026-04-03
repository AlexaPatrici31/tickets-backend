package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.ChatCaso;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoMensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatCasoRepository extends JpaRepository<ChatCaso, Integer> {

    List<ChatCaso> findByIdCasoOrderByFechaMensajeAsc(Integer idCaso);

    List<ChatCaso> findByIdCasoAndEsInternoFalseOrderByFechaMensajeAsc(Integer idCaso);

    List<ChatCaso> findByIdCasoAndEsInternoTrueOrderByFechaMensajeAsc(Integer idCaso);

    List<ChatCaso> findByIdCasoAndTipoMensajeOrderByFechaMensajeAsc(Integer idCaso, TipoMensaje tipoMensaje);

    @Query("SELECT c FROM ChatCaso c WHERE c.idCaso = :idCaso AND c.leido = false AND c.esInterno = false")
    List<ChatCaso> findNoLeidosPublicosByCaso(@Param("idCaso") Integer idCaso);

    @Modifying
    @Query("UPDATE ChatCaso c SET c.leido = true WHERE c.idCaso = :idCaso")
    void marcarTodosLeidosByCaso(@Param("idCaso") Integer idCaso);

    @Modifying
    @Query("UPDATE ChatCaso c SET c.leido = true WHERE c.idCaso = :idCaso AND c.esInterno = false")
    void marcarPublicosLeidosByCaso(@Param("idCaso") Integer idCaso);

    long countByIdCasoAndLeidoFalseAndEsInternoFalse(Integer idCaso);
}
