package com.coordinacioncafesystem.Entity;


import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Actividades")

public class ActividadesdelCafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_actividad;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Date fecha_inicio;

    @Column(nullable = false)
    private String estado;


    @ManyToOne
    @JoinColumn(name = "id_tecnologico")
    private Tecnologicos tecnm;

    @OneToMany(mappedBy = "actividad")
    private List<Proyectos> proyectos;

}
