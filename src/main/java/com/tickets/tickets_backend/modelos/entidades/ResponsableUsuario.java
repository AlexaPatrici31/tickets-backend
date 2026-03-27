package com.tickets.tickets_backend.modelos.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "responsable_usuario")
public class ResponsableUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_responsable_usuario")
    private Integer idResponsableUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_responsable", nullable = false)
    private Responsable responsable;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_vinculacion", nullable = false)
    private LocalDateTime fechaVinculacion;

    public Integer getIdResponsableUsuario() {
        return idResponsableUsuario;
    }

    public void setIdResponsableUsuario(Integer idResponsableUsuario) {
        this.idResponsableUsuario = idResponsableUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaVinculacion() {
        return fechaVinculacion;
    }

    public void setFechaVinculacion(LocalDateTime fechaVinculacion) {
        this.fechaVinculacion = fechaVinculacion;
    }

    @PrePersist
    public void prePersist() {
        if (fechaVinculacion == null) {
            fechaVinculacion = LocalDateTime.now();
        }
        if (activo == null) {
            activo = true;
        }
    }
}
