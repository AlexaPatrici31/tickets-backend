package com.tickets.tickets_backend.controladores.incidencia;

import com.tickets.tickets_backend.modelos.dtos.incidencia.DTOIncidenciaActualizar;
import com.tickets.tickets_backend.modelos.dtos.incidencia.DTOIncidenciaRegistro;
import com.tickets.tickets_backend.servicios.incidencia.ServicioIncidencia;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Incidencia", description = "Operaciones del módulo de incidencia")
@RestController
@RequestMapping("/api/v1/incidencia")
public class ControladorIncidencia {

    private final ServicioIncidencia servicioIncidencia;

    public ControladorIncidencia(ServicioIncidencia servicioIncidencia) {
        this.servicioIncidencia = servicioIncidencia;
    }

    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL')")
    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> crear(@RequestBody DTOIncidenciaRegistro datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioIncidencia.crear(datos));
    }

    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioIncidencia.obtenerPorId(id));
    }

    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/obtener/usuario/{idUsuario}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(servicioIncidencia.obtenerPorUsuario(idUsuario));
    }

    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/obtener/comunidad/{idComunidad}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorComunidad(@PathVariable Integer idComunidad) {
        return ResponseEntity.ok(servicioIncidencia.obtenerPorComunidad(idComunidad));
    }

    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/obtener/activas/comunidad/{idComunidad}")
    public ResponseEntity<List<Map<String, Object>>> obtenerActivasByComunidad(@PathVariable Integer idComunidad) {
        return ResponseEntity.ok(servicioIncidencia.obtenerActivasByComunidad(idComunidad));
    }

    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/obtener/categoria/{idCategoria}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorCategoria(@PathVariable Integer idCategoria) {
        return ResponseEntity.ok(servicioIncidencia.obtenerPorCategoria(idCategoria));
    }

    @PreAuthorize("hasAnyRole('ADMINCOMUNIDAD','ADMINGENERAL')")
    @GetMapping("/obtener/subcategoria/{idSubcategoria}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorSubcategoria(@PathVariable Integer idSubcategoria) {
        return ResponseEntity.ok(servicioIncidencia.obtenerPorSubcategoria(idSubcategoria));
    }

    @PreAuthorize("hasAnyRole('USUARIOGENERAL','ADMINCOMUNIDAD','ADMINGENERAL','RESPONSABLE')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Integer id,
                                                          @RequestBody DTOIncidenciaActualizar datos) {
        return ResponseEntity.ok(servicioIncidencia.actualizar(id, datos));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioIncidencia.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
