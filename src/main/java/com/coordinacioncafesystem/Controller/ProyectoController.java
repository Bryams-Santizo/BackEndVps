package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Dto.ProyectoDTO;
// NECESARIO: Importar la entidad Media.
import com.coordinacioncafesystem.Entity.Media;
import com.coordinacioncafesystem.Entity.Proyectos;
import com.coordinacioncafesystem.Entity.Tecnologicos;
import com.coordinacioncafesystem.Entity.Usuario;
import com.coordinacioncafesystem.Repository.TecnologicoRepository;
import com.coordinacioncafesystem.Service.ProyectosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "${frontend.origin}")
public class ProyectoController {

    private final ProyectosService service;
    private final TecnologicoRepository tecnologicosRepository;

    public ProyectoController(ProyectosService service,
                              TecnologicoRepository tecnologicosRepository) {
        this.service = service;
        this.tecnologicosRepository = tecnologicosRepository;
    }


    // ======= CREAR PROYECTO =======
    @PostMapping
    public ResponseEntity<Proyectos> crear(
            @RequestBody Proyectos p,
            @AuthenticationPrincipal Usuario usuario) {

        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado.");
        }

        String rol = usuario.getRol() != null ? usuario.getRol().getNombre() : null;

        if ("COORDINADOR".equalsIgnoreCase(rol)) {
            // 👉 coordinador: se fuerza siempre a su Tecnológico
            Tecnologicos tec = usuario.getTecnologico();
            if (tec == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El usuario coordinador no tiene Tecnológico asignado.");
            }
            p.setTecnologico(tec);

        } else if ("ADMIN".equalsIgnoreCase(rol)) {
            // 👉 admin: el frontend debe mandar el tecnologico.id
            if (p.getTecnologico() == null || p.getTecnologico().getId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Admin: debes especificar el Tecnológico del proyecto.");
            }
            Tecnologicos tec = tecnologicosRepository.findById(p.getTecnologico().getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Tecnológico no encontrado."));
            p.setTecnologico(tec);

        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Rol sin permisos para crear proyectos.");
        }

        Proyectos creado = service.crear(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // ======= LISTAR PROYECTOS =======
    @GetMapping
    public List<Proyectos> listar(@AuthenticationPrincipal Usuario usuario) {

        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado.");
        }

        String rol = usuario.getRol() != null ? usuario.getRol().getNombre() : null;

        if ("ADMIN".equalsIgnoreCase(rol)) {
            // Admin ve todos
            return service.listarTodos();
        }

        if ("COORDINADOR".equalsIgnoreCase(rol)) {
            Tecnologicos tec = usuario.getTecnologico();
            if (tec == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El coordinador no tiene Tecnológico asignado.");
            }
            return service.listarPorTecnologico(tec.getId());
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Rol sin permisos para listar proyectos.");
    }

    // ======= GET BY ID (con validación) =======
    @GetMapping("/{id}")
    public Proyectos getById(@PathVariable Long id,
                             @AuthenticationPrincipal Usuario usuario) {

        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado.");
        }

        Proyectos p = service.getById(id);

        String rol = usuario.getRol() != null ? usuario.getRol().getNombre() : null;

        if ("ADMIN".equalsIgnoreCase(rol)) {
            return p; // admin puede ver cualquiera
        }

        if ("COORDINADOR".equalsIgnoreCase(rol)) {
            Tecnologicos tec = usuario.getTecnologico();
            if (tec == null || p.getTecnologico() == null ||
                    !p.getTecnologico().getId().equals(tec.getId())) {

                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "No puedes ver proyectos de otro Tecnológico.");
            }
            return p;
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Rol sin permisos.");
    }

    // ======= UPDATE =======
    @PutMapping("/{id}")
    public Proyectos actualizar(@PathVariable Long id,
                                @RequestBody Proyectos p,
                                @AuthenticationPrincipal Usuario usuario) {

        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado.");
        }

        Proyectos existente = service.getById(id);
        String rol = usuario.getRol() != null ? usuario.getRol().getNombre() : null;

        if ("ADMIN".equalsIgnoreCase(rol)) {
            // Admin puede actualizar, mantenemos el mismo Tecnológico
            p.setId(id);
            p.setTecnologico(existente.getTecnologico());
            return service.actualizar(id, p);
        }

        if ("COORDINADOR".equalsIgnoreCase(rol)) {
            Tecnologicos tec = usuario.getTecnologico();
            if (tec == null || existente.getTecnologico() == null ||
                    !existente.getTecnologico().getId().equals(tec.getId())) {

                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "No puedes editar proyectos de otro Tecnológico.");
            }
            p.setId(id);
            p.setTecnologico(existente.getTecnologico());
            return service.actualizar(id, p);
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Rol sin permisos.");
    }

    // ======= DELETE =======
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id,
                                         @AuthenticationPrincipal Usuario usuario) {

        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado.");
        }

        Proyectos existente = service.getById(id);
        String rol = usuario.getRol() != null ? usuario.getRol().getNombre() : null;

        if ("ADMIN".equalsIgnoreCase(rol)) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }

        if ("COORDINADOR".equalsIgnoreCase(rol)) {
            Tecnologicos tec = usuario.getTecnologico();
            if (tec == null || existente.getTecnologico() == null ||
                    !existente.getTecnologico().getId().equals(tec.getId())) {

                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "No puedes borrar proyectos de otro Tecnológico.");
            }
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Rol sin permisos.");
    }

    // =========================================================================
    // ======= FUNCIÓN AUXILIAR: BUSCAR MEDIA POR TIPO (CORREGIDA) =======
    // =========================================================================

    /**
     * Busca la URL de un archivo dentro de la colección 'medias' de un proyecto
     * basado en el fileType (rol) asignado.
     * @param p El proyecto.
     * @param tipoBuscado El valor del campo fileType en la entidad Media (ej: "IMAGEN").
     * @return La URL/Ruta del archivo o null.
     */
    private String buscarUrlPorTipo(Proyectos p, String tipoBuscado) {
        if (p.getMedias() == null) {
            return null;
        }

        // 🚩 CORRECCIÓN: Usamos media.getFileType() para el filtro
        return p.getMedias().stream()
                .filter(media -> media.getFileType() != null && media.getFileType().equalsIgnoreCase(tipoBuscado))
                .findFirst()
                .map(Media::getUrl) // Usamos Media.getUrl() para la ruta
                .orElse(null);
    }

    // =========================================================================
    // ======= FUNCIÓN DE MAPEO (Conversión Entidad -> DTO) =======
    // =========================================================================

    /**
     * Mapea una entidad Proyectos a la DTO ProyectoDTO.
     */
    private ProyectoDTO mapearAProyectoDTO(Proyectos p) {
        String nombreTecnologico = p.getTecnologico() != null ? p.getTecnologico().getNombre() : "N/A";

        // Solución al error: Buscamos las URLs en la colección 'medias'
        String imagenUrl = buscarUrlPorTipo(p, "IMAGEN_PRINCIPAL");
        String documentoUrl = buscarUrlPorTipo(p, "DOCUMENTO_EVIDENCIA");

        return new ProyectoDTO(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getEstadoProyecto(),
                p.getEmpresaVinculada(),
                nombreTecnologico,
                p.getActividad(),
                imagenUrl,
                documentoUrl
        );
    }

    // =========================================================================
    // ======= ENDPOINT PÚBLICO: LISTAR ÚLTIMOS TRES PROYECTOS =======
    // =========================================================================

    /**
     * Endpoint público para listar los 3 proyectos más recientes.
     * URL final: /api/proyectos/latest
     */
    @GetMapping("/latest")
    public List<ProyectoDTO> listarUltimosTresProyectos() {
        List<Proyectos> entities = service.listarUltimosTres();

        return entities.stream()
                .map(this::mapearAProyectoDTO)
                .collect(Collectors.toList());
    }

}