package com.tickets.tickets_backend.servicios.estadoCaso;

import com.tickets.tickets_backend.modelos.dtos.estadoCaso.DTOActualizarEstadoCasoRequest;
import com.tickets.tickets_backend.modelos.dtos.estadoCaso.DTOCrearEstadoCasoRequest;
import com.tickets.tickets_backend.modelos.entidades.EstadoCaso;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoCaso;
import com.tickets.tickets_backend.repositorios.CasoRepository;
import com.tickets.tickets_backend.repositorios.EstadoCasoRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioEstadoCaso {

    private final EstadoCasoRepository estadoCasoRepository;
    private final CasoRepository casoRepository;

    public ServicioEstadoCaso(EstadoCasoRepository estadoCasoRepository,
                              CasoRepository casoRepository) {
        this.estadoCasoRepository = estadoCasoRepository;
        this.casoRepository = casoRepository;
    }

    public Map<String, Object> crear(DTOCrearEstadoCasoRequest datos) {
        validarDatos(datos.getTipoCaso(), datos.getOrden(), datos.getNombre(), datos.getEsFinal());

        boolean existeOrden = estadoCasoRepository
                .existsByTipoCasoAndOrden(datos.getTipoCaso(), datos.getOrden());

        if (existeOrden) {
            throw new RuntimeException("Ya existe un estado con ese orden para el tipo de caso indicado");
        }

        EstadoCaso estadoCaso = new EstadoCaso();
        estadoCaso.setTipoCaso(datos.getTipoCaso());
        estadoCaso.setNombre(datos.getNombre());
        estadoCaso.setDescripcion(datos.getDescripcion());
        estadoCaso.setOrden(datos.getOrden());
        estadoCaso.setEsFinal(datos.getEsFinal());
        estadoCaso.setActivo(true);

        estadoCaso = estadoCasoRepository.save(estadoCaso);

        return construirResponse(estadoCaso);
    }

    public List<Map<String, Object>> obtener() {
        return estadoCasoRepository.findAll()
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public Map<String, Object> obtenerPorId(Integer id) {
        EstadoCaso estadoCaso = buscarEstadoCaso(id);
        return construirResponse(estadoCaso);
    }

    public Map<String, Object> actualizar(Integer id, DTOActualizarEstadoCasoRequest datos) {
        validarDatos(datos.getTipoCaso(), datos.getOrden(), datos.getNombre(), datos.getEsFinal());

        EstadoCaso estadoCaso = buscarEstadoCaso(id);

        boolean existeOrden = estadoCasoRepository
                .existsByTipoCasoAndOrdenAndIdEstadoCasoNot(datos.getTipoCaso(), datos.getOrden(), id);

        if (existeOrden) {
            throw new RuntimeException("Ya existe un estado con ese orden para el tipo de caso indicado");
        }

        estadoCaso.setTipoCaso(datos.getTipoCaso());
        estadoCaso.setNombre(datos.getNombre());
        estadoCaso.setDescripcion(datos.getDescripcion());
        estadoCaso.setOrden(datos.getOrden());
        estadoCaso.setEsFinal(datos.getEsFinal());

        estadoCaso = estadoCasoRepository.save(estadoCaso);

        return construirResponse(estadoCaso);
    }

    public Map<String, Object> activar(Integer id) {
        EstadoCaso estadoCaso = buscarEstadoCaso(id);
        estadoCaso.setActivo(true);
        estadoCaso = estadoCasoRepository.save(estadoCaso);
        return construirResponse(estadoCaso);
    }

    public Map<String, Object> desactivar(Integer id) {
        EstadoCaso estadoCaso = buscarEstadoCaso(id);
        estadoCaso.setActivo(false);
        estadoCaso = estadoCasoRepository.save(estadoCaso);
        return construirResponse(estadoCaso);
    }

    public void eliminar(Integer id) {
        EstadoCaso estadoCaso = buscarEstadoCaso(id);

        boolean tieneCasos = casoRepository.existsByIdEstadoCasoActual(id);
        if (tieneCasos) {
            throw new RuntimeException("No se puede eliminar el estado porque tiene casos asociados");
        }

        estadoCasoRepository.delete(estadoCaso);
    }

    public List<Map<String, Object>> obtenerPorTipoCaso(TipoCaso tipoCaso) {
        return estadoCasoRepository.findByTipoCaso(tipoCaso)
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerPorTipoCasoOrdenados(TipoCaso tipoCaso) {
        return estadoCasoRepository.findByTipoCasoOrderByOrdenAsc(tipoCaso)
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerFinales() {
        return estadoCasoRepository.findByEsFinalTrue()
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerActivos() {
        return estadoCasoRepository.findByActivoTrue()
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    private EstadoCaso buscarEstadoCaso(Integer id) {
        return estadoCasoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado de caso no encontrado con id: " + id));
    }

    private void validarDatos(TipoCaso tipoCaso, Integer orden, String nombre, Boolean esFinal) {
        if (tipoCaso == null) {
            throw new RuntimeException("El tipoCaso es obligatorio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new RuntimeException("El nombre es obligatorio");
        }
        if (orden == null || orden < 1) {
            throw new RuntimeException("El orden debe ser mayor que 0");
        }
        if (esFinal == null) {
            throw new RuntimeException("El campo esFinal es obligatorio");
        }
    }

    private Map<String, Object> construirResponse(EstadoCaso estadoCaso) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idEstadoCaso", estadoCaso.getIdEstadoCaso());
        response.put("tipoCaso", estadoCaso.getTipoCaso());
        response.put("nombre", estadoCaso.getNombre());
        response.put("descripcion", estadoCaso.getDescripcion());
        response.put("orden", estadoCaso.getOrden());
        response.put("esFinal", estadoCaso.getEsFinal());
        response.put("activo", estadoCaso.getActivo());
        return response;
    }
}
