package com.coordinacioncafesystem.Entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "organizaciones")
public class Organizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // Tipo: TECNOLOGICO, GOBIERNO, EMPRESA, PRODUCTOR, SOCIO, COOPERATIVA
    private String tipo;

    private String estado;
    private String municipio;
    private String contacto;
    private String correoContacto;
    private String telefono;

    // Organización padre (por ejemplo, el Tec de Comalapa)
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Organizacion parent;

    @OneToMany(mappedBy = "parent")
    private Set<Organizacion> hijos = new HashSet<>();

    public Organizacion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public String getCorreoContacto() { return correoContacto; }
    public void setCorreoContacto(String correoContacto) { this.correoContacto = correoContacto; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Organizacion getParent() { return parent; }
    public void setParent(Organizacion parent) { this.parent = parent; }

    public Set<Organizacion> getHijos() { return hijos; }
    public void setHijos(Set<Organizacion> hijos) { this.hijos = hijos; }
}
