package com.coordinacioncafesystem.Controller;


import com.coordinacioncafesystem.Repository.UsuarioRepository;
import com.coordinacioncafesystem.Entity.GaleriaEvidencia;
import com.coordinacioncafesystem.Entity.Usuario;
import com.coordinacioncafesystem.Service.IGaleriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/galeria")
@CrossOrigin(origins = "${frontend.origin}")
public class GaleriaController {

    @Autowired
    private IGaleriaService galeriaService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> subir(
            @RequestPart("evidencia") String evidenciaJson,
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal Usuario usuarioLogueado) {
        try {
            // Si el token no se envió bien, usuarioLogueado será null
            if (usuarioLogueado == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se encontró sesión activa");
            }

            ObjectMapper mapper = new ObjectMapper();
            GaleriaEvidencia evidencia = mapper.readValue(evidenciaJson, GaleriaEvidencia.class);

            // Usar findByCorreo o el método que tengas para buscar por el "sub" del token
            Usuario usuarioReal = usuarioRepository.findByCorreo(usuarioLogueado.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la base de datos"));

            if (usuarioReal.getTecnologico() != null) {
                evidencia.setTecnologico(usuarioReal.getTecnologico());
            }

            return ResponseEntity.ok(galeriaService.guardarEvidencia(file, evidencia));
        } catch (Exception e) {
            e.printStackTrace(); // Esto te dirá la línea exacta del error en la consola negra
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/tecnologico/{id}")
    public ResponseEntity<List<GaleriaEvidencia>> listarPorTecnologico(@PathVariable Long id) {
        // Si quieres que este método sea público para ver las fotos sin loguearse:
        // No pidas @AuthenticationPrincipal aquí.
        return ResponseEntity.ok(galeriaService.listarPorTecnologico(id));
    }

    @GetMapping
    public ResponseEntity<List<GaleriaEvidencia>> listar(
            @AuthenticationPrincipal Usuario usuario) {

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String rol = usuario.getRol().getNombre();

        if ("ADMIN".equalsIgnoreCase(rol)) {
            return ResponseEntity.ok(galeriaService.listarTodas());
        }

        if ("COORDINADOR".equalsIgnoreCase(rol)) {
            Long tecId = usuario.getTecnologico().getId();
            return ResponseEntity.ok(galeriaService.listarPorTecnologico(tecId));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            galeriaService.eliminarEvidencia(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
