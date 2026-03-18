package com.tickets.tickets_backend.modelos.dtos.chatCaso;

import com.tickets.tickets_backend.modelos.enumeraciones.TipoMensaje;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DTOChatCasoRegistro {
    private TipoMensaje tipoMensaje;
    private String contenido;
    private Boolean esInterno;
    private LocalDateTime fechaMensaje;
    private Boolean leido;

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
