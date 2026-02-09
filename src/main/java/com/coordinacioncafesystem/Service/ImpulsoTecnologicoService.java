package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.ImpulsoTecnologico;
import com.coordinacioncafesystem.Repository.ImpulsoTecnologicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ImpulsoTecnologicoService {

    @Autowired
    private ImpulsoTecnologicoRepository repository;

    private final String UPLOAD_DIR = "uploads/";

    // --- 1. LISTAR TODOS ---
    public List<ImpulsoTecnologico> listarTodo() {
        return repository.findAll();
    }

    // --- 2. OBTENER POR ID ---
    public ImpulsoTecnologico obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con ID: " + id));
    }

    // --- 3. GUARDAR / ACTUALIZAR ---
    public ImpulsoTecnologico guardarConArchivos(ImpulsoTecnologico t, MultipartFile doc, MultipartFile carta) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // Si estamos editando (ID existe), buscamos el registro actual para no perder las rutas de archivos previos
        if (t.getId() != null) {
            Optional<ImpulsoTecnologico> existente = repository.findById(t.getId());
            if (existente.isPresent()) {
                // Si no se envió un nuevo doc, mantenemos el path anterior
                if (doc == null || doc.isEmpty()) {
                    t.setDocumentoPath(existente.get().getDocumentoPath());
                } else {
                    eliminarArchivoFisico(existente.get().getDocumentoPath()); // Opcional: borrar el viejo al reemplazar
                }
                // Si no se envió nueva carta, mantenemos el path anterior
                if (carta == null || carta.isEmpty()) {
                    t.setCartaPath(existente.get().getCartaPath());
                } else {
                    eliminarArchivoFisico(existente.get().getCartaPath()); // Opcional: borrar el viejo al reemplazar
                }
            }
        }

        // Procesar nuevo documento
        if (doc != null && !doc.isEmpty()) {
            String docName = System.currentTimeMillis() + "_doc_" + doc.getOriginalFilename();
            doc.transferTo(new File(UPLOAD_DIR + docName));
            t.setDocumentoPath(docName);
        }

        // Procesar nueva carta
        if (carta != null && !carta.isEmpty()) {
            String cartaName = System.currentTimeMillis() + "_carta_" + carta.getOriginalFilename();
            carta.transferTo(new File(UPLOAD_DIR + cartaName));
            t.setCartaPath(cartaName);
        }

        return repository.save(t);
    }

    // --- 4. ELIMINAR ---
    public void eliminar(Long id) {
        ImpulsoTecnologico t = obtenerPorId(id);
        // Borramos los archivos del disco antes de borrar el registro
        eliminarArchivoFisico(t.getDocumentoPath());
        eliminarArchivoFisico(t.getCartaPath());
        repository.deleteById(id);
    }

    // --- MÉTODO AUXILIAR PARA BORRAR ARCHIVOS ---
    private void eliminarArchivoFisico(String nombreArchivo) {
        if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
            try {
                Path path = Paths.get(UPLOAD_DIR + nombreArchivo);
                Files.deleteIfExists(path);
            } catch (IOException e) {
                System.err.println("Error al eliminar archivo: " + e.getMessage());
            }
        }
    }
}