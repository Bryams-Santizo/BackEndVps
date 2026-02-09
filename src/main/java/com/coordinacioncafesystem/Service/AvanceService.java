package com.coordinacioncafesystem.Service;


import com.coordinacioncafesystem.Dto.AvanceDTO;
import com.coordinacioncafesystem.Entity.Avance;

import java.util.List;
public interface AvanceService {
    Avance crear(Long proyectoId, AvanceDTO dto, Long autorId);
    List<Avance> listarPorProyecto(Long proyectoId);
}
