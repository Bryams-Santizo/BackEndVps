package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Dto.ParticipanteDTO;
import com.coordinacioncafesystem.Dto.RegistroParticipanteDTO;
import com.coordinacioncafesystem.Entity.Participantes;
import com.coordinacioncafesystem.Service.ParticipanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participantes")
@CrossOrigin(origins = "${frontend.origin}")

public class ParticipanteController {

    @Autowired
    private ParticipanteService service;

    @GetMapping
    public List<Participantes> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Participantes> crear(@RequestBody Participantes participante) {
        return ResponseEntity.ok(service.guardar(participante));
    }

    @GetMapping("/actividad-completa/{id}")
    public ResponseEntity<?> verActividad(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerTodaLaActividad(id));
    }

    @PostMapping("/registro-completo")
    public ResponseEntity<?> registrar(@RequestBody RegistroParticipanteDTO dto) {
        try {
            return ResponseEntity.ok(service.registrarCompleto(dto));
        } catch (Exception e) {
            // Aquí es donde te sale el mensaje del password
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody RegistroParticipanteDTO dto) {
        try {
            return ResponseEntity.ok(service.actualizar(id, dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar: " + e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.eliminarCompleto(id);
            // Retornamos un mensaje de éxito para que el Front sepa qué pasó
            return ResponseEntity.ok().body("Participante y todas sus dependencias eliminados correctamente.");
        } catch (Exception e) {
            // Si algo falla (ej. el ID no existe), enviamos un 400 o 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar: " + e.getMessage());
        }
    }



}
