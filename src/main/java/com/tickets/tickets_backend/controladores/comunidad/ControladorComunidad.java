package com.tickets.tickets_backend.controladores.comunidad;

import com.tickets.tickets_backend.modelos.dtos.comunidad.DTOActualizarComunidadRequest;
import com.tickets.tickets_backend.modelos.dtos.comunidad.DTOCrearComunidadRequest;
import com.tickets.tickets_backend.servicios.comunidad.ServicioComunidad;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Comunidad", description = "Operaciones del módulo de comunidad")
@RestController
@RequestMapping("/api/v1/comunidad")
public class ControladorComunidad {

    private final ServicioComunidad servicioComunidad;

    public ControladorComunidad(ServicioComunidad servicioComunidad) {
        this.servicioComunidad = servicioComunidad;
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> crear(@RequestBody DTOCrearComunidadRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioComunidad.crear(datos));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @GetMapping("/obtener")
    public ResponseEntity<Page<Map<String, Object>>> obtener(Pageable pageable) {
        return ResponseEntity.ok(servicioComunidad.obtener(pageable));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioComunidad.obtenerPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Integer id,
                                                          @RequestBody DTOActualizarComunidadRequest datos) {
        return ResponseEntity.ok(servicioComunidad.actualizar(id, datos));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @PatchMapping("/actualizar/{id}/activar")
    public ResponseEntity<Map<String, Object>> activar(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioComunidad.activar(id));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @PatchMapping("/actualizar/{id}/desactivar")
    public ResponseEntity<Map<String, Object>> desactivar(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioComunidad.desactivar(id));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioComunidad.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @GetMapping("/obtener/activas")
    public ResponseEntity<List<Map<String, Object>>> obtenerActivas() {
        return ResponseEntity.ok(servicioComunidad.obtenerActivas());
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/{id}/usuarios")
    public ResponseEntity<Page<Map<String, Object>>> obtenerUsuarios(@PathVariable Integer id,
                                                                     Pageable pageable) {
        return ResponseEntity.ok(servicioComunidad.obtenerUsuarios(id, pageable));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/{id}/responsables")
    public ResponseEntity<List<Map<String, Object>>> obtenerResponsables(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioComunidad.obtenerResponsables(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD','USUARIOGENERAL')")
    @GetMapping("/obtener/{id}/localizaciones")
    public ResponseEntity<List<Map<String, Object>>> obtenerLocalizaciones(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioComunidad.obtenerLocalizaciones(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/{id}/casos")
    public ResponseEntity<Page<Map<String, Object>>> obtenerCasos(@PathVariable Integer id,
                                                                  Pageable pageable) {
        return ResponseEntity.ok(servicioComunidad.obtenerCasos(id, pageable));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/{id}/incidencias")
    public ResponseEntity<List<Map<String, Object>>> obtenerIncidencias(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioComunidad.obtenerIncidencias(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/{id}/servicios")
    public ResponseEntity<List<Map<String, Object>>> obtenerServicios(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioComunidad.obtenerServicios(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/{id}/resumen")
    public ResponseEntity<Map<String, Object>> obtenerResumen(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioComunidad.obtenerResumen(id));
    }
}
