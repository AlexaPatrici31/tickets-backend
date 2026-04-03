package com.tickets.tickets_backend.controladores.historialCambioEstado;

import com.tickets.tickets_backend.modelos.dtos.historialCambioEstado.DTOHistorialCambioEstadoRegistro;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoEntidad;
import com.tickets.tickets_backend.servicios.historialCambioEstado.ServicioHistorialCambioEstado;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "HistorialCambioEstado", description = "Línea de tiempo y auditoría de cambios de estado")
@RestController
@RequestMapping("/api/v1/historial-cambio-estado")
public class ControladorHistorialCambioEstado {

    private final ServicioHistorialCambioEstado servicioHistorial;

    public ControladorHistorialCambioEstado(ServicioHistorialCambioEstado servicioHistorial) {
        this.servicioHistorial = servicioHistorial;
    }

    // Registrar manualmente un evento (admin/sistema)
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @PostMapping("/registrar")
    public ResponseEntity<Map<String, Object>> registrar(
            @RequestBody DTOHistorialCambioEstadoRegistro datos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicioHistorial.registrar(datos));
    }

    // Línea de tiempo de un caso (stepper en pantalla de detalle)
    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @GetMapping("/obtener/caso/{idCaso}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorCaso(@PathVariable Integer idCaso) {
        return ResponseEntity.ok(servicioHistorial.obtenerPorCaso(idCaso));
    }

    // Último estado de un caso (para el bottom sheet de cambio de estado)
    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @GetMapping("/obtener/caso/{idCaso}/ultimo")
    public ResponseEntity<Map<String, Object>> obtenerUltimoPorCaso(@PathVariable Integer idCaso) {
        return ResponseEntity.ok(servicioHistorial.obtenerUltimoPorCaso(idCaso));
    }

    // Historial genérico por tipo de entidad + id
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/obtener/{tipoEntidad}/{idEntidad}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorEntidad(
            @PathVariable TipoEntidad tipoEntidad,
            @PathVariable Integer idEntidad) {
        return ResponseEntity.ok(servicioHistorial.obtenerPorEntidad(tipoEntidad, idEntidad));
    }

    // Último registro genérico por entidad
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/obtener/{tipoEntidad}/{idEntidad}/ultimo")
    public ResponseEntity<Map<String, Object>> obtenerUltimoPorEntidad(
            @PathVariable TipoEntidad tipoEntidad,
            @PathVariable Integer idEntidad) {
        return ResponseEntity.ok(servicioHistorial.obtenerUltimoPorEntidad(tipoEntidad, idEntidad));
    }

    // Historial de acciones de un usuario
    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/obtener/usuario/{idUsuario}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorUsuario(
            @PathVariable Integer idUsuario) {
        return ResponseEntity.ok(servicioHistorial.obtenerPorUsuario(idUsuario));
    }
}
