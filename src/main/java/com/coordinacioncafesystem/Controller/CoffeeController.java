package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Service.CoffeeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/bolsa")
@CrossOrigin(origins = "${frontend.origin}") // Permite que Angular se conecte
public class CoffeeController {

    private final CoffeeService coffeeService;

    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping("/cafe-hoy")
    public Map<String, Double> getPrecio() {
        return coffeeService.obtenerPreciosConvertidos();
    }
}
