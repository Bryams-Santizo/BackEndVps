package com.coordinacioncafesystem.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "transferencias")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImpulsoTecnologico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreTecnologia;
    private String tipo;
    @Column(length = 1000)
    private String descripcion;
    private String capacidades;
    private String servicios;
    private String requisitos;
    private String disponibilidad;
    private String costos;
    private String responsable;

    // Guardamos la ruta o el nombre del archivo
    private String documentoPath;
    private String cartaPath;

    // Getters y Setters...


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreTecnologia() {
        return nombreTecnologia;
    }

    public void setNombreTecnologia(String nombreTecnologia) {
        this.nombreTecnologia = nombreTecnologia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCapacidades() {
        return capacidades;
    }

    public void setCapacidades(String capacidades) {
        this.capacidades = capacidades;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getCostos() {
        return costos;
    }

    public void setCostos(String costos) {
        this.costos = costos;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getDocumentoPath() {
        return documentoPath;
    }

    public void setDocumentoPath(String documentoPath) {
        this.documentoPath = documentoPath;
    }

    public String getCartaPath() {
        return cartaPath;
    }

    public void setCartaPath(String cartaPath) {
        this.cartaPath = cartaPath;
    }
}


