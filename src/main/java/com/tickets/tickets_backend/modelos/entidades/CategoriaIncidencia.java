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
    @Column(name = "idClasificacionIncidencia")
    private Integer idClasificacionIncidencia;

    @ManyToOne
    @JoinColumn(name = "idClasificacionIncidencia")
    private CategoriaIncidencia idClasificacioPadre;

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


    public Integer getIdClasificacionIncidencia() {
        return idClasificacionIncidencia;
    }

    public void setIdClasificacionIncidencia(Integer idClasificacionIncidencia) {
        this.idClasificacionIncidencia = idClasificacionIncidencia;
    }

    public CategoriaIncidencia getIdClasificacioPadre() {
        return idClasificacioPadre;
    }

    public void setIdClasificacioPadre(CategoriaIncidencia idClasificacioPadre) {
        this.idClasificacioPadre = idClasificacioPadre;
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
