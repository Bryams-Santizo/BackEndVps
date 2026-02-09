package com.coordinacioncafesystem.Controller;


import com.coordinacioncafesystem.Service.EstadisticasService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estadisticas")
@CrossOrigin(origins = "${frontend.origin}")
public class EstadisticasController {

    private final EstadisticasService service;

    public EstadisticasController(EstadisticasService service) {
        this.service = service;
    }

    @GetMapping("/proyectos-mes")
    public List<Object[]> proyectosPorMes() {
        return service.proyectosPorMes();
    }

    @GetMapping("/participantes-mes")
    public List<Object[]> participantesPorMes() {
        return service.participantesPorMes();
    }

    @GetMapping("/proyectos-estado")
    public List<Object[]> proyectosPorEstado() {
        return service.proyectosPorEstado();
    }

    @GetMapping("/tipos-proyecto")
    public List<Object[]> tiposProyecto() {
        return service.tiposProyecto();
    }

    @GetMapping("/estado-proyectos")
    public List<Object[]> estadoProyectos() {
        return service.estadoProyectos();
    }

    @GetMapping("/totales")
    public Map<String, Long> obtenerTotales() {
        Map<String, Long> respuesta = new HashMap<>();
        respuesta.put("totalProyectos", service.getTotalProyectos());
        respuesta.put("totalParticipantes", service.getTotalParticipantes());
        return respuesta;
    }

}
