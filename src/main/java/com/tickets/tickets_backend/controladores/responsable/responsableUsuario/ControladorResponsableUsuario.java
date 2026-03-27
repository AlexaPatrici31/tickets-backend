package com.tickets.tickets_backend.controladores.responsable.responsableUsuario;

import com.tickets.tickets_backend.servicios.responsable.responsableUsuario.ServicioResponsableUsuario;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "ResponsableUsuario", description = "Operaciones de vínculo entre responsable y usuario")
@RestController
@RequestMapping("/api/v1/responsable-usuario")
public class ControladorResponsableUsuario {

    private final ServicioResponsableUsuario servicioResponsableUsuario;

    public ControladorResponsableUsuario(ServicioResponsableUsuario servicioResponsableUsuario) {
        this.servicioResponsableUsuario = servicioResponsableUsuario;
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @PostMapping("/vincular/{idResponsable}/usuario/{idUsuario}")
    public ResponseEntity<Map<String, Object>> vincular(@PathVariable Integer idResponsable,
                                                        @PathVariable Integer idUsuario) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicioResponsableUsuario.vincular(idResponsable, idUsuario));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @DeleteMapping("/desvincular/{idResponsable}/usuario/{idUsuario}")
    public ResponseEntity<Void> desvincular(@PathVariable Integer idResponsable,
                                            @PathVariable Integer idUsuario) {
        servicioResponsableUsuario.desvincular(idResponsable, idUsuario);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/responsable/{idResponsable}/usuarios")
    public ResponseEntity<List<Map<String, Object>>> obtenerUsuariosDeResponsable(@PathVariable Integer idResponsable) {
        return ResponseEntity.ok(servicioResponsableUsuario.obtenerUsuariosDeResponsable(idResponsable));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD','USUARIOGENERAL')")
    @GetMapping("/obtener/usuario/{idUsuario}/responsables")
    public ResponseEntity<List<Map<String, Object>>> obtenerResponsablesDeUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(servicioResponsableUsuario.obtenerResponsablesDeUsuario(idUsuario));
    }

    @PreAuthorize("hasAnyRole('ADMINGENERAL','ADMINCOMUNIDAD')")
    @GetMapping("/obtener/existe/{idResponsable}/usuario/{idUsuario}")
    public ResponseEntity<Map<String, Object>> existeVinculo(@PathVariable Integer idResponsable,
                                                             @PathVariable Integer idUsuario) {
        return ResponseEntity.ok(servicioResponsableUsuario.existeVinculo(idResponsable, idUsuario));
    }
}
