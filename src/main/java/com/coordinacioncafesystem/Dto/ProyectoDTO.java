package com.coordinacioncafesystem.Dto;

public class ProyectoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;
    private String empresaVinculada;
    private String tecnologico;
    private String actividad;
    private String imagenUrl;       // ya estaba
    private String documentoUrl;    // NUEVO: enlace para descargar/ver PDF, DOC, etc.

    public ProyectoDTO() {}

    public ProyectoDTO(Long id, String nombre, String descripcion,
                       String estado, String empresaVinculada,
                       String tecnologico, String actividad,
                       String imagenUrl, String documentoUrl) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.empresaVinculada = empresaVinculada;
        this.tecnologico = tecnologico;
        this.actividad = actividad;
        this.imagenUrl = imagenUrl;
        this.documentoUrl = documentoUrl;
    }

    // getters & setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEmpresaVinculada() {
        return empresaVinculada;
    }

    public void setEmpresaVinculada(String empresaVinculada) {
        this.empresaVinculada = empresaVinculada;
    }

    public String getTecnologico() {
        return tecnologico;
    }

    public void setTecnologico(String tecnologico) {
        this.tecnologico = tecnologico;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getDocumentoUrl() {
        return documentoUrl;
    }

    public void setDocumentoUrl(String documentoUrl) {
        this.documentoUrl = documentoUrl;
    }
}
