package com.coordinacioncafesystem.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tecnologicos")
public class Tecnologicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;      // "TecNM Campus Frontera Comalapa"
    private String estado;      // "Chiapas"
    private String ciudad;
    private String telefono;
    private String correoContacto;

    // podrías opcionalmente tener un coordinador principal:
    // @OneToOne
    // @JoinColumn(name = "coordinador_id")
    // private Usuario coordinador;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreoContacto() { return correoContacto; }
    public void setCorreoContacto(String correoContacto) { this.correoContacto = correoContacto; }
}
