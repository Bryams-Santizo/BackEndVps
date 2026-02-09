package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Entity.ImpulsoTecnologico;
import com.coordinacioncafesystem.Service.ImpulsoTecnologicoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/transferencias")
@CrossOrigin(origins = "${frontend.origin}")
public class ImpulsoTecnologicoController {

    @Autowired
    private ImpulsoTecnologicoService service;

    // --- 1. OBTENER TODOS LOS REGISTROS ---
    @GetMapping
    public ResponseEntity<List<ImpulsoTecnologico>> listarTodos() {
        return ResponseEntity.ok(service.listarTodo());
    }

    // --- 2. OBTENER UNO POR ID ---
    @GetMapping("/{id}")
    public ResponseEntity<ImpulsoTecnologico> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // --- 3. CREAR NUEVO REGISTRO ---
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ImpulsoTecnologico> guardar(
            @RequestPart("datos") String datosJson,
            @RequestPart(value = "documentos", required = false) MultipartFile documentos,
            @RequestPart(value = "carta", required = false) MultipartFile carta) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ImpulsoTecnologico transferencia = mapper.readValue(datosJson, ImpulsoTecnologico.class);

        return ResponseEntity.ok(service.guardarConArchivos(transferencia, documentos, carta));
    }

    // --- 4. ACTUALIZAR REGISTRO EXISTENTE ---
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ImpulsoTecnologico> actualizar(
            @PathVariable Long id,
            @RequestPart("datos") String datosJson,
            @RequestPart(value = "documentos", required = false) MultipartFile documentos,
            @RequestPart(value = "carta", required = false) MultipartFile carta) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ImpulsoTecnologico datosActualizados = mapper.readValue(datosJson, ImpulsoTecnologico.class);

        // Aseguramos que el ID del objeto sea el mismo que el de la URL
        datosActualizados.setId(id);

        return ResponseEntity.ok(service.guardarConArchivos(datosActualizados, documentos, carta));
    }

    // --- 5. ELIMINAR REGISTRO ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}