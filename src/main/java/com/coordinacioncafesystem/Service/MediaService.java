package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Media;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MediaService {

    Media store(MultipartFile file, Long proyectoId, Long uploaderId) throws IOException;

    Resource loadAsResource(Long mediaId) throws IOException;

    List<Media> listByProyecto(Long proyectoId);
    void delete(Long mediaId) throws IOException;

    Media getMedia(Long id);

    Media update(Long mediaId, MultipartFile nuevoArchivo) throws IOException;
}
