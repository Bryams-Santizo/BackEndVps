package com.coordinacioncafesystem.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "eventos")
public class Eventos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_evento;

    // Campos originales
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipo;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT-6") // O "America/Mexico_City"
    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio; // Usaremos este campo para la fechaHorario

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_fin")
    private Date fechaFin; // Opcional si solo es un día

    @Column(nullable = false)
    private String lugar; // Mapea a 'sede' en Angular

    // Campos agregados de Angular
    @Column(nullable = false, columnDefinition = "TEXT")
    private String organizador;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String objetivo; // Mapea a 'objetivo' en Angular

    @Column(name = "publico_objetivo", columnDefinition = "TEXT")
    private String publicoObjetivo;

    @Column(columnDefinition = "TEXT")
    private String programa;

    @Column(columnDefinition = "TEXT")
    private String ponentes;

    @Column(columnDefinition = "TEXT")
    private String requisitos;

    @Column(columnDefinition = "TEXT")
    private String materiales;

    // Campo para la evidencia (ruta del archivo)
    @Column(name = "ruta_evidencia")
    private String rutaEvidencia;

    // Campo para la imagen principal (Banner/Thumbnail)
    @Column(name = "ruta_imagen")
    private String rutaImagen; // Guarda el nombre del archivo de imagen

    // Campo para el enlace externo (Facebook, YouTube, etc.)
    @Column(name = "enlace_externo", columnDefinition = "TEXT")
    private String enlaceExterno;

    @ManyToOne
    @JoinColumn(name = "tecnologico_id")
    private Tecnologicos tecnologico;


    // Constructor (opcional pero recomendado)
    public Eventos() {}

    // === Getters y Setters ===


    public Long getId_evento() {
        return id_evento;
    }

    public void setId_evento(Long id_evento) {
        this.id_evento = id_evento;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getPublicoObjetivo() {
        return publicoObjetivo;
    }

    public void setPublicoObjetivo(String publicoObjetivo) {
        this.publicoObjetivo = publicoObjetivo;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getPonentes() {
        return ponentes;
    }

    public void setPonentes(String ponentes) {
        this.ponentes = ponentes;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getMateriales() {
        return materiales;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }

    public String getRutaEvidencia() {
        return rutaEvidencia;
    }

    public void setRutaEvidencia(String rutaEvidencia) {
        this.rutaEvidencia = rutaEvidencia;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getEnlaceExterno() {
        return enlaceExterno;
    }

    public void setEnlaceExterno(String enlaceExterno) {
        this.enlaceExterno = enlaceExterno;
    }

    public Tecnologicos getTecnologico() {
        return tecnologico;
    }

    public void setTecnologico(Tecnologicos tecnologico) {
        this.tecnologico = tecnologico;
    }
}