package com.tickets.tickets_backend.servicios.categoriaIncidencia;

import com.tickets.tickets_backend.modelos.dtos.categoriaIncidencia.DTOActualizarCategoriaIncidenciaRequest;
import com.tickets.tickets_backend.modelos.dtos.categoriaIncidencia.DTOCrearCategoriaIncidenciaRequest;
import com.tickets.tickets_backend.modelos.entidades.CategoriaIncidencia;
import com.tickets.tickets_backend.modelos.enumeraciones.NivelClasificacion;
import com.tickets.tickets_backend.repositorios.CategoriaIncidenciaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioCategoriaIncidencia {

    private final CategoriaIncidenciaRepository categoriaIncidenciaRepository;

    public ServicioCategoriaIncidencia(CategoriaIncidenciaRepository categoriaIncidenciaRepository) {
        this.categoriaIncidenciaRepository = categoriaIncidenciaRepository;
    }

    public Map<String, Object> crear(DTOCrearCategoriaIncidenciaRequest datos) {
        if (datos.getNivelClasificacion() == NivelClasificacion.SUBCATEGORIA && datos.getIdCategoriaPadre() == null) {
            throw new RuntimeException("La subcategoría requiere idCategoriaPadre");
        }

        if (datos.getIdCategoriaPadre() != null) {
            buscarCategoria(datos.getIdCategoriaPadre());
        }

        CategoriaIncidencia categoria = new CategoriaIncidencia();
        categoria.setCategoriaPadre(datos.getIdCategoriaPadre());
        categoria.setNombre(datos.getNombre());
        categoria.setColorHex(datos.getColorHex());
        categoria.setIcono(datos.getIcono());
        categoria.setNivelClasificacion(datos.getNivelClasificacion());
        categoria.setActivo(true);

        categoria = categoriaIncidenciaRepository.save(categoria);

        return construirResponse(categoria);
    }

    public Page<Map<String, Object>> obtener(Pageable pageable) {
        return categoriaIncidenciaRepository.findAll(pageable)
                .map(this::construirResponse);
    }

    public Map<String, Object> obtenerPorId(Integer id) {
        CategoriaIncidencia categoria = buscarCategoria(id);
        return construirResponse(categoria);
    }

    public Map<String, Object> actualizar(Integer id, DTOActualizarCategoriaIncidenciaRequest datos) {
        CategoriaIncidencia categoria = buscarCategoria(id);
        categoria.setNombre(datos.getNombre());
        categoria.setColorHex(datos.getColorHex());
        categoria.setIcono(datos.getIcono());

        categoria = categoriaIncidenciaRepository.save(categoria);

        return construirResponse(categoria);
    }

    public Map<String, Object> activar(Integer id) {
        CategoriaIncidencia categoria = buscarCategoria(id);
        categoria.setActivo(true);
        categoria = categoriaIncidenciaRepository.save(categoria);
        return construirResponse(categoria);
    }

    public Map<String, Object> desactivar(Integer id) {
        CategoriaIncidencia categoria = buscarCategoria(id);
        categoria.setActivo(false);
        categoria = categoriaIncidenciaRepository.save(categoria);
        return construirResponse(categoria);
    }

    public void eliminar(Integer id) {
        CategoriaIncidencia categoria = buscarCategoria(id);
        categoriaIncidenciaRepository.delete(categoria);
    }

    public List<Map<String, Object>> obtenerRaiz() {
        return categoriaIncidenciaRepository.findByCategoriaPadreIsNull()
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerSubcategorias(Integer id) {
        buscarCategoria(id);
        return categoriaIncidenciaRepository.findByCategoriaPadre(id)
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerPorNivel(NivelClasificacion nivel) {
        return categoriaIncidenciaRepository.findByNivelClasificacion(nivel)
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerActivas() {
        return categoriaIncidenciaRepository.findByActivoTrue()
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    public List<Map<String, Object>> obtenerIncidencias(Integer id) {
        buscarCategoria(id);
        return List.of();
    }

    private CategoriaIncidencia buscarCategoria(Integer id) {
        return categoriaIncidenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
    }

    private Map<String, Object> construirResponse(CategoriaIncidencia categoria) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("idCategoria", categoria.getIdCategoriaIncidencia());
        response.put("idCategoriaPadre", categoria.getCategoriaPadre());
        response.put("nombre", categoria.getNombre());
        response.put("colorHex", categoria.getColorHex());
        response.put("icono", categoria.getIcono());
        response.put("nivelClasificacion", categoria.getNivelClasificacion());
        response.put("activo", categoria.getActivo());
        return response;
    }
}

