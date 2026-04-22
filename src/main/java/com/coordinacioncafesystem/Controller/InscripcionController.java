package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Dto.DatosAceptacionDTO;
import com.coordinacioncafesystem.Entity.Inscripcion;
import com.coordinacioncafesystem.Service.InscripcionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Si ya usas CORS global en SecurityConfig, puedes quitar esto.
// Si lo dejas, mejor usa el property:
@CrossOrigin(origins = "${frontend.origin}")
@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService service;

    public InscripcionController(InscripcionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Inscripcion> solicitar(@RequestBody Inscripcion inscripcion) {
        return ResponseEntity.ok(service.solicitarInscripcion(inscripcion));
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<Inscripcion>> listarPendientes() {
        return ResponseEntity.ok(service.listarPendientes());
    }

    @PutMapping("/{id}/aceptar")
    public ResponseEntity<Void> aceptar(@PathVariable Long id, @RequestBody DatosAceptacionDTO datos) {
        service.aceptarInscripcion(id, datos);
        return ResponseEntity.ok().build();
    }

    // ✅ borrar una inscripción
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInscripcion(@PathVariable Long id) {
        service.eliminarInscripcion(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ borrar todas las inscripciones de una capacitación
    @DeleteMapping("/por-capacitacion/{capacitacionId}")
    public ResponseEntity<Long> eliminarPorCapacitacion(@PathVariable Long capacitacionId) {
        long borradas = service.eliminarPorCapacitacion(capacitacionId);
        return ResponseEntity.ok(borradas);
    }
}
