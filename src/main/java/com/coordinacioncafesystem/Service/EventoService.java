package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Eventos;
import com.coordinacioncafesystem.Entity.Proyectos;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface EventoService {

    // === Coordinador (privado) ===

    // 1. CREACIÓN BÁSICA (solo JSON)
    Eventos crear(Eventos evento);

    // 2. CREACIÓN CON ARCHIVOS
    Eventos crearConArchivos(Eventos evento, MultipartFile imagen, MultipartFile documento);

    // 3. ACTUALIZACIÓN CON ARCHIVOS  ✅ (ESTE FALTABA)
    Eventos actualizarConArchivos(
            Long id,
            Eventos evento,
            MultipartFile imagen,
            MultipartFile documento
    );

    // 4. CRUD NORMAL
    List<Eventos> listarTodos();
    Optional<Eventos> buscarPorId(Long id);
    Eventos actualizar(Long id, Eventos evento);
    void eliminar(Long id);

    // === Público ===
    List<Eventos> listarPublicos();
    List<Eventos> listarProximos();
    List<Eventos> listarUltimosTres();
    List<Eventos> listarPorTecnologico(Long tecnologicoId);
}
