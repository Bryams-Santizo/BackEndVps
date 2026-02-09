package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Eventos;
import com.coordinacioncafesystem.Entity.Proyectos;
import com.coordinacioncafesystem.Repository.EventosRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EventoServiceImpl implements EventoService {

    private final EventosRepository eventosRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public EventoServiceImpl(EventosRepository eventosRepository) {
        this.eventosRepository = eventosRepository;
    }

    // 🚩 MÉTODO AUXILIAR PARA GUARDAR ARCHIVOS (REUSABLE)
    private String guardarArchivo(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String nombreArchivo = UUID.randomUUID().toString() + extension;

        Path rutaDirectorio = Paths.get(uploadDir).toAbsolutePath();
        Path rutaArchivo = rutaDirectorio.resolve(nombreArchivo);

        if (!Files.exists(rutaDirectorio)) {
            Files.createDirectories(rutaDirectorio);
        }

        Files.copy(file.getInputStream(), rutaArchivo);
        return nombreArchivo; // Retorna solo el nombre/UUID para la DB
    }


    // 🚩 1. MÉTODO PRINCIPAL DE CREACIÓN (Maneja los dos archivos)
    @Override
    public Eventos crearConArchivos(Eventos evento, MultipartFile imagen, MultipartFile documento) {
        try {
            // 1. Procesar y guardar la IMAGEN PRINCIPAL
            String rutaImagen = guardarArchivo(imagen);
            evento.setRutaImagen(rutaImagen); // 🚩 ASIGNACIÓN CRÍTICA

            // 2. Procesar y guardar el DOCUMENTO/EVIDENCIA
            String rutaEvidencia = guardarArchivo(documento);
            evento.setRutaEvidencia(rutaEvidencia); // 🚩 ASIGNACIÓN CRÍTICA

            // El enlace externo (URL) ya viene en el objeto 'evento'

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar los archivos: " + e.getMessage());
        }

        return eventosRepository.save(evento);
    }

    // ❌ ELIMINAMOS O RENOMBRAMOS el método crearConEvidencia si ya no se usa.
    // Usaremos el nombre que tenía pero con solo 1 argumento para el caso base:

    @Override
    public Eventos crear(Eventos evento) {
        // 🚩 LLAMA AL REPOSITORIO DIRECTAMENTE (Opción más simple)
        // Ya no llama al método con archivos
        return eventosRepository.save(evento);
    }

    // -------------------------------------------------------------
    // --- MÉTODOS DE LECTURA Y CRUD BÁSICO ---
    // -------------------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public List<Eventos> listarTodos() {
        return eventosRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Eventos> buscarPorId(Long id) {
        return eventosRepository.findById(id);
    }

    @Override
    public Eventos actualizar(Long id, Eventos evento) {
        throw new UnsupportedOperationException("Use actualizarConArchivos");
    }


    @Override
    public Eventos actualizarConArchivos(
            Long id,
            Eventos evento,
            MultipartFile imagen,
            MultipartFile documento
    ) {
        Eventos existente = eventosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        // 🔒 Mantener relaciones importantes
        evento.setId_evento(id);
        evento.setTecnologico(existente.getTecnologico());

        // ✏️ Actualizar campos normales
        existente.setNombre(evento.getNombre());
        existente.setTipo(evento.getTipo());
        existente.setFechaInicio(evento.getFechaInicio());
        existente.setFechaFin(evento.getFechaFin());
        existente.setLugar(evento.getLugar());
        existente.setOrganizador(evento.getOrganizador());
        existente.setObjetivo(evento.getObjetivo());
        existente.setPublicoObjetivo(evento.getPublicoObjetivo());
        existente.setPrograma(evento.getPrograma());
        existente.setPonentes(evento.getPonentes());
        existente.setRequisitos(evento.getRequisitos());
        existente.setMateriales(evento.getMateriales());
        existente.setEnlaceExterno(evento.getEnlaceExterno());

        try {
            // 🖼️ SOLO si llega nueva imagen
            if (imagen != null && !imagen.isEmpty()) {
                String rutaImagen = guardarArchivo(imagen);
                existente.setRutaImagen(rutaImagen);
            }

            // 📄 SOLO si llega nuevo documento
            if (documento != null && !documento.isEmpty()) {
                String rutaDocumento = guardarArchivo(documento);
                existente.setRutaEvidencia(rutaDocumento);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al actualizar archivos: " + e.getMessage());
        }

        return eventosRepository.save(existente);
    }


    @Override
    public void eliminar(Long id) {
        if (!eventosRepository.existsById(id)) {
            throw new RuntimeException("Evento no encontrado para eliminar.");
        }
        eventosRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Eventos> listarUltimosTres() {
        return eventosRepository.findTop3ByOrderByFechaInicioDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Eventos> listarPublicos() {
        return eventosRepository.findAllByOrderByFechaInicioDesc();
    }
    @Override
    public List<Eventos> listarPorTecnologico(Long tecnologicoId) {
        return eventosRepository.findByTecnologicoId(tecnologicoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Eventos> listarProximos() {
        return eventosRepository.findByFechaFinAfterOrderByFechaInicioAsc(new Date());
    }
}