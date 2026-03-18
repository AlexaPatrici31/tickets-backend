package com.tickets.tickets_backend.modelos.entidades;

import com.tickets.tickets_backend.modelos.enumeraciones.TipoMensaje;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chatscasos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatCaso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idChatCaso")
    private Integer idChatCaso;

    @Column(name = "idCaso")
    private Integer idCaso;

    @Column(name = "idUsuarioEmisor")
    private Integer idUsuarioEmisor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoMensaje tipoMensaje;

    @Column(nullable = false, length = 50)
    private String contenido;

    @Column (nullable = false)
    private Boolean esInterno = true;

    @Column(nullable = false)
    private LocalDateTime fechaMensaje;

    @Column (nullable = false)
    private Boolean leido = true;

    public Integer getIdChatCaso() {
        return idChatCaso;
    }

    public void setIdChatCaso(Integer idChatCaso) {
        this.idChatCaso = idChatCaso;
    }

    public Integer getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(Integer idCaso) {
        this.idCaso = idCaso;
    }

    public Integer getIdUsuarioEmisor() {
        return idUsuarioEmisor;
    }

    public void setIdUsuarioEmisor(Integer idUsuarioEmisor) {
        this.idUsuarioEmisor = idUsuarioEmisor;
    }

    public TipoMensaje getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(TipoMensaje tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }


    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Boolean getEsInterno() {
        return esInterno;
    }

    public void setEsInterno(Boolean esInterno) {
        this.esInterno = esInterno;
    }

    public LocalDateTime getFechaMensaje() {
        return fechaMensaje;
    }

    public void setFechaMensaje(LocalDateTime fechaMensaje) {
        this.fechaMensaje = fechaMensaje;
    }

    public Boolean getLeido() {
        return leido;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }
}
