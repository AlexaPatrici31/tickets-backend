package com.tickets.tickets_backend.modelos.entidades;

import com.tickets.tickets_backend.modelos.enumeraciones.NivelClasificacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categoriasIncidencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaIncidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoriaIncidencia")
    private Integer idCategoriaIncidencia;

    @ManyToOne
    @JoinColumn(name = "idCategoriaPadre")
    private CategoriaIncidencia categoriaPadre;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String colorHex;

    @Column(length = 100)
    private String icono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NivelClasificacion nivelClasificacion;

    @Column(nullable = false)
    private Boolean activo = true;

    public Integer getIdCategoriaIncidencia() {
        return idCategoriaIncidencia;
    }

    public void setIdCategoriaIncidencia(Integer idCategoriaIncidencia) {
        this.idCategoriaIncidencia = idCategoriaIncidencia;
    }

    public CategoriaIncidencia getCategoriaPadre() {
        return categoriaPadre;
    }

    public void setCategoriaPadre(CategoriaIncidencia categoriaPadre) {
        this.categoriaPadre = categoriaPadre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public NivelClasificacion getNivelClasificacion() {
        return nivelClasificacion;
    }

    public void setNivelClasificacion(NivelClasificacion nivelClasificacion) {
        this.nivelClasificacion = nivelClasificacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
