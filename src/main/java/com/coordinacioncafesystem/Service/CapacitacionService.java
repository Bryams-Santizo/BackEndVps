package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Capacitacion;
import com.coordinacioncafesystem.Repository.CapacitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CapacitacionService {
    @Autowired
    private CapacitacionRepository repository;

    // Lógica para el Administrador: Ver todo
    public List<Capacitacion> listarTodas() {
        return repository.findAll();
    }

    // Lógica para el Público: Ver solo cursos activos
    public List<Capacitacion> listarPublicas() {
        return repository.findByActivoTrue();
    }

    public Capacitacion buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la capacitación con ID: " + id));
    }

    public Capacitacion guardar(Capacitacion capacitacion) {
        // Aquí podrías agregar validaciones, por ejemplo:
        if (capacitacion.getNombre() == null || capacitacion.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del curso es obligatorio");
        }
        return repository.save(capacitacion);
    }

    public void eliminar(Long id) {
        // Opcional: Verificar si existe antes de borrar para evitar excepciones feas
        if (!repository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: No existe el ID " + id);
        }
        repository.deleteById(id);
    }
}
