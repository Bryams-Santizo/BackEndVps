package com.coordinacioncafesystem.Entity;


import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.util.List;

@Entity
@Table(name = "gobierno")
public class Gobierno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_institucion;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String contacto;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    private String direccion;







}
