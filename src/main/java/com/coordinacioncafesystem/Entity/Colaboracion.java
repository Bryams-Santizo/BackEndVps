package com.coordinacioncafesystem.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "colaboraciones")
public class Colaboracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String institucionSolicitante;
    private String estado;
    private String tipoColaboracion;

    @Column(columnDefinition = "TEXT")
    private String descripcionNecesidad;

    private Integer numeroEstudiantes;

    @Column(columnDefinition = "TEXT")
    private String perfilCompetencias;

    private String duracion;

    @Column(columnDefinition = "TEXT")
    private String beneficios;

    private String personaContacto;

    // Para archivos, usualmente guardamos la ruta o el nombre del archivo
    private String documentosAdjuntos;
    private String cartaIntencion;

    // Getters y Setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitucionSolicitante() {
        return institucionSolicitante;
    }

    public void setInstitucionSolicitante(String institucionSolicitante) {
        this.institucionSolicitante = institucionSolicitante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoColaboracion() {
        return tipoColaboracion;
    }

    public void setTipoColaboracion(String tipoColaboracion) {
        this.tipoColaboracion = tipoColaboracion;
    }

    public String getDescripcionNecesidad() {
        return descripcionNecesidad;
    }

    public void setDescripcionNecesidad(String descripcionNecesidad) {
        this.descripcionNecesidad = descripcionNecesidad;
    }

    public Integer getNumeroEstudiantes() {
        return numeroEstudiantes;
    }

    public void setNumeroEstudiantes(Integer numeroEstudiantes) {
        this.numeroEstudiantes = numeroEstudiantes;
    }

    public String getPerfilCompetencias() {
        return perfilCompetencias;
    }

    public void setPerfilCompetencias(String perfilCompetencias) {
        this.perfilCompetencias = perfilCompetencias;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public String getDocumentosAdjuntos() {
        return documentosAdjuntos;
    }

    public void setDocumentosAdjuntos(String documentosAdjuntos) {
        this.documentosAdjuntos = documentosAdjuntos;
    }

    public String getCartaIntencion() {
        return cartaIntencion;
    }

    public void setCartaIntencion(String cartaIntencion) {
        this.cartaIntencion = cartaIntencion;
    }
}
