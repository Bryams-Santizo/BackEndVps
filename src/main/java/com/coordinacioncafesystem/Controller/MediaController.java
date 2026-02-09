package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Entity.Media;
import com.coordinacioncafesystem.Service.MediaService;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/media")
@CrossOrigin(origins = "${frontend.origin}")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService){
        this.mediaService = mediaService;
    }

    // SUBIR
    @PostMapping("/upload")
    public ResponseEntity<Media> upload(@RequestParam("file") MultipartFile file,
                                        @RequestParam("proyectoId") Long proyectoId,
                                        @RequestParam("uploaderId") Long uploaderId) throws IOException {
        Media m = mediaService.store(file, proyectoId, uploaderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(m);
    }

    // LISTAR POR PROYECTO
    @GetMapping("/por-proyecto/{proyectoId}")
    public List<Media> listarPorProyecto(@PathVariable Long proyectoId) {
        return mediaService.listByProyecto(proyectoId);
    }

    // DESCARGAR
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws IOException {

        // Obtenemos la info del media (tipo, nombre, etc.)
        Media media = mediaService.getMedia(id);
        Resource res = mediaService.loadAsResource(id);

        if (res == null || !res.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Determinar el Content-Type (igual que en /view)
        MediaType mediaType;
        try {
            if (media.getFileType() != null) {
                mediaType = MediaType.parseMediaType(media.getFileType());
            } else {
                mediaType = MediaType.APPLICATION_OCTET_STREAM;
            }
        } catch (Exception e) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }

        // Nombre del archivo a descargar
        String filename = media.getFileName() != null
                ? media.getFileName()
                : res.getFilename();

        return ResponseEntity.ok()
                .contentType(mediaType)  // 👈 ahora el navegador sabe qué tipo de archivo es
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .body(res);

    }


    // VER (inline)
    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> view(@PathVariable Long id) {
        try {
            Media media = mediaService.getMedia(id);
            Resource res = mediaService.loadAsResource(id);

            // Si el Service no lanzó excepción pero el recurso no es legible
            if (!res.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(media.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + media.getFileName() + "\"")
                    .body(res);
        } catch (Exception e) {
            // En lugar de un pantallazo de error, enviamos un 404 limpio
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ACTUALIZAR DOCUMENTO
    @PutMapping("/{id}")
    public ResponseEntity<Media> actualizar(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {

        Media m = mediaService.update(id, file);
        return ResponseEntity.ok(m);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) throws IOException {
        mediaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
