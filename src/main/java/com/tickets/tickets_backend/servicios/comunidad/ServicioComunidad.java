package com.tickets.tickets_backend.servicios.comunidad;

import com.tickets.tickets_backend.modelos.dtos.comunidad.DTOActualizarComunidadRequest;
import com.tickets.tickets_backend.modelos.dtos.comunidad.DTOCrearComunidadRequest;
import com.tickets.tickets_backend.modelos.entidades.Comunidad;
import com.tickets.tickets_backend.modelos.entidades.Responsable;
import com.tickets.tickets_backend.repositorios.ComunidadRepository;
import com.tickets.tickets_backend.repositorios.ResponsableRepository;
import com.tickets.tickets_backend.servicios.usuario.ServicioUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioComunidad {

    private final ComunidadRepository comunidadRepository;
    private final ServicioUsuario servicioUsuario;
    private final ResponsableRepository responsableRepository;

    public ServicioComunidad(ComunidadRepository comunidadRepository, ServicioUsuario servicioUsuario, ResponsableRepository responsableRepository) {
        this.comunidadRepository = comunidadRepository;
        this.servicioUsuario = servicioUsuario;
        this.responsableRepository = responsableRepository;
    }

    public Map<String, Object> crear(DTOCrearComunidadRequest datos) {
        Comunidad comunidad = new Comunidad();
        comunidad.setNombre(datos.getNombre());
        comunidad.setDescripcion(datos.getDescripcion());
        comunidad.setDireccionPrincipal(datos.getDireccionPrincipal());
        comunidad.setCiudad(datos.getCiudad());
        comunidad.setActivo(true);

        comunidad = comunidadRepository.save(comunidad);

        return construirComunidadResponse(comunidad);
    }

    public Page<Map<String, Object>> obtener(Pageable pageable) {
        return comunidadRepository.findAll(pageable)
                .map(this::construirComunidadResponse);
    }

    public Map<String, Object> obtenerPorId(Integer id) {
        Comunidad comunidad = buscarComunidad(id);
        return construirComunidadResponse(comunidad);
    }

    public Map<String, Object> actualizar(Integer id, DTOActualizarComunidadRequest datos) {
        Comunidad comunidad = buscarComunidad(id);
        comunidad.setNombre(datos.getNombre());
        comunidad.setDescripcion(datos.getDescripcion());
        comunidad.setDireccionPrincipal(datos.getDireccionPrincipal());
        comunidad.setCiudad(datos.getCiudad());

        comunidad = comunidadRepository.save(comunidad);

        return construirComunidadResponse(comunidad);
    }

    public Map<String, Object> activar(Integer id) {
        Comunidad comunidad = buscarComunidad(id);
        comunidad.setActivo(true);
        comunidad = comunidadRepository.save(comunidad);
        return construirComunidadResponse(comunidad);
    }

    public Map<String, Object> desactivar(Integer id) {
        Comunidad comunidad = buscarComunidad(id);
        comunidad.setActivo(false);
        comunidad = comunidadRepository.save(comunidad);
        return construirComunidadResponse(comunidad);
    }

    public void eliminar(Integer id) {
        Comunidad comunidad = buscarComunidad(id);

        if (Boolean.FALSE.equals(comunidad.getActivo())) {
            return;
        }

        comunidad.setActivo(false);
        comunidadRepository.save(comunidad);
    }

    public List<Map<String, Object>> obtenerActivas() {
        return comunidadRepository.findByActivoTrue()
                .stream()
                .map(this::construirComunidadResponse)
                .toList();
    }

    public Page<Map<String, Object>> obtenerUsuarios(Integer id, Pageable pageable) {
        buscarComunidad(id);
        return servicioUsuario.obtenerPorComunidad(id, pageable);
    }

    public List<Map<String, Object>> obtenerResponsables(Integer id) {
        buscarComunidad(id);
        return responsableRepository.findByIdComunidad(id)
                .stream()
                .map(this::construirResponsableResponse)
                .toList();
    }
    private Map<String, Object> construirResponsableResponse(Responsable responsable) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idResponsable", responsable.getIdResponsable());
        response.put("idComunidad", responsable.getIdComunidad());
        response.put("tipoResponsable", responsable.getTipoResponsable());
        response.put("descripcion", responsable.getDescripcion());
        response.put("activo", responsable.getActivo());
        return response;
    }


    public List<Map<String, Object>> obtenerLocalizaciones(Integer id) {
        buscarComunidad(id);
        return List.of();
    }

    public Page<Map<String, Object>> obtenerCasos(Integer id, Pageable pageable) {
        buscarComunidad(id);
        return new PageImpl<>(List.of(), pageable, 0);
    }

    public List<Map<String, Object>> obtenerIncidencias(Integer id) {
        buscarComunidad(id);
        return List.of();
    }

    public List<Map<String, Object>> obtenerServicios(Integer id) {
        buscarComunidad(id);
        return List.of();
    }

    public Map<String, Object> obtenerResumen(Integer id) {
        buscarComunidad(id);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idComunidad", id);
        response.put("totalCasos", 0);
        response.put("totalIncidencias", 0);
        response.put("totalServicios", 0);
        response.put("totalUsuarios", 0);
        return response;
    }

    private Comunidad buscarComunidad(Integer id) {
        return comunidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comunidad no encontrada con id: " + id));
    }

    private Map<String, Object> construirComunidadResponse(Comunidad comunidad) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idComunidad", comunidad.getIdComunidad());
        response.put("nombre", comunidad.getNombre());
        response.put("descripcion", comunidad.getDescripcion());
        response.put("direccionPrincipal", comunidad.getDireccionPrincipal());
        response.put("ciudad", comunidad.getCiudad());
        response.put("activo", comunidad.getActivo());
        return response;
    }
}
