package com.tickets.tickets_backend.modelos.entidades;

import com.tickets.tickets_backend.modelos.enumeraciones.TipoEntidad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_cambio_estado", indexes = {
        @Index(name = "idx_tipo_entidad", columnList = "tipoEntidad"),
        @Index(name = "idx_id_entidad", columnList = "idEntidad"),
        @Index(name = "idx_tipo_accion", columnList = "tipoAccion"),
        @Index(name = "idx_fecha_cambio", columnList = "fechaCambio"),
        @Index(name = "idx_tipo_id", columnList = "tipoEntidad,idEntidad")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialCambioEstado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHistorial")
    private Integer idHistorial;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoEntidad tipoEntidad;

    @Column(nullable = false)
    private Integer idEntidad;

    @Column(length = 50, nullable = false)
    private String tipoAccion = "CAMBIO_ESTADO"; // CREACION, ACTUALIZACION, ELIMINACION, CAMBIO_ESTADO, TRANSICION

    @Column(length = 50)
    private String estadoAnterior;

    @Column(nullable = false, length = 50)
    private String estadoNuevo;

    @Column(nullable = false)
    private LocalDateTime fechaCambio;

    @Column
    private Integer idUsuarioResponsable;

    @Column(length = 150)
    private String nombreUsuarioResponsable;

    @Column(length = 100)
    private String emailUsuario;

    public Integer getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }

    public TipoEntidad getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(TipoEntidad tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }

    public Integer getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Integer idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(String estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public Integer getIdUsuarioResponsable() {
        return idUsuarioResponsable;
    }

    public void setIdUsuarioResponsable(Integer idUsuarioResponsable) {
        this.idUsuarioResponsable = idUsuarioResponsable;
    }

    public String getNombreUsuarioResponsable() {
        return nombreUsuarioResponsable;
    }

    public void setNombreUsuarioResponsable(String nombreUsuarioResponsable) {
        this.nombreUsuarioResponsable = nombreUsuarioResponsable;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}
