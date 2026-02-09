package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Dto.RegistroParticipanteDTO;
import com.coordinacioncafesystem.Entity.*;
import com.coordinacioncafesystem.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParticipanteService {

    @Autowired private ParticipanteRepository repository;
    @Autowired private TecnologicoRepository tecnologicoRepo;
    @Autowired private EmpresaRepository empresaRepo;
    @Autowired private GobiernoRepository gobiernoRepo;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private RolRepository rolRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    // Repositorios para actividad
    @Autowired private ProyectosRepository proyectoRepo;
    @Autowired private EventosRepository eventoRepo;
    @Autowired private MediaRepository mediaRepo;

    // --- MÉTODOS ORIGINALES MANTENIDOS ---

    public List<Participantes> listarTodos() {
        return repository.findAll();
    }

    public Participantes guardar(Participantes participante) {
        return repository.save(participante);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Map<String, Object> obtenerTodaLaActividad(Long participanteId) {
        Map<String, Object> respuesta = new HashMap<>();

        // 1. Buscamos al participante primero
        Participantes participante = repository.findById(participanteId)
                .orElseThrow(() -> new RuntimeException("Participante no encontrado"));

        // 2. Obtenemos el ID del tecnológico vinculado (si existe)
        Long tecId = (participante.getTecnologico() != null) ? participante.getTecnologico().getId() : null;

        // 3. Buscamos proyectos y eventos usando el ID del tecnológico
        if (tecId != null) {
            List<Proyectos> proyectos = proyectoRepo.findByTecnologicoId(tecId);
            proyectos.forEach(p -> p.setMedias(mediaRepo.findByProyectoId(p.getId())));

            respuesta.put("proyectos", proyectos);
            respuesta.put("eventos", eventoRepo.findByTecnologicoId(tecId));
            // Usamos el nombre del tecnológico si existe, si no, el del participante
            respuesta.put("nombre", participante.getTecnologico().getNombre());
        } else {
            // Si es una Empresa o no tiene tecnológico vinculado
            respuesta.put("proyectos", List.of());
            respuesta.put("eventos", List.of());
            respuesta.put("nombre", participante.getNombre());
        }

        return respuesta;
    }

    // --- NUEVO MÉTODO DE REGISTRO UNIFICADO ---

    @Transactional
    public Participantes registrarCompleto(RegistroParticipanteDTO dto) {

        // 1. Buscar el ROL usando el ID que viene de Angular (dto.rolId())
        Rol rol = rolRepository.findById(dto.rolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + dto.rolId()));

        Tecnologicos tec = null;
        Empresas emp = null;
        Gobierno gob = null;

        // 2. Crear Entidad según tipo
        // Nota: Asegúrate de que dto.tipo() devuelva el ENUM o conviértelo adecuadamente
        switch (dto.tipo()) {
            case TECNOLOGICO -> {
                tec = new Tecnologicos();
                tec.setNombre(dto.nombre());
                tec.setEstado(dto.estado());
                tec.setCorreoContacto(dto.correo());
                tec = tecnologicoRepo.save(tec);
            }
            case EMPRESA -> {
                emp = new Empresas();
                emp.setNombre(dto.nombre());
                emp.setCorreo(dto.correo());
                emp = empresaRepo.save(emp);
            }
        }

        // 3. Crear el Usuario de acceso
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre());
        usuario.setCorreo(dto.correo());
        usuario.setPassword(passwordEncoder.encode(dto.password())); // Aquí ya no será null
        usuario.setActivo(true);
        usuario.setRol(rol); // 🚩 USAMOS EL ROL ENCONTRADO POR ID

        if (tec != null) usuario.setTecnologico(tec);
        usuarioRepo.save(usuario);

        // 4. Crear el Participante vinculado
        Participantes participante = new Participantes();
        participante.setNombre(dto.nombre());
        participante.setTipo(dto.tipo());
        participante.setCorreo(dto.correo());
        participante.setTelefono(dto.telefono());
        participante.setEstado(dto.estado());

        participante.setTecnologico(tec);
        participante.setEmpresa(emp);
        participante.setGobierno(gob);

        return repository.save(participante);
    }

    @Transactional
    public Participantes actualizar(Long id, RegistroParticipanteDTO dto) {
        Participantes p = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante no encontrado"));

        // Actualizar datos básicos
        p.setNombre(dto.nombre());
        p.setCorreo(dto.correo());
        p.setTelefono(dto.telefono());
        p.setEstado(dto.estado());

        // Si necesitas actualizar la entidad relacionada (Tecnologico/Empresa)
        if (p.getTecnologico() != null) {
            Tecnologicos tec = p.getTecnologico();
            tec.setNombre(dto.nombre());
            tec.setEstado(dto.estado());
            tecnologicoRepo.save(tec);
        }

        // Actualizar Usuario si el correo cambió o si se envió un nuevo password
        Usuario user = usuarioRepo.findByCorreo(p.getCorreo()).orElse(null);
        if (user != null) {
            user.setCorreo(dto.correo());
            if (dto.password() != null && !dto.password().isBlank()) {
                user.setPassword(passwordEncoder.encode(dto.password()));
            }
            usuarioRepo.save(user);
        }

        return repository.save(p);
    }

    @Transactional // Importante: Asegura que toda la operación sea una sola unidad
    public void eliminarCompleto(Long id) {
        // 1. Buscamos el participante para conocer sus vínculos
        Participantes participante = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante no encontrado"));

        // 2. Borrar el Usuario de acceso asociado (se busca por el correo)
        usuarioRepo.findByCorreo(participante.getCorreo()).ifPresent(usuario -> {
            usuarioRepo.delete(usuario);
        });

        // 3. Si es de tipo TECNOLOGICO, borrar sus dependencias
        if (participante.getTecnologico() != null) {
            Long tecId = participante.getTecnologico().getId();

            // a. Borrar Eventos relacionados
            List<Eventos> eventos = eventoRepo.findByTecnologicoId(tecId);
            eventoRepo.deleteAll(eventos);

            // b. Borrar Proyectos (y sus registros de Media)
            List<Proyectos> proyectos = proyectoRepo.findByTecnologicoId(tecId);
            for (Proyectos proy : proyectos) {
                // Borrar fotos/archivos del proyecto
                var medias = mediaRepo.findByProyectoId(proy.getId());
                mediaRepo.deleteAll(medias);
                // Borrar el proyecto
                proyectoRepo.delete(proy);
            }

            // c. Borrar el registro en la tabla Tecnologicos
            tecnologicoRepo.deleteById(tecId);
        }

        // 4. Si es de tipo EMPRESA, borrar registro en tabla Empresas
        if (participante.getEmpresa() != null) {
            empresaRepo.delete(participante.getEmpresa());
        }

        // 5. Finalmente, borrar el registro del Participante
        repository.delete(participante);
    }
}