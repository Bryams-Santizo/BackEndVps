package com.coordinacioncafesystem.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "capacitaciones")
public class Capacitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // CAMPOS TEXTO LARGO
    // =========================

    @Column(columnDefinition = "TEXT")
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String tipo;

    @Column(columnDefinition = "TEXT")
    private String tipoOtro;

    @Column(columnDefinition = "TEXT")
    private String duracion;

    @Column(columnDefinition = "TEXT")
    private String requisitos;

    @Column(columnDefinition = "TEXT")
    private String competencias;

    @Column(columnDefinition = "TEXT")
    private String publicoObjetivo;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    @Column(columnDefinition = "TEXT")
    private String costo;

    @Column(columnDefinition = "TEXT")
    private String emisor;

    @Column(columnDefinition = "TEXT")
    private String disponibilidad;

    @Column(columnDefinition = "TEXT")
    private String materiales;

    @Column(columnDefinition = "TEXT")
    private String criteriosevaluacion;

    @Column(columnDefinition = "TEXT")
    private String instructores;

    // =========================
    // ESTADO
    // =========================

    private boolean activo = true;

    // =========================
    // RELACIONES
    // =========================

    @OneToMany(
            mappedBy = "capacitacion",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Inscripcion> inscripciones;

    // =========================
    // GETTERS AND SETTERS
    // =========================

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoOtro() {
        return tipoOtro;
    }

    public void setTipoOtro(String tipoOtro) {
        this.tipoOtro = tipoOtro;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getCompetencias() {
        return competencias;
    }

    public void setCompetencias(String competencias) {
        this.competencias = competencias;
    }

    public String getPublicoObjetivo() {
        return publicoObjetivo;
    }

    public void setPublicoObjetivo(String publicoObjetivo) {
        this.publicoObjetivo = publicoObjetivo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getMateriales() {
        return materiales;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }

    public String getCriteriosevaluacion() {
        return criteriosevaluacion;
    }

    public void setCriteriosevaluacion(String criteriosevaluacion) {
        this.criteriosevaluacion = criteriosevaluacion;
    }

    public String getInstructores() {
        return instructores;
    }

    public void setInstructores(String instructores) {
        this.instructores = instructores;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
}
