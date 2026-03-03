package com.coordinacioncafesystem.Service;


import com.coordinacioncafesystem.Entity.Colaboracion;

import java.util.List;

public interface ColaboracionService {
    Colaboracion guardar(Colaboracion colaboracion);
    List<Colaboracion> listarTodo();

    Colaboracion obtenerPorId(Long id);
    void eliminar(Long id);
}
