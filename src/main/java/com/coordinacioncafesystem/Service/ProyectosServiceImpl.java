package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Proyectos;
import com.coordinacioncafesystem.Repository.ProyectosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProyectosServiceImpl implements ProyectosService {

    private final ProyectosRepository repo;

    public ProyectosServiceImpl(ProyectosRepository repo) {
        this.repo = repo;
    }

    @Override
    public Proyectos crear(Proyectos p) {
        return repo.save(p);
    }

    @Override
    public Proyectos actualizar(Long id, Proyectos p) {
        Proyectos existente = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        existente.setNombre(p.getNombre());
        existente.setDescripcion(p.getDescripcion());
        existente.setActividad(p.getActividad());
        existente.setNecesidad(p.getNecesidad());
        existente.setEstadoProyecto(p.getEstadoProyecto());
        existente.setFechaInicio(p.getFechaInicio());
        existente.setFechaFin(p.getFechaFin());
        existente.setEmpresaVinculada(p.getEmpresaVinculada());
        // tecnológico no se cambia aquí por seguridad

        return repo.save(existente);
    }

    @Override
    public Proyectos getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    @Override
    public List<Proyectos> listarTodos() {
        return repo.findAll();
    }

    @Override
    public List<Proyectos> listarPorTecnologico(Long tecnologicoId) {
        return repo.findByTecnologicoId(tecnologicoId);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true) // La lectura es de solo lectura
    public List<Proyectos> listarUltimosTres() {
        // 🚩 LLAMADA AL MÉTODO DEFINIDO EN EL REPOSITORIO
        return repo.findTop5ByOrderByFechaInicioDesc();
    }
}
