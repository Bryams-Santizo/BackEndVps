package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Dto.AvanceDTO;
import com.coordinacioncafesystem.Entity.Avance;
import com.coordinacioncafesystem.Service.AvanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/avances")
@CrossOrigin(origins = "${frontend.origin}")
public class AvanceController {
    private final AvanceService avanceService;
    public AvanceController(AvanceService avanceService){ this.avanceService = avanceService; }

    @PostMapping("/{proyectoId}")
    @PreAuthorize("@securityService.canAccessByOrg(authentication.principal, #proyectoId) or hasRole('ADMIN')")
    public ResponseEntity<Avance> crearAvance(@PathVariable Long proyectoId, @RequestBody AvanceDTO dto){
        Avance a = avanceService.crear(proyectoId, dto, dto.getAutorId());
        return ResponseEntity.status(HttpStatus.CREATED).body(a);
    }

    @GetMapping("/por-proyecto/{proyectoId}")
    public List<Avance> listarPorProyecto(@PathVariable Long proyectoId){
        return avanceService.listarPorProyecto(proyectoId);
    }
}
