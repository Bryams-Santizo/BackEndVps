package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Entity.Capacitacion;
import com.coordinacioncafesystem.Service.CapacitacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/capacitaciones")
@CrossOrigin(origins = "${frontend.origin}")
public class CapacitacionController {

    private final CapacitacionService service;

    public CapacitacionController(CapacitacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Capacitacion> obtenerCursos() {
        return service.listarPublicas();
    }

    @PostMapping
    public ResponseEntity<Capacitacion> crear(@RequestBody Capacitacion capacitacion) {
        Capacitacion nueva = service.guardar(capacitacion);
        return ResponseEntity.ok(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Capacitacion> actualizar(@PathVariable Long id, @RequestBody Capacitacion detalles) {
        Capacitacion curso = service.buscarPorId(id);

        curso.setNombre(detalles.getNombre());
        curso.setTipo(detalles.getTipo());
        curso.setDuracion(detalles.getDuracion());
        curso.setRequisitos(detalles.getRequisitos());
        curso.setCompetencias(detalles.getCompetencias());
        curso.setPublicoObjetivo(detalles.getPublicoObjetivo());
        curso.setContenido(detalles.getContenido());
        curso.setMateriales(detalles.getMateriales());
        curso.setEmisor(detalles.getEmisor());
        curso.setCriteriosevaluacion(detalles.getCriteriosevaluacion());
        curso.setDisponibilidad(detalles.getDisponibilidad());
        curso.setCosto(detalles.getCosto());
        curso.setInstructores(detalles.getInstructores());
        curso.setActivo(detalles.isActivo());

        return ResponseEntity.ok(service.guardar(curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarCascade(id); // ✅ primero borra inscripciones, luego capacitacion
        return ResponseEntity.noContent().build();
    }
}
