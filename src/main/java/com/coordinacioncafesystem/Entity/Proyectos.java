package com.coordinacioncafesystem.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "proyectos")
public class Proyectos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(columnDefinition = "text")
    private String descripcion;

    @Column(columnDefinition = "text")
    private String actividad;

    @Column(columnDefinition = "text")
    private String necesidad;

    // En curso, Finalizado, etc.
    private String estadoProyecto;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private String empresaVinculada;

    @ManyToOne
    @JoinColumn(name = "tecnologico_id")
    private Tecnologicos tecnologico;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avance> avances;

    // 👇 No incluir la lista de medias en el JSON del proyecto
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Media> medias;

    // ========== getters / setters ==========

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getActividad() { return actividad; }
    public void setActividad(String actividad) { this.actividad = actividad; }

    public String getNecesidad() { return necesidad; }
    public void setNecesidad(String necesidad) { this.necesidad = necesidad; }

    public String getEstadoProyecto() { return estadoProyecto; }
    public void setEstadoProyecto(String estadoProyecto) { this.estadoProyecto = estadoProyecto; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public String getEmpresaVinculada() { return empresaVinculada; }
    public void setEmpresaVinculada(String empresaVinculada) { this.empresaVinculada = empresaVinculada; }

    public Tecnologicos getTecnologico() { return tecnologico; }
    public void setTecnologico(Tecnologicos tecnologico) { this.tecnologico = tecnologico; }

    public List<Avance> getAvances() { return avances; }
    public void setAvances(List<Avance> avances) { this.avances = avances; }

    public List<Media> getMedias() { return medias; }
    public void setMedias(List<Media> medias) { this.medias = medias; }
}
