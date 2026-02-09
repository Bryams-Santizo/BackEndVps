package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Media;
import com.coordinacioncafesystem.Entity.Proyectos;
import com.coordinacioncafesystem.Entity.Usuario;
import com.coordinacioncafesystem.Repository.MediaRepository;
import com.coordinacioncafesystem.Repository.ProyectosRepository;
import com.coordinacioncafesystem.Repository.UsuarioRepository;
import com.coordinacioncafesystem.Service.MediaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepo;
    private final ProyectosRepository proyectosRepo;
    private final UsuarioRepository usuarioRepo;

    private final Path rootLocation;

    public MediaServiceImpl(MediaRepository mediaRepo,
                            ProyectosRepository proyectosRepo,
                            UsuarioRepository usuarioRepo,
                            @Value("${media.upload-dir:uploads}") String uploadDir){

        this.mediaRepo = mediaRepo;
        this.proyectosRepo = proyectosRepo;
        this.usuarioRepo = usuarioRepo;
        this.rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio de subida", e);
        }
    }

    @Override
    public Media store(MultipartFile file, Long proyectoId, Long uploaderId) throws IOException {
        Proyectos proyecto = proyectosRepo.findById(proyectoId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        Usuario usuario = usuarioRepo.findById(uploaderId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (file.isEmpty()) {
            throw new RuntimeException("Archivo vacío");
        }

        String originalName = file.getOriginalFilename();
        String extension = "";
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        String storedName = UUID.randomUUID() + extension;
        Path targetFile = this.rootLocation.resolve(storedName);

        Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);

        Media media = new Media();
        media.setFileName(originalName);
        media.setFileType(file.getContentType());       // <- aquí queda "image/png" o "application/pdf"
        media.setUrl(storedName);                      // nombre físico del archivo
        media.setUploadedAt(LocalDateTime.now());
        media.setProyecto(proyecto);
        media.setUploadedBy(usuario);

        return mediaRepo.save(media);
    }

    @Override
    public Resource loadAsResource(Long mediaId) throws IOException {
        Media media = mediaRepo.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media no encontrada"));

        Path filePath = this.rootLocation.resolve(media.getUrl()).normalize();
        Resource resource = new FileSystemResource(filePath);

        if (!resource.exists()) {
            throw new RuntimeException("No se encontró el archivo: " + media.getUrl());
        }

        return resource;
    }

    @Override
    public List<Media> listByProyecto(Long proyectoId) {
        return mediaRepo.findByProyecto_IdOrderByUploadedAtDesc(proyectoId);
    }

    @Override
    public void delete(Long mediaId) throws IOException {
        Media media = mediaRepo.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media no encontrada"));

        Path filePath = this.rootLocation.resolve(media.getUrl()).normalize();
        Files.deleteIfExists(filePath);

        mediaRepo.delete(media);
    }

    @Override
    public Media getMedia(Long id) {
        return mediaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Media no encontrada"));
    }

    @Override
    public Media update(Long mediaId, MultipartFile nuevoArchivo) throws IOException {
        Media media = mediaRepo.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media no encontrada"));

        // borrar archivo viejo
        Path oldPath = this.rootLocation.resolve(media.getUrl()).normalize();
        Files.deleteIfExists(oldPath);

        // guardar nuevo
        String originalName = nuevoArchivo.getOriginalFilename();
        String extension = "";
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        String storedName = UUID.randomUUID() + extension;

        Path targetFile = this.rootLocation.resolve(storedName);
        Files.copy(nuevoArchivo.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);

        media.setFileName(originalName);
        media.setFileType(nuevoArchivo.getContentType());
        media.setUrl(storedName);
        media.setUploadedAt(LocalDateTime.now());

        return mediaRepo.save(media);
    }
}
