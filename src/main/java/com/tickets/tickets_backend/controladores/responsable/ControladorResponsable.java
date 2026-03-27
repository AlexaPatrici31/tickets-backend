package com.tickets.tickets_backend.controladores.responsable;

import com.tickets.tickets_backend.modelos.dtos.responsable.DTOActualizarResponsableRequest;
import com.tickets.tickets_backend.modelos.dtos.responsable.DTOCrearResponsableRequest;
import com.tickets.tickets_backend.servicios.responsable.ServicioResponsable;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Responsable", description = "Operaciones del módulo responsable")
@RestController
@RequestMapping("/api/v1/responsable")
public class ControladorResponsable {

    private final ServicioResponsable servicioResponsable;

    public ControladorResponsable(ServicioResponsable servicioResponsable) {
        this.servicioResponsable = servicioResponsable;
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> crear(@RequestBody DTOCrearResponsableRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioResponsable.crear(datos));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @GetMapping("/obtener")
    public ResponseEntity<Page<Map<String, Object>>> obtener(Pageable pageable) {
        return ResponseEntity.ok(servicioResponsable.obtener(pageable));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioResponsable.obtenerPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Integer id,
                                                          @RequestBody DTOActualizarResponsableRequest datos) {
        return ResponseEntity.ok(servicioResponsable.actualizar(id, datos));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PatchMapping("/actualizar/{id}/activar")
    public ResponseEntity<Map<String, Object>> activar(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioResponsable.activar(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PatchMapping("/actualizar/{id}/desactivar")
    public ResponseEntity<Map<String, Object>> desactivar(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioResponsable.desactivar(id));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioResponsable.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/comunidad/{idComunidad}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorComunidad(@PathVariable Integer idComunidad) {
        return ResponseEntity.ok(servicioResponsable.obtenerPorComunidad(idComunidad));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/tipo/{tipoResponsable}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorTipo(@PathVariable String tipoResponsable) {
        return ResponseEntity.ok(servicioResponsable.obtenerPorTipo(tipoResponsable));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/activos")
    public ResponseEntity<List<Map<String, Object>>> obtenerActivos() {
        return ResponseEntity.ok(servicioResponsable.obtenerActivos());
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD','RESPONSABLE')")
    @GetMapping("/obtener/{id}/casos-asignados")
    public ResponseEntity<List<Map<String, Object>>> obtenerCasosAsignados(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioResponsable.obtenerCasosAsignados(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/{id}/usuarios")
    public ResponseEntity<List<Map<String, Object>>> obtenerUsuarios(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioResponsable.obtenerUsuarios(id));
    }
}


