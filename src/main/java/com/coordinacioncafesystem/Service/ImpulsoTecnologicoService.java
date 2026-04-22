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
import java.nio.file.StandardCopyOption;
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
        // 1. Obtener la ruta absoluta de la carpeta del proyecto
        Path root = Paths.get("uploads").toAbsolutePath().normalize();

        // 2. Crear el directorio si no existe
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }

        // ... (lógica de edición para mantener archivos anteriores)

        // 3. Procesar nuevo documento (Imagen de tecnología)
        if (doc != null && !doc.isEmpty()) {
            String docName = System.currentTimeMillis() + "_doc_" + doc.getOriginalFilename();
            // USAR EL PATH ABSOLUTO AQUÍ
            Files.copy(doc.getInputStream(), root.resolve(docName), StandardCopyOption.REPLACE_EXISTING);
            t.setDocumentoPath(docName);
        }

        // 4. Procesar nueva carta (Imagen de evidencia)
        if (carta != null && !carta.isEmpty()) {
            String cartaName = System.currentTimeMillis() + "_carta_" + carta.getOriginalFilename();
            Files.copy(carta.getInputStream(), root.resolve(cartaName), StandardCopyOption.REPLACE_EXISTING);
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
