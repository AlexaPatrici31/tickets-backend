package com.tickets.tickets_backend.controladores.localizacion;

import com.tickets.tickets_backend.modelos.dtos.Localizacion.DTOActualizarLocalizacionRequest;
import com.tickets.tickets_backend.modelos.dtos.Localizacion.DTOCrearLocalizacionRequest;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoLocalizacion;
import com.tickets.tickets_backend.servicios.localizacion.ServicioLocalizacion;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Localizacion", description = "Operaciones del módulo localización")
@RestController
@RequestMapping("/api/v1/localizacion")
public class ControladorLocalizacion {

    private final ServicioLocalizacion servicioLocalizacion;

    public ControladorLocalizacion(ServicioLocalizacion servicioLocalizacion) {
        this.servicioLocalizacion = servicioLocalizacion;
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> crear(@RequestBody DTOCrearLocalizacionRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioLocalizacion.crear(datos));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @GetMapping("/obtener")
    public ResponseEntity<Page<Map<String, Object>>> obtener(Pageable pageable) {
        return ResponseEntity.ok(servicioLocalizacion.obtener(pageable));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD','USUARIOGENERAL')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioLocalizacion.obtenerPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @PathVariable Integer id,
            @RequestBody DTOActualizarLocalizacionRequest datos) {
        return ResponseEntity.ok(servicioLocalizacion.actualizar(id, datos));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PatchMapping("/actualizar/{id}/activar")
    public ResponseEntity<Map<String, Object>> activar(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioLocalizacion.activar(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PatchMapping("/actualizar/{id}/desactivar")
    public ResponseEntity<Map<String, Object>> desactivar(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioLocalizacion.desactivar(id));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioLocalizacion.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/obtener/comunidad/{idComunidad}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorComunidad(@PathVariable Integer idComunidad) {
        return ResponseEntity.ok(servicioLocalizacion.obtenerPorComunidad(idComunidad));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/tipo/{tipoLocalizacion}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorTipo(@PathVariable TipoLocalizacion tipoLocalizacion) {
        return ResponseEntity.ok(servicioLocalizacion.obtenerPorTipo(tipoLocalizacion));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/obtener/padre/{idPadre}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorPadre(@PathVariable Integer idPadre) {
        return ResponseEntity.ok(servicioLocalizacion.obtenerPorPadre(idPadre));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/raiz")
    public ResponseEntity<List<Map<String, Object>>> obtenerRaiz() {
        return ResponseEntity.ok(servicioLocalizacion.obtenerRaiz());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/obtener/{id}/hijos")
    public ResponseEntity<List<Map<String, Object>>> obtenerHijos(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioLocalizacion.obtenerHijos(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/{id}/casos")
    public ResponseEntity<List<Map<String, Object>>> obtenerCasos(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioLocalizacion.obtenerCasos(id));
    }
}
