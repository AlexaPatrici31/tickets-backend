package com.tickets.tickets_backend.modelos.dtos.reporte;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DTOReporteUsuarioActividadItem {
    @JsonProperty("id_usuario")
    private Long idUsuario;

    @JsonProperty("nombre_completo")
    private String nombreCompleto;

    @JsonProperty("tipo_usuario")
    private String tipoUsuario;
    // Valores: "ADMIN_GENERAL", "ADMIN_COMUNIDAD", "RESPONSABLE", "USUARIO_GENERAL"

    @JsonProperty("casos_creados")
    private Long casosCreados;

    @JsonProperty("casos_atendidos")
    private Long casosAtendidos;

    @JsonProperty("mensajes_chat")
    private Long mensajesChat;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Long getCasosCreados() {
        return casosCreados;
    }

    public void setCasosCreados(Long casosCreados) {
        this.casosCreados = casosCreados;
    }

    public Long getCasosAtendidos() {
        return casosAtendidos;
    }

    public void setCasosAtendidos(Long casosAtendidos) {
        this.casosAtendidos = casosAtendidos;
    }

    public Long getMensajesChat() {
        return mensajesChat;
    }

    public void setMensajesChat(Long mensajesChat) {
        this.mensajesChat = mensajesChat;
    }
}
