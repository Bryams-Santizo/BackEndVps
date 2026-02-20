package com.coordinacioncafesystem.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "galeria_evidencias")
@JsonIgnoreProperties({"usuarios", "proyectos", "eventos", "participantes", "participante", "hibernateLazyInitializer", "handler"})

public class GaleriaEvidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "actividad", length = 255)
    private String actividad;

    @Column(name = "subtema", length = 255)
    private String subtema;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    @Column(name = "ruta_imagen")
    private String rutaImagen;   // Nombre del archivo en el servidor

    @Column(name = "fecha_registro")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participante_id")
    private Participantes participante; // Relación con tu entidad principal
    @ManyToOne
    @JoinColumn(name = "tecnologico_id")
    private Tecnologicos tecnologico;

    @PrePersist

    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }

    // Getters y Setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getSubtema() {
        return subtema;
    }

    public void setSubtema(String subtema) {
        this.subtema = subtema;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }


    public Tecnologicos getTecnologico() {
        return tecnologico;
    }

    public void setTecnologico(Tecnologicos tecnologico) {
        this.tecnologico = tecnologico;
    }

    public Participantes getParticipante() {
        return participante;
    }

    public void setParticipante(Participantes participante) {
        this.participante = participante;
    }
}
