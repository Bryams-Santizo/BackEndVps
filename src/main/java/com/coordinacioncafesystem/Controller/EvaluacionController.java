package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Entity.ResultadoEvaluacion;
import com.coordinacioncafesystem.Service.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/evaluaciones")
@CrossOrigin(origins = "${frontend.origin}")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    // Recibe el examen terminado desde Angular (Paso 4)
    @PostMapping("/calcular")
    public ResponseEntity<ResultadoEvaluacion> calcularTest(@RequestBody Map<String, Object> payload) {
        // Extraemos los datos del JSON enviado por Angular
        Long productorId = Long.valueOf(payload.get("productorId").toString());
        Long certificacionId = Long.valueOf(payload.get("certificacionId").toString());
        int aciertos = (int) payload.get("aciertos");
        int total = (int) payload.get("total");
        String recomendaciones = (String) payload.get("recomendaciones");

        return ResponseEntity.ok(evaluacionService.procesarTest(
                productorId,
                certificacionId,
                aciertos,
                total,
                recomendaciones
        ));
    }

    // Genera y descarga el PDF (Paso 5)
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable Long id) {
        byte[] pdfBytes = evaluacionService.generarReportePDF(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Reporte_Certificacion_ADICAM.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
