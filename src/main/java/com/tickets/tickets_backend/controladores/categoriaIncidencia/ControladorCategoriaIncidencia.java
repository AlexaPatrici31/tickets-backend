package com.tickets.tickets_backend.controladores.categoriaIncidencia;

import com.tickets.tickets_backend.modelos.dtos.categoriaIncidencia.DTOActualizarCategoriaIncidenciaRequest;
import com.tickets.tickets_backend.modelos.dtos.categoriaIncidencia.DTOCrearCategoriaIncidenciaRequest;
import com.tickets.tickets_backend.modelos.enumeraciones.NivelClasificacion;
import com.tickets.tickets_backend.servicios.categoriaIncidencia.ServicioCategoriaIncidencia;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "CategoriaIncidencia", description = "Operaciones del módulo categoría de incidencia")
@RestController
@RequestMapping("/api/v1/categoria-incidencia")
public class ControladorCategoriaIncidencia {

    private final ServicioCategoriaIncidencia servicioCategoriaIncidencia;

    public ControladorCategoriaIncidencia(ServicioCategoriaIncidencia servicioCategoriaIncidencia) {
        this.servicioCategoriaIncidencia = servicioCategoriaIncidencia;
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> crear(@RequestBody DTOCrearCategoriaIncidenciaRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioCategoriaIncidencia.crear(datos));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/obtener")
    public ResponseEntity<Page<Map<String, Object>>> obtener(Pageable pageable) {
        return ResponseEntity.ok(servicioCategoriaIncidencia.obtener(pageable));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioCategoriaIncidencia.obtenerPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @PathVariable Integer id,
            @RequestBody DTOActualizarCategoriaIncidenciaRequest datos) {
        return ResponseEntity.ok(servicioCategoriaIncidencia.actualizar(id, datos));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PatchMapping("/actualizar/{id}/activar")
    public ResponseEntity<Map<String, Object>> activar(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioCategoriaIncidencia.activar(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PatchMapping("/actualizar/{id}/desactivar")
    public ResponseEntity<Map<String, Object>> desactivar(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioCategoriaIncidencia.desactivar(id));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioCategoriaIncidencia.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/obtener/raiz")
    public ResponseEntity<List<Map<String, Object>>> obtenerRaiz() {
        return ResponseEntity.ok(servicioCategoriaIncidencia.obtenerRaiz());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/obtener/{id}/subcategorias")
    public ResponseEntity<List<Map<String, Object>>> obtenerSubcategorias(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioCategoriaIncidencia.obtenerSubcategorias(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/nivel/{nivel}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorNivel(@PathVariable NivelClasificacion nivel) {
        return ResponseEntity.ok(servicioCategoriaIncidencia.obtenerPorNivel(nivel));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/obtener/activas")
    public ResponseEntity<List<Map<String, Object>>> obtenerActivas() {
        return ResponseEntity.ok(servicioCategoriaIncidencia.obtenerActivas());
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/{id}/incidencias")
    public ResponseEntity<List<Map<String, Object>>> obtenerIncidencias(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioCategoriaIncidencia.obtenerIncidencias(id));
    }
}
