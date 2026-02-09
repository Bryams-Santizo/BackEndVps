package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Dto.AvanceDTO;
import com.coordinacioncafesystem.Entity.Avance;
import com.coordinacioncafesystem.Entity.Proyectos;
import com.coordinacioncafesystem.Entity.Usuario;
import com.coordinacioncafesystem.Repository.AvanceRepository;
import com.coordinacioncafesystem.Repository.ProyectosRepository;
import com.coordinacioncafesystem.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class AvanceServiceImpl implements AvanceService {
    private final AvanceRepository avanceRepo;
    private final ProyectosRepository proyectoRepo;
    private final UsuarioRepository usuarioRepo;
    public AvanceServiceImpl(AvanceRepository avanceRepo, ProyectosRepository proyectoRepo, UsuarioRepository usuarioRepo){
        this.avanceRepo = avanceRepo; this.proyectoRepo = proyectoRepo; this.usuarioRepo = usuarioRepo;
    }
    @Override
    public Avance crear(Long proyectoId, AvanceDTO dto, Long autorId){
        Proyectos p = proyectoRepo.findById(proyectoId).orElseThrow();
        Usuario u = usuarioRepo.findById(autorId).orElseThrow();
        Avance a = new Avance();
        a.setProyecto(p); a.setTitulo(dto.getTitulo()); a.setDescripcion(dto.getDescripcion());
        a.setAutor(u); a.setFechaRegistro(LocalDateTime.now());
        return avanceRepo.save(a);
    }
    @Override public List<Avance> listarPorProyecto(Long proyectoId){ return avanceRepo.findByProyectoId(proyectoId); }
}
