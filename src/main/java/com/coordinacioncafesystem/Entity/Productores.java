package com.coordinacioncafesystem.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "productores")
public class Productores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreProductor;
    private String nombreFinca;
    private String ubicacion;
    private Double hectareas;
    private Integer altitudPromedio;
    private Double volumenProduccion;
    private String variedades;


    @OneToMany(mappedBy = "productor", cascade = CascadeType.ALL)
    private List<ResultadoEvaluacion> evaluaciones;

    //Getters and Setters//


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProductor() {
        return nombreProductor;
    }

    public void setNombreProductor(String nombreProductor) {
        this.nombreProductor = nombreProductor;
    }

    public String getNombreFinca() {
        return nombreFinca;
    }

    public void setNombreFinca(String nombreFinca) {
        this.nombreFinca = nombreFinca;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getHectareas() {
        return hectareas;
    }

    public void setHectareas(Double hectareas) {
        this.hectareas = hectareas;
    }

    public Integer getAltitudPromedio() {
        return altitudPromedio;
    }

    public void setAltitudPromedio(Integer altitudPromedio) {
        this.altitudPromedio = altitudPromedio;
    }

    public Double getVolumenProduccion() {
        return volumenProduccion;
    }

    public void setVolumenProduccion(Double volumenProduccion) {
        this.volumenProduccion = volumenProduccion;
    }

    public String getVariedades() {
        return variedades;
    }

    public void setVariedades(String variedades) {
        this.variedades = variedades;
    }

    public List<ResultadoEvaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<ResultadoEvaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }
}
