package com.coordinacioncafesystem.Dto;

public class DatosAceptacionDTO {
    private String fechaInicio;
    private String hora;
    private String lugar;
    private String mensajeAdicional;

    // Getters y Setters
    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    public String getMensajeAdicional() { return mensajeAdicional; }
    public void setMensajeAdicional(String mensajeAdicional) { this.mensajeAdicional = mensajeAdicional; }
}
