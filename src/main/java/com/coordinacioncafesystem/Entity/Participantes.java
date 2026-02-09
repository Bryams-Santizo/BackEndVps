package com.coordinacioncafesystem.Entity;


import com.coordinacioncafesystem.enums.TipoParticipante;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "participantes")
public class Participantes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // Aquí irá el nombre del Tec, Empresa o Gob.

    @Enumerated(EnumType.STRING)
    private TipoParticipante tipo; // TECNOLOGICO, EMPRESA, GOBIERNO

    // 🚩 RELACIONES: Aquí es donde "jalas" la información
    @ManyToOne
    @JoinColumn(name = "tecnologico_id")
    private Tecnologicos tecnologico;

    @ManyToOne
    @JoinColumn(name = "empresa_id") // Asumiendo que crearás o tienes esta tabla
    private Empresas empresa;

    @ManyToOne
    @JoinColumn(name = "gobierno_id") // Asumiendo que crearás o tienes esta tabla
    private Gobierno gobierno;

    // Estos campos pueden ser propios del "enlace" o venir de la tabla original
    private String correo;
    private String telefono;
    private String estado;


    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoParticipante getTipo() {
        return tipo;
    }

    public void setTipo(TipoParticipante tipo) {
        this.tipo = tipo;
    }

    public Tecnologicos getTecnologico() {
        return tecnologico;
    }

    public void setTecnologico(Tecnologicos tecnologico) {
        this.tecnologico = tecnologico;
    }

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }

    public Gobierno getGobierno() {
        return gobierno;
    }

    public void setGobierno(Gobierno gobierno) {
        this.gobierno = gobierno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
