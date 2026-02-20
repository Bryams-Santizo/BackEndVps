package com.coordinacioncafesystem.Controller;


import com.coordinacioncafesystem.Dto.DatosAceptacionDTO;
import com.coordinacioncafesystem.Entity.Inscripcion;
import com.coordinacioncafesystem.Service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@CrossOrigin(origins = "http://localhost:4200") // O "*" para permitir todo
public class InscripcionController {

    @Autowired
    private InscripcionService service;

    @PostMapping
    public ResponseEntity<Inscripcion> solicitar(@RequestBody Inscripcion inscripcion) {
        return ResponseEntity.ok(service.solicitarInscripcion(inscripcion));
    }

    @GetMapping("/pendientes")
    public List<Inscripcion> listarPendientes() {
        return service.listarPendientes();
    }

    @PutMapping("/{id}/aceptar")
    public ResponseEntity<?> aceptar(@PathVariable Long id, @RequestBody DatosAceptacionDTO datos) {
        service.aceptarInscripcion(id, datos);
        return ResponseEntity.ok().build();
    }
}
