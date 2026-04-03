package com.tickets.tickets_backend.controladores.chatCaso;

import com.tickets.tickets_backend.modelos.dtos.chatCaso.DTOChatCasoRegistro;
import com.tickets.tickets_backend.servicios.chatCaso.ServicioChatCaso;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "ChatCaso", description = "Operaciones del módulo de chat de caso")
@RestController
@RequestMapping("/api/v1/chat-caso")
public class ControladorChatCaso {

    private final ServicioChatCaso servicioChatCaso;

    public ControladorChatCaso(ServicioChatCaso servicioChatCaso) {
        this.servicioChatCaso = servicioChatCaso;
    }

    // Crear mensaje — idUsuarioEmisor llega como parámetro query (o del token en el futuro)
    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @PostMapping("/crear/{idCaso}")
    public ResponseEntity<Map<String, Object>> crear(
            @PathVariable Integer idCaso,
            @RequestParam Integer idUsuarioEmisor,
            @RequestBody DTOChatCasoRegistro datos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicioChatCaso.crear(idCaso, idUsuarioEmisor, datos));
    }

    // Todos los mensajes (admin/responsable ve internos + públicos)
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @GetMapping("/obtener/{idCaso}/todos")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodos(@PathVariable Integer idCaso) {
        return ResponseEntity.ok(servicioChatCaso.obtenerTodosByCaso(idCaso));
    }

    // Solo mensajes públicos (ciudadano)
    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @GetMapping("/obtener/{idCaso}/publicos")
    public ResponseEntity<List<Map<String, Object>>> obtenerPublicos(@PathVariable Integer idCaso) {
        return ResponseEntity.ok(servicioChatCaso.obtenerPublicosByCaso(idCaso));
    }

    // Solo mensajes internos (admin/responsable)
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @GetMapping("/obtener/{idCaso}/internos")
    public ResponseEntity<List<Map<String, Object>>> obtenerInternos(@PathVariable Integer idCaso) {
        return ResponseEntity.ok(servicioChatCaso.obtenerInternosByCaso(idCaso));
    }

    // Detalle de un mensaje específico
    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @GetMapping("/obtener/mensaje/{idChatCaso}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Integer idChatCaso) {
        return ResponseEntity.ok(servicioChatCaso.obtenerPorId(idChatCaso));
    }

    // Conteo de no leídos
    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @GetMapping("/obtener/{idCaso}/no-leidos")
    public ResponseEntity<Map<String, Object>> contarNoLeidos(@PathVariable Integer idCaso) {
        Map<String, Object> response = new java.util.LinkedHashMap<>();
        response.put("idCaso", idCaso);
        response.put("noLeidos", servicioChatCaso.contarNoLeidos(idCaso));
        return ResponseEntity.ok(response);
    }

    // Marcar todos como leídos
    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @PatchMapping("/actualizar/{idCaso}/leer-todos")
    public ResponseEntity<Map<String, Object>> marcarTodosLeidos(@PathVariable Integer idCaso) {
        return ResponseEntity.ok(servicioChatCaso.marcarTodosLeidos(idCaso));
    }

    // Marcar solo públicos como leídos (ciudadano)
    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @PatchMapping("/actualizar/{idCaso}/leer-publicos")
    public ResponseEntity<Map<String, Object>> marcarPublicosLeidos(@PathVariable Integer idCaso) {
        return ResponseEntity.ok(servicioChatCaso.marcarPublicosLeidos(idCaso));
    }

    // Eliminar mensaje (solo admin)
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @DeleteMapping("/eliminar/{idChatCaso}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idChatCaso) {
        servicioChatCaso.eliminar(idChatCaso);
        return ResponseEntity.noContent().build();
    }
}
