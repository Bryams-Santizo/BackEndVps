package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Entity.Productores;
import com.coordinacioncafesystem.Service.ProductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productores")
@CrossOrigin(origins = "${frontend.origin}")
public class ProductorController {

    @Autowired
    private ProductorService productorService;

    // Guarda el formulario del Paso 1
    @PostMapping
    public ResponseEntity<Productores> crearProductor(@RequestBody Productores productor) {
        return ResponseEntity.ok(productorService.guardar(productor));
    }

}
