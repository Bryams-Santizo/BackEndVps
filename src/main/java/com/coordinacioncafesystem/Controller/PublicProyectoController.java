package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Dto.ProyectoDTO;
import com.coordinacioncafesystem.Entity.Media;
import com.coordinacioncafesystem.Entity.Proyectos;
import com.coordinacioncafesystem.Repository.MediaRepository;
import com.coordinacioncafesystem.Repository.ProyectosRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/proyectos")
public class PublicProyectoController {

    private final ProyectosRepository proyectosRepo;
    private final MediaRepository mediaRepo;

    public PublicProyectoController(ProyectosRepository proyectosRepo,
                                    MediaRepository mediaRepo) {
        this.proyectosRepo = proyectosRepo;
        this.mediaRepo = mediaRepo;
    }

    @GetMapping
    public List<ProyectoDTO> listarPublicos() {
        List<Proyectos> proyectos = proyectosRepo.findAll();

        return proyectos.stream().map(p -> {

            List<Media> medias = mediaRepo.findByProyecto_IdOrderByUploadedAtDesc(p.getId());

            // Imagen de portada
            Media portada = medias.stream()
                    .filter(m -> m.getFileType() != null && m.getFileType().startsWith("image/"))
                    .findFirst()
                    .orElse(null);

            // Documento principal
            Media docPrincipal = medias.stream()
                    .filter(m -> m.getFileType() == null || !m.getFileType().startsWith("image/"))
                    .findFirst()
                    .orElse(null);

            String imagenUrl = (portada != null)
                    ? "/api/media/view/" + portada.getId()
                    : null;

            String documentoUrl = (docPrincipal != null)
                    ? "/api/media/download/" + docPrincipal.getId()
                    : null;

            String nombreTec = p.getTecnologico() != null
                    ? p.getTecnologico().getNombre()
                    : "";

            return new ProyectoDTO(
                    p.getId(),
                    p.getNombre(),
                    p.getDescripcion(),
                    p.getEstadoProyecto(),
                    p.getEmpresaVinculada(),
                    nombreTec,
                    p.getActividad(),
                    imagenUrl,
                    documentoUrl
            );
        }).toList();
    }


}
