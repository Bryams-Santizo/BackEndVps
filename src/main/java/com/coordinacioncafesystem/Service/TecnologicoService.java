package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Tecnologicos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
public interface TecnologicoService {
    Tecnologicos crear(Tecnologicos t);
    Tecnologicos actualizar(Long id, Tecnologicos t);
    Tecnologicos getById(Long id);
    Page<Tecnologicos> listar(Pageable p);
    List<Tecnologicos> listarPorEstado(String estado);
}
