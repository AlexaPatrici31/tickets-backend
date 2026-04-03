package com.tickets.tickets_backend.servicios.chatCaso;

import com.tickets.tickets_backend.modelos.dtos.chatCaso.DTOChatCasoRegistro;
import com.tickets.tickets_backend.modelos.entidades.ChatCaso;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoMensaje;
import com.tickets.tickets_backend.repositorios.ChatCasoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioChatCaso {

    private final ChatCasoRepository chatCasoRepository;

    public ServicioChatCaso(ChatCasoRepository chatCasoRepository) {
        this.chatCasoRepository = chatCasoRepository;
    }

    // ── CREATE ─────────────────────────────────────────────────────────────────
    @Transactional
    public Map<String, Object> crear(Integer idCaso, Integer idUsuarioEmisor,
                                     DTOChatCasoRegistro datos) {
        ChatCaso mensaje = new ChatCaso();
        mensaje.setIdCaso(idCaso);
        mensaje.setIdUsuarioEmisor(idUsuarioEmisor);
        mensaje.setTipoMensaje(datos.getTipoMensaje() != null
                ? datos.getTipoMensaje() : TipoMensaje.MENSAJE);
        mensaje.setContenido(datos.getContenido());
        mensaje.setEsInterno(datos.getEsInterno() != null ? datos.getEsInterno() : false);
        mensaje.setFechaMensaje(LocalDateTime.now());
        mensaje.setLeido(false);
        mensaje = chatCasoRepository.save(mensaje);
        return construirResponse(mensaje);
    }

    // ── READ ───────────────────────────────────────────────────────────────────
    public List<Map<String, Object>> obtenerTodosByCaso(Integer idCaso) {
        return chatCasoRepository.findByIdCasoOrderByFechaMensajeAsc(idCaso)
                .stream().map(this::construirResponse).toList();
    }

    public List<Map<String, Object>> obtenerPublicosByCaso(Integer idCaso) {
        return chatCasoRepository
                .findByIdCasoAndEsInternoFalseOrderByFechaMensajeAsc(idCaso)
                .stream().map(this::construirResponse).toList();
    }

    public List<Map<String, Object>> obtenerInternosByCaso(Integer idCaso) {
        return chatCasoRepository
                .findByIdCasoAndEsInternoTrueOrderByFechaMensajeAsc(idCaso)
                .stream().map(this::construirResponse).toList();
    }

    public Map<String, Object> obtenerPorId(Integer idChatCaso) {
        return construirResponse(buscarMensaje(idChatCaso));
    }

    public long contarNoLeidos(Integer idCaso) {
        return chatCasoRepository.countByIdCasoAndLeidoFalseAndEsInternoFalse(idCaso);
    }

    // ── PATCH: marcar leídos ───────────────────────────────────────────────────
    @Transactional
    public Map<String, Object> marcarTodosLeidos(Integer idCaso) {
        chatCasoRepository.marcarTodosLeidosByCaso(idCaso);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idCaso", idCaso);
        response.put("mensaje", "Todos los mensajes marcados como leídos.");
        return response;
    }

    @Transactional
    public Map<String, Object> marcarPublicosLeidos(Integer idCaso) {
        chatCasoRepository.marcarPublicosLeidosByCaso(idCaso);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idCaso", idCaso);
        response.put("mensaje", "Mensajes públicos marcados como leídos.");
        return response;
    }

    // ── DELETE ─────────────────────────────────────────────────────────────────
    @Transactional
    public void eliminar(Integer idChatCaso) {
        ChatCaso mensaje = buscarMensaje(idChatCaso);
        chatCasoRepository.delete(mensaje);
    }

    // ── Helpers ────────────────────────────────────────────────────────────────
    private ChatCaso buscarMensaje(Integer idChatCaso) {
        return chatCasoRepository.findById(idChatCaso)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado con id: " + idChatCaso));
    }

    private Map<String, Object> construirResponse(ChatCaso c) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idChatCaso",      c.getIdChatCaso());
        response.put("idCaso",          c.getIdCaso());
        response.put("idUsuarioEmisor", c.getIdUsuarioEmisor());
        response.put("tipoMensaje",     c.getTipoMensaje());
        response.put("contenido",       c.getContenido());
        response.put("esInterno",       c.getEsInterno());
        response.put("fechaMensaje",    c.getFechaMensaje());
        response.put("leido",           c.getLeido());
        return response;
    }
}
