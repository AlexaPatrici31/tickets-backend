package com.tickets.tickets_backend.controladores.servicioComunitario;

import com.tickets.tickets_backend.modelos.dtos.servicioComunitario.DTOCambiarEstadoEjecucion;
import com.tickets.tickets_backend.modelos.dtos.servicioComunitario.DTOServicioComunitarioActualizar;
import com.tickets.tickets_backend.modelos.dtos.servicioComunitario.DTOServicioComunitarioRegistro;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoServicio;
import com.tickets.tickets_backend.servicios.servicioComunitario.ServicioServicioComunitario;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "ServicioComunitario", description = "Operaciones del módulo de servicio comunitario")
@RestController
@RequestMapping("/api/v1/servicio-comunitario")
public class ControladorServicioComunitario {

    private final ServicioServicioComunitario servicioServicioComunitario;

    public ControladorServicioComunitario(ServicioServicioComunitario servicioServicioComunitario) {
        this.servicioServicioComunitario = servicioServicioComunitario;
    }

    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL')")
    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> crear(@RequestBody DTOServicioComunitarioRegistro datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioServicioComunitario.crear(datos));
    }

    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioServicioComunitario.obtenerPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/obtener")
    public ResponseEntity<List<Map<String, Object>>> obtener() {
        return ResponseEntity.ok(servicioServicioComunitario.obtener());
    }

    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/obtener/usuario/{idUsuario}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(servicioServicioComunitario.obtenerPorUsuario(idUsuario));
    }

    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/obtener/comunidad/{idComunidad}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorComunidad(@PathVariable Integer idComunidad) {
        return ResponseEntity.ok(servicioServicioComunitario.obtenerPorComunidad(idComunidad));
    }

    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @GetMapping("/obtener/en-ejecucion")
    public ResponseEntity<List<Map<String, Object>>> obtenerEnEjecucion() {
        return ResponseEntity.ok(servicioServicioComunitario.obtenerEnEjecucion());
    }

    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/obtener/completados")
    public ResponseEntity<List<Map<String, Object>>> obtenerCompletados() {
        return ResponseEntity.ok(servicioServicioComunitario.obtenerCompletados());
    }

    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/obtener/tipo/{tipoServicio}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorTipo(@PathVariable TipoServicio tipoServicio) {
        return ResponseEntity.ok(servicioServicioComunitario.obtenerPorTipo(tipoServicio));
    }

    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @GetMapping("/obtener/responsable/{idResponsable}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorResponsable(@PathVariable Integer idResponsable) {
        return ResponseEntity.ok(servicioServicioComunitario.obtenerPorResponsable(idResponsable));
    }

    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Integer id,
                                                          @RequestBody DTOServicioComunitarioActualizar datos) {
        return ResponseEntity.ok(servicioServicioComunitario.actualizar(id, datos));
    }

    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @PatchMapping("/actualizar/{id}/estado-ejecucion")
    public ResponseEntity<Map<String, Object>> actualizarEstadoEjecucion(@PathVariable Integer id,
                                                                         @RequestBody DTOCambiarEstadoEjecucion datos) {
        return ResponseEntity.ok(servicioServicioComunitario.actualizarEstadoEjecucion(
                id, datos.getEstadoEjecucion(), datos.getObservacionesEjecucion()));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioServicioComunitario.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
