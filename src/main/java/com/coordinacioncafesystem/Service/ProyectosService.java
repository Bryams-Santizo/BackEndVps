package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Proyectos;

import java.util.List;

public interface ProyectosService {

    Proyectos crear(Proyectos p);

    Proyectos actualizar(Long id, Proyectos p);

    Proyectos getById(Long id);

    List<Proyectos> listarTodos();

    List<Proyectos> listarPorTecnologico(Long tecnologicoId);

    List<Proyectos> listarUltimosTres();

    void eliminar(Long id);
}
