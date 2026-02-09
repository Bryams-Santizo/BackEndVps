package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Service.ReporteService;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "${frontend.origin}")
public class ReporteController {
    private final ReporteService reporteService;
    public ReporteController(ReporteService reporteService){ this.reporteService = reporteService; }

    @GetMapping("/tecnologico/{id}/export")
    @PreAuthorize("@securityService.canAccessByOrg(authentication.principal, #id) or hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportTecnologico(@PathVariable Long id) throws Exception {
        byte[] doc = reporteService.generateTecnologicoReport(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tecnologico_"+id+".docx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                .body(doc);
    }
}

