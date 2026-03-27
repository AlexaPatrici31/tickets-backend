package com.tickets.tickets_backend.controladores.estadoCaso;

import com.tickets.tickets_backend.modelos.dtos.estadoCaso.DTOActualizarEstadoCasoRequest;
import com.tickets.tickets_backend.modelos.dtos.estadoCaso.DTOCrearEstadoCasoRequest;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoCaso;
import com.tickets.tickets_backend.servicios.estadoCaso.ServicioEstadoCaso;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "EstadoCaso", description = "Operaciones del módulo estado de caso")
@RestController
@RequestMapping("/api/v1/estado-caso")
public class ControladorEstadoCaso {

    private final ServicioEstadoCaso servicioEstadoCaso;

    public ControladorEstadoCaso(ServicioEstadoCaso servicioEstadoCaso) {
        this.servicioEstadoCaso = servicioEstadoCaso;
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> crear(@RequestBody DTOCrearEstadoCasoRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioEstadoCaso.crear(datos));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/obtener")
    public ResponseEntity<List<Map<String, Object>>> obtener() {
        return ResponseEntity.ok(servicioEstadoCaso.obtener());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioEstadoCaso.obtenerPorId(id));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Integer id,
                                                          @RequestBody DTOActualizarEstadoCasoRequest datos) {
        return ResponseEntity.ok(servicioEstadoCaso.actualizar(id, datos));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @PatchMapping("/actualizar/{id}/activar")
    public ResponseEntity<Map<String, Object>> activar(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioEstadoCaso.activar(id));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @PatchMapping("/actualizar/{id}/desactivar")
    public ResponseEntity<Map<String, Object>> desactivar(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioEstadoCaso.desactivar(id));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioEstadoCaso.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/obtener/tipo/{tipoCaso}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorTipoCaso(@PathVariable TipoCaso tipoCaso) {
        return ResponseEntity.ok(servicioEstadoCaso.obtenerPorTipoCaso(tipoCaso));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/obtener/tipo/{tipoCaso}/ordenados")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorTipoCasoOrdenados(@PathVariable TipoCaso tipoCaso) {
        return ResponseEntity.ok(servicioEstadoCaso.obtenerPorTipoCasoOrdenados(tipoCaso));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/finales")
    public ResponseEntity<List<Map<String, Object>>> obtenerFinales() {
        return ResponseEntity.ok(servicioEstadoCaso.obtenerFinales());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/obtener/activos")
    public ResponseEntity<List<Map<String, Object>>> obtenerActivos() {
        return ResponseEntity.ok(servicioEstadoCaso.obtenerActivos());
    }
}
