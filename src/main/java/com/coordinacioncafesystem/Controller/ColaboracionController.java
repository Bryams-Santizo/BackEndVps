package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Entity.Colaboracion;
import com.coordinacioncafesystem.Service.ColaboracionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/colaboraciones")
@CrossOrigin(origins = "${frontend.origin}")
public class ColaboracionController {

    @Autowired
    private ColaboracionService service;

    @GetMapping
    public ResponseEntity<List<Colaboracion>> listar() {
        return ResponseEntity.ok(service.listarTodo());
    }

    @PostMapping
    public ResponseEntity<Colaboracion> guardar(
            @RequestPart("colaboracion") String colaboracionJson,
            @RequestPart(value = "fileDocumento", required = false) MultipartFile fileDoc,
            @RequestPart(value = "fileCarta", required = false) MultipartFile fileCarta
    ) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Colaboracion colaboracion = objectMapper.readValue(colaboracionJson, Colaboracion.class);

        Path root = Paths.get("uploads");
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }

        if (fileDoc != null && !fileDoc.isEmpty()) {
            // Limpiamos el nombre: quitamos espacios y caracteres raros
            String nombreOriginal = fileDoc.getOriginalFilename() != null ? fileDoc.getOriginalFilename() : "archivo";
            String nombreFinal = System.currentTimeMillis() + "_" + nombreOriginal.replaceAll("[^a-zA-Z0-9.]", "_");
            Files.copy(fileDoc.getInputStream(), root.resolve(nombreFinal));
            colaboracion.setDocumentosAdjuntos(nombreFinal);
        }

        if (fileCarta != null && !fileCarta.isEmpty()) {
            String nombreOriginal = fileCarta.getOriginalFilename() != null ? fileCarta.getOriginalFilename() : "carta";
            String nombreFinal = System.currentTimeMillis() + "_" + nombreOriginal.replaceAll("[^a-zA-Z0-9.]", "_");
            Files.copy(fileCarta.getInputStream(), root.resolve(nombreFinal));
            colaboracion.setCartaIntencion(nombreFinal);
        }

        return ResponseEntity.ok(service.guardar(colaboracion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colaboracion> actualizar(
            @PathVariable Long id,
            @RequestPart("colaboracion") String colaboracionJson,
            @RequestPart(value = "fileDocumento", required = false) MultipartFile fileDoc,
            @RequestPart(value = "fileCarta", required = false) MultipartFile fileCarta
    ) throws Exception {

        Colaboracion colaboracionExistente = service.obtenerPorId(id);

        ObjectMapper objectMapper = new ObjectMapper();
        Colaboracion detalles = objectMapper.readValue(colaboracionJson, Colaboracion.class);

        // 1. Actualizar datos de texto
        colaboracionExistente.setInstitucionSolicitante(detalles.getInstitucionSolicitante());
        colaboracionExistente.setTipoColaboracion(detalles.getTipoColaboracion());
        colaboracionExistente.setEstado(detalles.getEstado());
        colaboracionExistente.setDescripcionNecesidad(detalles.getDescripcionNecesidad());
        colaboracionExistente.setNumeroEstudiantes(detalles.getNumeroEstudiantes());
        colaboracionExistente.setPerfilCompetencias(detalles.getPerfilCompetencias());
        colaboracionExistente.setDuracion(detalles.getDuracion());
        colaboracionExistente.setBeneficios(detalles.getBeneficios());
        colaboracionExistente.setPersonaContacto(detalles.getPersonaContacto());

        Path root = Paths.get("uploads");
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }

        // 2. Si se subió un NUEVO documento, lo guardamos y actualizamos el nombre
        if (fileDoc != null && !fileDoc.isEmpty()) {
            String nombreOriginal = fileDoc.getOriginalFilename() != null ? fileDoc.getOriginalFilename() : "archivo";
            String nombreFinal = System.currentTimeMillis() + "_" + nombreOriginal.replaceAll("[^a-zA-Z0-9.]", "_");
            Files.copy(fileDoc.getInputStream(), root.resolve(nombreFinal));
            colaboracionExistente.setDocumentosAdjuntos(nombreFinal);
        }

        // 3. Si se subió una NUEVA carta, la guardamos y actualizamos el nombre
        if (fileCarta != null && !fileCarta.isEmpty()) {
            String nombreOriginal = fileCarta.getOriginalFilename() != null ? fileCarta.getOriginalFilename() : "carta";
            String nombreFinal = System.currentTimeMillis() + "_" + nombreOriginal.replaceAll("[^a-zA-Z0-9.]", "_");
            Files.copy(fileCarta.getInputStream(), root.resolve(nombreFinal));
            colaboracionExistente.setCartaIntencion(nombreFinal);
        }

        return ResponseEntity.ok(service.guardar(colaboracionExistente));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
