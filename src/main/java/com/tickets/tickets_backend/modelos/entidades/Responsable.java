package com.tickets.tickets_backend.modelos.entidades;

import com.tickets.tickets_backend.modelos.enumeraciones.TipoResponsable;
import jakarta.persistence.*;

@Entity
@Table(name = "responsables")
public class Responsable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idresponsable")
    private Integer idResponsable;

    @Column(name = "idcomunidad", nullable = false)
    private Integer idComunidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tiporesponsable", nullable = false, length = 20)
    private TipoResponsable tipoResponsable;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private Boolean activo = true;

    public Responsable() {
    }

    public Integer getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Integer idResponsable) {
        this.idResponsable = idResponsable;
    }

    public Integer getIdComunidad() {
        return idComunidad;
    }

    public void setIdComunidad(Integer idComunidad) {
        this.idComunidad = idComunidad;
    }

    public TipoResponsable getTipoResponsable() {
        return tipoResponsable;
    }

    public void setTipoResponsable(TipoResponsable tipoResponsable) {
        this.tipoResponsable = tipoResponsable;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
