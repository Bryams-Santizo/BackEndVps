package com.coordinacioncafesystem.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "avances")
public class Avance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String descripcion;

    private LocalDateTime fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyectos proyecto;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public Proyectos getProyecto() { return proyecto; }
    public void setProyecto(Proyectos proyecto) { this.proyecto = proyecto; }

    public void setTitulo(String titulo) {
    }

    public void setAutor(Usuario u) {
    }
}
