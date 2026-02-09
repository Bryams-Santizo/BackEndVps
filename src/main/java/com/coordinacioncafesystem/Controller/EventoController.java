package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Entity.Eventos;
import com.coordinacioncafesystem.Entity.Tecnologicos;
import com.coordinacioncafesystem.Entity.Usuario;
import com.coordinacioncafesystem.Service.EventoService;
import com.coordinacioncafesystem.Repository.TecnologicoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "${frontend.origin}")
public class EventoController {

    private final EventoService eventoService;
    private final TecnologicoRepository tecnologicoRepository;

    public EventoController(EventoService eventoService, TecnologicoRepository tecnologicoRepository) {
        this.eventoService = eventoService;
        this.tecnologicoRepository = tecnologicoRepository;
    }

    // ======= LISTAR EVENTOS (Filtrado por Rol y Tecnológico) =======
    @GetMapping
    public List<Eventos> listarTodos(@AuthenticationPrincipal Usuario usuario) {
        validarUsuario(usuario);
        String rol = usuario.getRol().getNombre();

        if ("ADMIN".equalsIgnoreCase(rol)) {
            return eventoService.listarTodos();
        }

        if ("COORDINADOR".equalsIgnoreCase(rol)) {
            Long tecId = usuario.getTecnologico().getId();
            return eventoService.listarPorTecnologico(tecId);
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para ver eventos.");
    }

    // ======= CREAR EVENTO =======
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Eventos> crear(
            @RequestPart("evento") String eventoJson,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen,
            @RequestPart(value = "documento", required = false) MultipartFile documento,
            @AuthenticationPrincipal Usuario usuario) {

        validarUsuario(usuario);

        try {
            ObjectMapper mapper = new ObjectMapper();
            Eventos evento = mapper.readValue(eventoJson, Eventos.class);
            String rol = usuario.getRol().getNombre();

            if ("COORDINADOR".equalsIgnoreCase(rol)) {
                // Forzar el tecnológico del coordinador
                evento.setTecnologico(usuario.getTecnologico());
            } else if ("ADMIN".equalsIgnoreCase(rol)) {
                // El admin debe especificar el Tecnológico en el JSON
                if (evento.getTecnologico() == null || evento.getTecnologico().getId() == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin debe asignar un Tecnológico.");
                }
                Tecnologicos tec = tecnologicoRepository.findById(evento.getTecnologico().getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tecnológico no encontrado."));
                evento.setTecnologico(tec);
            }

            Eventos guardado = eventoService.crearConArchivos(evento, imagen, documento);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al procesar el evento: " + e.getMessage());
        }
    }

    // ======= OBTENER POR ID =======
    @GetMapping("/{id}")
    public ResponseEntity<Eventos> obtenerPorId(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        validarUsuario(usuario);
        Eventos evento = eventoService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado."));

        validarPropiedad(evento, usuario);
        return ResponseEntity.ok(evento);
    }

    // ======= ACTUALIZAR =======
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Eventos> actualizar(
            @PathVariable Long id,
            @RequestPart("evento") Eventos evento,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen,
            @RequestPart(value = "documento", required = false) MultipartFile documento,
            @AuthenticationPrincipal Usuario usuario
    ) {
        validarUsuario(usuario);

        Eventos existente = eventoService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        validarPropiedad(existente, usuario);

        // Mantener tecnológico original
        evento.setTecnologico(existente.getTecnologico());

        Eventos actualizado = eventoService.actualizarConArchivos(id, evento, imagen, documento);
        return ResponseEntity.ok(actualizado);
    }




    // ======= ELIMINAR =======
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        validarUsuario(usuario);
        Eventos existente = eventoService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        validarPropiedad(existente, usuario);

        eventoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ======= PUBLICO: ÚLTIMOS TRES =======
    @GetMapping("/latest")
    public List<Eventos> listarUltimosTres() {
        return eventoService.listarUltimosTres();
    }

    // --- MÉTODOS PRIVADOS DE APOYO ---

    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado.");
        }
    }

    private void validarPropiedad(Eventos evento, Usuario usuario) {
        String rol = usuario.getRol().getNombre();
        if ("COORDINADOR".equalsIgnoreCase(rol)) {
            if (usuario.getTecnologico() == null ||
                    !evento.getTecnologico().getId().equals(usuario.getTecnologico().getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso sobre este evento.");
            }
        }
    }
}