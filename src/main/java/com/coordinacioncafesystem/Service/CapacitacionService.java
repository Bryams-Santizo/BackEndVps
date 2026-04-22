package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Capacitacion;
import com.coordinacioncafesystem.Repository.CapacitacionRepository;
import com.coordinacioncafesystem.Repository.InscripcionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CapacitacionService {

    private final CapacitacionRepository capacitacionRepo;
    private final InscripcionRepository inscripcionRepo;

    public CapacitacionService(CapacitacionRepository capacitacionRepo, InscripcionRepository inscripcionRepo) {
        this.capacitacionRepo = capacitacionRepo;
        this.inscripcionRepo = inscripcionRepo;
    }

    public List<Capacitacion> listarPublicas() {
        // Ajusta si filtras por activo
        return capacitacionRepo.findAll();
    }

    public Capacitacion guardar(Capacitacion c) {
        return capacitacionRepo.save(c);
    }

    public Capacitacion buscarPorId(Long id) {
        return capacitacionRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Capacitación no encontrada con id: " + id));
    }

    // ✅ Eliminar “bien”: primero inscripciones, luego capacitación
    @Transactional
    public void eliminarCascade(Long capacitacionId) {
        // Borra hijas
        inscripcionRepo.deleteByCapacitacionId(capacitacionId);

        // Borra padre
        if (!capacitacionRepo.existsById(capacitacionId)) {
            throw new RuntimeException("Capacitación no encontrada con id: " + capacitacionId);
        }
        capacitacionRepo.deleteById(capacitacionId);
    }
}
