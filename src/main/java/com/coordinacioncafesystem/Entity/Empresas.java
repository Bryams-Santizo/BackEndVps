package com.coordinacioncafesystem.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "empresas")
public class Empresas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String tipo; // transformadora, exportadora, comercializadora, etc.
    private String contacto;
    private String correo;
    private String telefono;
    private String direccion;

    // Relación con organización (si la empresa está asociada a un tec o convenio)
    @ManyToOne
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    public Empresas() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Organizacion getOrganizacion() { return organizacion; }
    public void setOrganizacion(Organizacion organizacion) { this.organizacion = organizacion; }
}
