package com.tickets.tickets_backend.modelos.entidades;

import com.tickets.tickets_backend.modelos.enumeraciones.TipoLocalizacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "localizaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Localizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLocalizacion")
    private Integer idLocalizacion;

    @Column(name = "idComunidad", nullable = false)
    private Integer idComunidad;

    @Column(name = "idLocalizacionPadre")
    private Integer localizacionPadre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoLocalizacion", nullable = false, length = 20)
    private TipoLocalizacion tipoLocalizacion;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "direccion", nullable = false, length = 150)
    private String direccion;

    @Column(name = "referencia", nullable = false, length = 150)
    private String referencia;

    @Column(name = "latitud", nullable = false, precision = 10, scale = 8)
    private BigDecimal latitud;

    @Column(name = "longitud", nullable = false, precision = 11, scale = 8)
    private BigDecimal longitud;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    public Integer getIdLocalizacion() {
        return idLocalizacion;
    }

    public void setIdLocalizacion(Integer idLocalizacion) {
        this.idLocalizacion = idLocalizacion;
    }

    public Integer getIdComunidad() {
        return idComunidad;
    }

    public void setIdComunidad(Integer idComunidad) {
        this.idComunidad = idComunidad;
    }

    public Integer getLocalizacionPadre() {
        return localizacionPadre;
    }

    public void setLocalizacionPadre(Integer localizacionPadre) {
        this.localizacionPadre = localizacionPadre;
    }

    public TipoLocalizacion getTipoLocalizacion() {
        return tipoLocalizacion;
    }

    public void setTipoLocalizacion(TipoLocalizacion tipoLocalizacion) {
        this.tipoLocalizacion = tipoLocalizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
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
