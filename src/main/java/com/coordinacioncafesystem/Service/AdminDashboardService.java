package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Dto.ActividadRecienteDTO;
import com.coordinacioncafesystem.Dto.AdminDashboardSummary;
import com.coordinacioncafesystem.Entity.Media;
import com.coordinacioncafesystem.Entity.Proyectos;
import com.coordinacioncafesystem.Entity.Tecnologicos;
import com.coordinacioncafesystem.Entity.Usuario;
import com.coordinacioncafesystem.Repository.MediaRepository;
import com.coordinacioncafesystem.Repository.ProyectosRepository;
import com.coordinacioncafesystem.Repository.TecnologicoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminDashboardService {

    private final TecnologicoRepository tecnRepo;
    private final ProyectosRepository proyRepo;
    private final MediaRepository mediaRepo;

    public AdminDashboardService(
            TecnologicoRepository tecnRepo,
            ProyectosRepository proyRepo,
            MediaRepository mediaRepo
    ) {
        this.tecnRepo = tecnRepo;
        this.proyRepo = proyRepo;
        this.mediaRepo = mediaRepo;
    }

    public AdminDashboardSummary buildSummary(Usuario admin) {
        AdminDashboardSummary dto = new AdminDashboardSummary();

        // Datos del admin
        dto.setAdminNombre(
                admin != null && admin.getNombre() != null
                        ? admin.getNombre()
                        : "Administrador"
        );

        if (admin != null && admin.getTecnologico() != null) {
            dto.setTecnologicoNombre(admin.getTecnologico().getNombre());
        } else {
            dto.setTecnologicoNombre("Sin tecnológico asignado");
        }

        // Contadores
        dto.setTotalTecnologicos(tecnRepo.count());
        dto.setTotalProyectos(proyRepo.count());
        dto.setProyectosActivos(proyRepo.countByEstadoProyecto("En curso"));
        dto.setTotalEventos(0L); // luego lo conectas a tu tabla de eventos

        // 🔹 Actividad reciente basada en documentos subidos (Media)
        List<ActividadRecienteDTO> actividades = new ArrayList<>();

        // No hay filtro de días: si fue hace 5 días, 10, etc., igual entra
        List<Media> ultimos = mediaRepo.findTop10ByOrderByUploadedAtDesc();

        for (Media m : ultimos) {
            ActividadRecienteDTO item = new ActividadRecienteDTO();
            item.setTipo("MEDIA");
            item.setEtiqueta("Archivo");
            item.setBadgeColor("info");

            Proyectos p = m.getProyecto();
            Tecnologicos tec = p != null ? p.getTecnologico() : null;

            String tecNombre = tec != null ? tec.getNombre() : "TecNM";
            String proyectoNombre = p != null ? p.getNombre() : "(sin proyecto)";

            item.setMensaje(
                    "El " + tecNombre +
                            " subió \"" + m.getFileName() +
                            "\" al proyecto \"" + proyectoNombre + "\"."
            );

            LocalDateTime fecha = m.getUploadedAt();
            if (fecha == null) {
                fecha = LocalDateTime.now();
            }
            item.setFecha(fecha);

            actividades.add(item);
        }

        dto.setActividadReciente(actividades);

        return dto;
    }
}
