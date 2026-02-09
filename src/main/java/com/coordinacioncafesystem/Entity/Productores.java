package com.coordinacioncafesystem.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "productores")
public class Productores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String ubicacion;
    private Double hectareas;
    private String tipoCafe;
    private String telefono;
    private String correo;

    // Cada productor puede estar vinculado a un tecnológico (por convenio)
    @ManyToOne
    @JoinColumn(name = "tecnologico_id")
    private Organizacion tecnologico;

    public Productores() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public Double getHectareas() { return hectareas; }
    public void setHectareas(Double hectareas) { this.hectareas = hectareas; }

    public String getTipoCafe() { return tipoCafe; }
    public void setTipoCafe(String tipoCafe) { this.tipoCafe = tipoCafe; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Organizacion getTecnologico() { return tecnologico; }
    public void setTecnologico(Organizacion tecnologico) { this.tecnologico = tecnologico; }
}

