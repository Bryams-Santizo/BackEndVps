package com.coordinacioncafesystem.Controller;

import com.coordinacioncafesystem.Dto.AdminDashboardSummary;
import com.coordinacioncafesystem.Entity.Usuario;
import com.coordinacioncafesystem.Service.AdminDashboardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "${frontend.origin}")
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    public AdminDashboardController(AdminDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard-resumen")
    public AdminDashboardSummary getDashboardResumen(
            @AuthenticationPrincipal Usuario usuarioLogueado
    ) {
        return dashboardService.buildSummary(usuarioLogueado);
    }
}
