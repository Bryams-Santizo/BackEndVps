package com.coordinacioncafesystem.Dto;

import java.time.LocalDateTime;

public class ActividadRecienteDTO {

    private String tipo;       // "MEDIA", "PROYECTO", etc.
    private String mensaje;    // texto a mostrar
    private String etiqueta;   // "Archivo", "Nuevo", etc.
    private String badgeColor; // "info", "success", "primary"...
    private LocalDateTime fecha;

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getEtiqueta() { return etiqueta; }
    public void setEtiqueta(String etiqueta) { this.etiqueta = etiqueta; }

    public String getBadgeColor() { return badgeColor; }
    public void setBadgeColor(String badgeColor) { this.badgeColor = badgeColor; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
