package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Tecnologicos;
import com.coordinacioncafesystem.Repository.TecnologicoRepository;
import com.coordinacioncafesystem.Service.TecnologicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class TecnologicoServiceImpl implements TecnologicoService {
    private final TecnologicoRepository repo;
    public TecnologicoServiceImpl(TecnologicoRepository repo){ this.repo = repo; }
    @Override public Tecnologicos crear(Tecnologicos t){ return repo.save(t); }
    @Override public Tecnologicos actualizar(Long id, Tecnologicos t){ Tecnologicos ex = repo.findById(id).orElseThrow(); ex.setNombre(t.getNombre()); return repo.save(ex); }
    @Override public Tecnologicos getById(Long id){ return repo.findById(id).orElseThrow(); }
    @Override public Page<Tecnologicos> listar(Pageable p){ return repo.findAll(p); }
    @Override public List<Tecnologicos> listarPorEstado(String estado){ return repo.findByEstado(estado); }
}

