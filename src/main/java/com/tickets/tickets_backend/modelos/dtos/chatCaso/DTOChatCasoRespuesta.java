package com.tickets.tickets_backend.modelos.dtos.chatCaso;

import lombok.Data;

@Data
public class DTOChatCasoRespuesta extends DTOChatCasoRegistro {
    private Integer idChatCaso;
    private Integer idCaso;
    private Integer idUsuarioEmisor;

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
}
