package com.tickets.tickets_backend.controladores.caso;

import com.tickets.tickets_backend.modelos.dtos.caso.*;
import com.tickets.tickets_backend.modelos.enumeraciones.Prioridad;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoCaso;
import com.tickets.tickets_backend.servicios.caso.ServicioCaso;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Caso", description = "Operaciones del módulo de caso")
@RestController
@RequestMapping("/api/v1/caso")
public class ControladorCaso {

    private final ServicioCaso servicioCaso;

    public ControladorCaso(ServicioCaso servicioCaso) {
        this.servicioCaso = servicioCaso;
    }

    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> crear(@RequestBody DTOCasoRegistro datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioCaso.crear(datos));
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioCaso.obtenerPorId(id));
    }

    @GetMapping("/obtener/{id}/detalle-completo")
    public ResponseEntity<Map<String, Object>> obtenerDetalle(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioCaso.obtenerDetalle(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/comunidad/{idComunidad}")
    public ResponseEntity<Page<Map<String, Object>>> obtenerPorComunidad(
            @PathVariable Integer idComunidad, Pageable pageable) {
        return ResponseEntity.ok(servicioCaso.obtenerPorComunidad(idComunidad, pageable));
    }

    @GetMapping("/obtener/usuario/{idUsuario}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(servicioCaso.obtenerPorUsuario(idUsuario));
    }

    @PreAuthorize("hasRole('USUARIOGENERAL')")
    @GetMapping("/obtener/visibles/comunidad/{idComunidad}")
    public ResponseEntity<List<Map<String, Object>>> obtenerVisibles(@PathVariable Integer idComunidad) {
        return ResponseEntity.ok(servicioCaso.obtenerVisiblesComunidad(idComunidad));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/tipo/{tipoCaso}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorTipo(@PathVariable TipoCaso tipoCaso) {
        return ResponseEntity.ok(servicioCaso.obtenerPorTipo(tipoCaso));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/estado/{idEstadoCaso}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorEstado(@PathVariable Integer idEstadoCaso) {
        return ResponseEntity.ok(servicioCaso.obtenerPorEstado(idEstadoCaso));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/prioridad/{prioridad}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorPrioridad(@PathVariable Prioridad prioridad) {
        return ResponseEntity.ok(servicioCaso.obtenerPorPrioridad(prioridad));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/localizacion/{idLocalizacion}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorLocalizacion(@PathVariable Integer idLocalizacion) {
        return ResponseEntity.ok(servicioCaso.obtenerPorLocalizacion(idLocalizacion));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/activos")
    public ResponseEntity<List<Map<String, Object>>> obtenerActivos() {
        return ResponseEntity.ok(servicioCaso.obtenerActivos());
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/cerrados")
    public ResponseEntity<List<Map<String, Object>>> obtenerCerrados() {
        return ResponseEntity.ok(servicioCaso.obtenerCerrados());
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Integer id,
                                                          @RequestBody DTOCasoActualizar datos) {
        return ResponseEntity.ok(servicioCaso.actualizar(id, datos));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PatchMapping("/actualizar/{id}/responsable")
    public ResponseEntity<Map<String, Object>> asignarResponsable(@PathVariable Integer id,
                                                                  @RequestBody DTOAsignarResponsable datos) {
        return ResponseEntity.ok(servicioCaso.asignarResponsable(id, datos));
    }

    @PatchMapping("/actualizar/{id}/estado")
    public ResponseEntity<Map<String, Object>> cambiarEstado(@PathVariable Integer id,
                                                             @RequestBody DTOCambiarEstadoCaso datos) {
        return ResponseEntity.ok(servicioCaso.cambiarEstado(id, datos, null, null, null));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioCaso.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
