package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Entity.Tecnologicos;
import com.coordinacioncafesystem.Service.TecnologicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tecnologicos")
@CrossOrigin(origins = "${frontend.origin}")
public class TecnologicoController {
    private final TecnologicoService service;
    public TecnologicoController(TecnologicoService service){ this.service = service; }

    @GetMapping public Page<Tecnologicos> listar(Pageable p){ return service.listar(p); }

    @GetMapping("/{id}") public Tecnologicos get(@PathVariable Long id){ return service.getById(id); }

    @PostMapping @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINADOR')")
    public ResponseEntity<Tecnologicos> crear(@RequestBody Tecnologicos t){ return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(t)); }
}
