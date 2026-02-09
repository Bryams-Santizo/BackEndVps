package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Entity.Asistencia;
import com.coordinacioncafesystem.Repository.AsistenciaRepository;
import com.coordinacioncafesystem.Service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencia")
@CrossOrigin(origins = "${frontend.origin}")
public class AsistenciaController {


    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping("/solicitar")
    public ResponseEntity<Asistencia> crear(@RequestBody Asistencia solicitud) {
        return new ResponseEntity<>(asistenciaService.guardarSolicitudProductor(solicitud), HttpStatus.CREATED);
    }

    @PutMapping("/vincular/{id}")
    public ResponseEntity<Asistencia> vincular(@PathVariable Long id, @RequestBody Asistencia datos) {
        return new ResponseEntity<>(asistenciaService.vincularInstitucion(id, datos), HttpStatus.OK);
    }

    @GetMapping("/pendientes") // Cambia "/lista" por "/pendientes"
    public List<Asistencia> listar() {
        // Asegúrate de que este método en el servicio de Java
        // realmente devuelva los que tienen estatus PENDIENTE
        return asistenciaService.listarSolicitudesPendientes();
    }
}


