package com.tickets.tickets_backend.modelos.enumeraciones;

public enum TipoEntidad {
    CASO("Caso", "📌"),
    INCIDENCIA("Incidencia", "🚨"),
    SERVICIOCOMUNITARIO("Servicio comunitario", "🛠️"),
    USUARIO("Usuario", "👤"),
    COMUNIDAD("Comunidad", "🏘️"),
    LOCALIZACION("Localización", "📍"),
    CHATCASO("Chat del caso", "💬"),
    ;

    private final String nombreLegible;
    private final String icono;

    TipoEntidad(String nombreLegible, String icono) {
        this.nombreLegible = nombreLegible;
        this.icono = icono;
}

    public String getNombreLegible() {
        return nombreLegible;
    }

    public String getIcono() {
        return icono;
    }

    public String getDisplayName() {
        return icono + " " + nombreLegible;
    }
}
