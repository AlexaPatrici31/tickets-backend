package com.tickets.tickets_backend.controladores.usuario;

import com.tickets.tickets_backend.modelos.dtos.usuario.*;
import com.tickets.tickets_backend.modelos.enumeraciones.EstadoUsuario;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoUsuario;
import com.tickets.tickets_backend.servicios.usuario.ServicioUsuario;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Usuario", description = "Operaciones del módulo de usuarios")
@RestController
@RequestMapping("/api/v1/usuario")
public class ControladorUsuario {

    private final ServicioUsuario servicioUsuario;

    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @PostMapping("/bootstrap-admin")
    public ResponseEntity<Map<String, Object>> bootstrapAdmin(@RequestBody DTOCrearUsuarioRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioUsuario.bootstrapAdmin(datos));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> crear(@RequestBody DTOCrearUsuarioRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioUsuario.crear(datos));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @PostMapping("/crearadmin-comunidad")
    public ResponseEntity<Map<String, Object>> crearAdminComunidad(@RequestBody DTOCrearUsuarioRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioUsuario.crearAdminComunidad(datos));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PostMapping("/crearresponsable")
    public ResponseEntity<Map<String, Object>> crearResponsable(@RequestBody DTOCrearUsuarioRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioUsuario.crearResponsable(datos));
    }

    @PostMapping("/crearciudadano")
    public ResponseEntity<Map<String, Object>> crearCiudadano(@RequestBody DTOCrearUsuarioRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioUsuario.crearCiudadano(datos));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @GetMapping("/obtener")
    public ResponseEntity<Page<Map<String, Object>>> obtener(Pageable pageable) {
        return ResponseEntity.ok(servicioUsuario.obtener(pageable));
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioUsuario.obtenerPorId(id));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Integer id,
                                                          @RequestBody DTOActualizarUsuarioRequest datos) {
        return ResponseEntity.ok(servicioUsuario.actualizar(id, datos));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @PatchMapping("/actualizar/{id}/estado")
    public ResponseEntity<Map<String, Object>> cambiarEstado(@PathVariable Integer id,
                                                             @RequestBody DTOCambiarEstadoUsuarioRequest datos) {
        return ResponseEntity.ok(servicioUsuario.cambiarEstado(id, datos));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PatchMapping("/actualizar/{id}/activar")
    public ResponseEntity<Map<String, Object>> activar(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioUsuario.activar(id));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PatchMapping("/actualizar/{id}/suspender")
    public ResponseEntity<Map<String, Object>> suspender(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioUsuario.suspender(id));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @PatchMapping("/actualizar/{id}/eliminar")
    public ResponseEntity<Map<String, Object>> eliminarLogico(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioUsuario.eliminarLogico(id));
    }

    @PatchMapping("/actualizar/{id}/foto")
    public ResponseEntity<Map<String, Object>> actualizarFoto(@PathVariable Integer id,
                                                              @RequestBody DTOActualizarFotoPerfilRequest datos) {
        return ResponseEntity.ok(servicioUsuario.actualizarFoto(id, datos));
    }

    @PatchMapping("/actualizar/{id}/contrasena")
    public ResponseEntity<Map<String, Object>> cambiarContrasena(@PathVariable Integer id,
                                                                 @RequestBody DTOActualizarContrasenaUsuarioRequest datos) {
        return ResponseEntity.ok(servicioUsuario.cambiarContrasena(id, datos));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @PatchMapping("/actualizar/{id}/tipo")
    public ResponseEntity<Map<String, Object>> cambiarTipo(@PathVariable Integer id,
                                                           @RequestBody DTOCambiarTipoUsuarioRequest datos) {
        return ResponseEntity.ok(servicioUsuario.cambiarTipo(id, datos));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @PatchMapping("/actualizar/{id}/comunidad")
    public ResponseEntity<Map<String, Object>> cambiarComunidad(@PathVariable Integer id,
                                                                @RequestBody DTOCambiarComunidadUsuarioRequest datos) {
        return ResponseEntity.ok(servicioUsuario.cambiarComunidad(id, datos));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarFisico(@PathVariable Integer id) {
        servicioUsuario.eliminarFisico(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/comunidad/{idComunidad}")
    public ResponseEntity<Page<Map<String, Object>>> obtenerPorComunidad(@PathVariable Integer idComunidad,
                                                                         Pageable pageable) {
        return ResponseEntity.ok(servicioUsuario.obtenerPorComunidad(idComunidad, pageable));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @GetMapping("/obtener/tipo/{tipoUsuario}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorTipo(@PathVariable TipoUsuario tipoUsuario) {
        return ResponseEntity.ok(servicioUsuario.obtenerPorTipo(tipoUsuario));
    }

    @PreAuthorize("hasRole('ADMINGENERAL')")
    @GetMapping("/obtener/estado/{estado}")
    public ResponseEntity<List<Map<String, Object>>> obtenerPorEstado(@PathVariable EstadoUsuario estado) {
        return ResponseEntity.ok(servicioUsuario.obtenerPorEstado(estado));
    }

    @GetMapping("/obtener/{id}/casos")
    public ResponseEntity<List<Map<String, Object>>> obtenerCasos(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioUsuario.obtenerCasos(id));
    }

    @GetMapping("/obtener/{id}/responsabilidades")
    public ResponseEntity<List<Map<String, Object>>> obtenerResponsabilidades(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioUsuario.obtenerResponsabilidades(id));
    }

    @GetMapping("/obtener/{id}/historial-cambios")
    public ResponseEntity<List<Map<String, Object>>> obtenerHistorialCambios(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioUsuario.obtenerHistorialCambios(id));
    }
}
