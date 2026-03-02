package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Entity.Certificacion;
import com.coordinacioncafesystem.Service.CertificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificaciones")
@CrossOrigin(origins = "${frontend.origin}")

public class CertificacionController {

    @Autowired
    private CertificacionService certificacionService;

    // Trae las certificaciones dependiendo del continente (Paso 2 y 3)
    @GetMapping("/mercado/{mercado}")
    public ResponseEntity<List<Certificacion>> obtenerCertificacionesPorMercado(@PathVariable String mercado) {
        return ResponseEntity.ok(certificacionService.obtenerPorMercado(mercado));
    }

}
