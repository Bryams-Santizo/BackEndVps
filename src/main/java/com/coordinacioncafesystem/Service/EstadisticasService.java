package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Repository.EstadisticasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadisticasService {

    private final EstadisticasRepository repository;

    public EstadisticasService(EstadisticasRepository repository) {
        this.repository = repository;
    }

    public List<Object[]> proyectosPorMes() {
        return repository.proyectosPorMes();
    }

    public List<Object[]> participantesPorMes() {
        return repository.participantesPorMes();
    }

    public List<Object[]> proyectosPorEstado() {
        return repository.proyectosPorEstado();
    }

    public List<Object[]> tiposProyecto() {
        return repository.tiposProyecto();
    }

    public List<Object[]> estadoProyectos() {
        return repository.estadoProyectos();
    }


    //rendimiento
    public long getTotalProyectos() {
        return repository.countTotalProyectos();
    }

    public long getTotalParticipantes() {
        return repository.countTotalParticipantes();
    }
}
