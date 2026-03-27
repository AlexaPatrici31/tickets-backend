package com.tickets.tickets_backend.servicios.localizacion;

import com.tickets.tickets_backend.modelos.dtos.Localizacion.DTOActualizarLocalizacionRequest;
import com.tickets.tickets_backend.modelos.dtos.Localizacion.DTOCrearLocalizacionRequest;
import com.tickets.tickets_backend.modelos.entidades.Localizacion;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoLocalizacion;
import com.tickets.tickets_backend.repositorios.LocalizacionRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioLocalizacion {

    private final LocalizacionRepository localizacionRepository;

    public ServicioLocalizacion(LocalizacionRepository localizacionRepository) {
        this.localizacionRepository = localizacionRepository;
    }

    public Map<String, Object> crear(DTOCrearLocalizacionRequest datos) {
        if (datos.getIdLocalizacionPadre() != null) {
            buscarLocalizacion(datos.getIdLocalizacionPadre());
        }

        Localizacion localizacion = new Localizacion();
        localizacion.setIdComunidad(datos.getIdComunidad());
        localizacion.setLocalizacionPadre(datos.getIdLocalizacionPadre());
        localizacion.setTipoLocalizacion(datos.getTipoLocalizacion());
        localizacion.setNombre(datos.getNombre());
        localizacion.setDireccion(datos.getDireccion());
        localizacion.setReferencia(datos.getReferencia());
        localizacion.setLatitud(datos.getLatitud());
        localizacion.setLongitud(datos.getLongitud());
        localizacion.setDescripcion(datos.getDescripcion());
        localizacion.setActivo(true);

        localizacion = localizacionRepository.save(localizacion);

        return construirResponse(localizacion);
    }

    public Page<Map<String, Object>> obtener(Pageable pageable) {
        return localizacionRepository.findAll(pageable)
                .map(this::construirResponse);
    }

    public Map<String, Object> obtenerPorId(Integer id) {
        Localizacion localizacion = buscarLocalizacion(id);
        return construirResponse(localizacion);
    }

    public Map<String, Object> actualizar(Integer id, DTOActualizarLocalizacionRequest datos) {
        Localizacion localizacion = buscarLocalizacion(id);
        localizacion.setNombre(datos.getNombre());
        localizacion.setDireccion(datos.getDireccion());
        localizacion.setReferencia(datos.getReferencia());
        localizacion.setLatitud(datos.getLatitud());
        localizacion.setLongitud(datos.getLongitud());
        localizacion.setDescripcion(datos.getDescripcion());

        localizacion = localizacionRepository.save(localizacion);

        return construirResponse(localizacion);
    }

    public Map<String, Object> activar(Integer id) {
        Localizacion localizacion = buscarLocalizacion(id);
        localizacion.setActivo(true);
        localizacion = localizacionRepository.save(localizacion);
        return construirResponse(localizacion);
    }

    public Map<String, Object> desactivar(Integer id) {
        Localizacion localizacion = buscarLocalizacion(id);
        localizacion.setActivo(false);
        localizacion = localizacionRepository.save(localizacion);
        return construirResponse(localizacion);
    }

    public void eliminar(Integer id) {
        Localizacion localizacion = buscarLocalizacion(id);
        localizacionRepository.delete(localizacion);
    }

    public List<Map<String, Object>> obtenerPorComunidad(Integer idComunidad) {
        return localizacionRepository.findByIdComunidad(idComunidad)
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerPorTipo(TipoLocalizacion tipoLocalizacion) {
        return localizacionRepository.findByTipoLocalizacion(tipoLocalizacion)
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerPorPadre(Integer idPadre) {
        buscarLocalizacion(idPadre);
        return localizacionRepository.findByLocalizacionPadre(idPadre)
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerRaiz() {
        return localizacionRepository.findByLocalizacionPadreIsNull()
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerHijos(Integer id) {
        buscarLocalizacion(id);
        return localizacionRepository.findByLocalizacionPadre(id)
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerCasos(Integer id) {
        buscarLocalizacion(id);
        return List.of();
    }

    private Localizacion buscarLocalizacion(Integer id) {
        return localizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Localización no encontrada con id: " + id));
    }

    private Map<String, Object> construirResponse(Localizacion localizacion) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idLocalizacion", localizacion.getIdLocalizacion());
        response.put("idComunidad", localizacion.getIdComunidad());
        response.put("idLocalizacionPadre", localizacion.getLocalizacionPadre());
        response.put("tipoLocalizacion", localizacion.getTipoLocalizacion());
        response.put("nombre", localizacion.getNombre());
        response.put("direccion", localizacion.getDireccion());
        response.put("referencia", localizacion.getReferencia());
        response.put("latitud", localizacion.getLatitud());
        response.put("longitud", localizacion.getLongitud());
        response.put("descripcion", localizacion.getDescripcion());
        response.put("activo", localizacion.getActivo());
        return response;
    }
}
