package com.coordinacioncafesystem.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;

@Entity
@Table(name = "asistencia_tecnica")

public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- DATOS DEL PRODUCTOR (Se llenan primero) ---
    private String nombreSolicitante;
    private String tipoSolicitante;
    @Column(columnDefinition = "TEXT")
    private String problemaEspecifico;
    private String ubicacion;

    // --- DATOS DEL VÍNCULO (Nuevos campos clave) ---
    private Long institucionId;     // El ID de la tabla Empresa, Gobierno u Org.
    private String tipoInstitucion;

    // Estos campos se llenan para mostrar el nombre sin hacer joins complejos
    private String nombreInstitucionViculada;
    private String especialistas;
    private String costos;

    // Estatus: PENDIENTE o VINCULADO
    private String estatus = "PENDIENTE";

    // Getters y Setters...


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public String getTipoSolicitante() {
        return tipoSolicitante;
    }

    public void setTipoSolicitante(String tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    public String getProblemaEspecifico() {
        return problemaEspecifico;
    }

    public void setProblemaEspecifico(String problemaEspecifico) {
        this.problemaEspecifico = problemaEspecifico;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Long getInstitucionId() {
        return institucionId;
    }

    public void setInstitucionId(Long institucionId) {
        this.institucionId = institucionId;
    }

    public String getTipoInstitucion() {
        return tipoInstitucion;
    }

    public void setTipoInstitucion(String tipoInstitucion) {
        this.tipoInstitucion = tipoInstitucion;
    }

    public String getNombreInstitucionViculada() {
        return nombreInstitucionViculada;
    }

    public void setNombreInstitucionViculada(String nombreInstitucionViculada) {
        this.nombreInstitucionViculada = nombreInstitucionViculada;
    }

    public String getEspecialistas() {
        return especialistas;
    }

    public void setEspecialistas(String especialistas) {
        this.especialistas = especialistas;
    }

    public String getCostos() {
        return costos;
    }

    public void setCostos(String costos) {
        this.costos = costos;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}