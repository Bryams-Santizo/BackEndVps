package com.coordinacioncafesystem.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscripciones")
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreAlumno;
    private String emailAlumno;
    private String telefonoAlumno;

    // Estado: PENDIENTE, ACEPTADO
    private String estado;

    private LocalDateTime fechaSolicitud;

    @ManyToOne
    @JoinColumn(name = "capacitacion_id")
    private Capacitacion capacitacion;

    @PrePersist
    public void prePersist() {
        this.fechaSolicitud = LocalDateTime.now();
        if(this.estado == null) this.estado = "PENDIENTE";
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreAlumno() { return nombreAlumno; }
    public void setNombreAlumno(String nombreAlumno) { this.nombreAlumno = nombreAlumno; }
    public String getEmailAlumno() { return emailAlumno; }
    public void setEmailAlumno(String emailAlumno) { this.emailAlumno = emailAlumno; }
    public String getTelefonoAlumno() { return telefonoAlumno; }
    public void setTelefonoAlumno(String telefonoAlumno) { this.telefonoAlumno = telefonoAlumno; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Capacitacion getCapacitacion() { return capacitacion; }
    public void setCapacitacion(Capacitacion capacitacion) { this.capacitacion = capacitacion; }
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
}
