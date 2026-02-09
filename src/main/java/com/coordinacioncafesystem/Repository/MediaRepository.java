package com.coordinacioncafesystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coordinacioncafesystem.Entity.Media;
import java.util.List;
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByProyectoId(Long proyectoId);

    // Portada: última imagen
    Media findFirstByProyectoIdAndFileTypeStartingWithOrderByUploadedAtDesc(
            Long proyectoId,
            String fileTypePrefix
    );

    // Documento principal: último media que NO sea imagen
    Media findFirstByProyectoIdAndFileTypeNotLikeOrderByUploadedAtDesc(
            Long proyectoId,
            String pattern
    );

    List<Media> findByProyecto_IdOrderByUploadedAtDesc(Long id);
    // si quieres seguir usando los otros:
    Media findFirstByProyecto_IdAndFileTypeStartingWithOrderByUploadedAtDesc(Long proyectoId, String prefix);
    Media findFirstByProyecto_IdAndFileTypeNotLikeOrderByUploadedAtDesc(Long proyectoId, String pattern);
    List<Media> findTop10ByOrderByUploadedAtDesc();
}


