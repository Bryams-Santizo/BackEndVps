package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.GaleriaEvidencia;
import com.coordinacioncafesystem.Entity.Participantes;
import com.coordinacioncafesystem.Repository.GaleriaRepository;
import com.coordinacioncafesystem.Repository.ParticipanteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class GaleriaServiceImpl implements IGaleriaService {

    @Autowired
    private GaleriaRepository galeriaRepository;

    @Autowired
    private ParticipanteRepository participanteRepository; // Para buscar al dueño de la foto



    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    @Transactional
    public GaleriaEvidencia guardarEvidencia(MultipartFile archivo, GaleriaEvidencia evidencia) throws IOException {
        // 1. Crear carpeta si no existe
        Path rutaDirectorio = Paths.get(uploadDir).toAbsolutePath();
        if (!Files.exists(rutaDirectorio)) {
            Files.createDirectories(rutaDirectorio);
        }

        // 2. Procesar archivo si existe
        if (archivo != null && !archivo.isEmpty()) {
            String extension = archivo.getOriginalFilename().substring(archivo.getOriginalFilename().lastIndexOf("."));
            String nombreFisico = UUID.randomUUID().toString() + extension;
            Path rutaArchivo = rutaDirectorio.resolve(nombreFisico);

            Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

            // Seteamos la ruta en la entidad que recibimos
            evidencia.setRutaImagen(nombreFisico);
            evidencia.setNombreArchivo(archivo.getOriginalFilename());
        }

        // 3. Guardar en DB (la relación con Tecnológico ya viene seteada desde el Controller)
        return galeriaRepository.save(evidencia);
    }

    @Override
    public List<GaleriaEvidencia>listarPorTecnologico(Long tecnologicoId) {
        return galeriaRepository.findByTecnologicoId(tecnologicoId);
    }

    @Override
    public List<GaleriaEvidencia> listarTodas() {
        return galeriaRepository.findAll();
    }


    @Override
    @Transactional
    public void eliminarEvidencia(Long id) throws IOException {
        GaleriaEvidencia ev = galeriaRepository.findById(id).orElseThrow();
        Path path = Paths.get(uploadDir).resolve(ev.getRutaImagen());
        Files.deleteIfExists(path);
        galeriaRepository.delete(ev);
    }
}
