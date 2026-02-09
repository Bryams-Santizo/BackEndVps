package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Entity.Eventos;
import com.coordinacioncafesystem.Service.EventoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/eventos")

public class PublicEventoController {

    private final EventoService eventoService;

    public PublicEventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    // TODOS los eventos públicos (podrías usar esto para /ver-eventos)
    @GetMapping
    public List<Eventos> listarPublicos() {
        return eventoService.listarPublicos();
    }

    // Próximos eventos (si luego quieres una sección de "proximos")
    @GetMapping("/proximos")
    public List<Eventos> listarProximos() {
        return eventoService.listarProximos();
    }

    // Últimos 3 (para el home)
    @GetMapping("/ultimos")
    public List<Eventos> listarUltimosTres() {
        return eventoService.listarUltimosTres();
    }



}
