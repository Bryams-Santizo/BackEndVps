package com.coordinacioncafesystem.Dto;

import java.util.List;

public class AdminDashboardSummary {

    private String adminNombre;
    private String tecnologicoNombre;

    private long totalTecnologicos;
    private long totalProyectos;
    private long proyectosActivos;
    private long totalEventos;

    private List<ActividadRecienteDTO> actividadReciente;

    public String getAdminNombre() { return adminNombre; }
    public void setAdminNombre(String adminNombre) { this.adminNombre = adminNombre; }

    public String getTecnologicoNombre() { return tecnologicoNombre; }
    public void setTecnologicoNombre(String tecnologicoNombre) { this.tecnologicoNombre = tecnologicoNombre; }

    public long getTotalTecnologicos() { return totalTecnologicos; }
    public void setTotalTecnologicos(long totalTecnologicos) { this.totalTecnologicos = totalTecnologicos; }

    public long getTotalProyectos() { return totalProyectos; }
    public void setTotalProyectos(long totalProyectos) { this.totalProyectos = totalProyectos; }

    public long getProyectosActivos() { return proyectosActivos; }
    public void setProyectosActivos(long proyectosActivos) { this.proyectosActivos = proyectosActivos; }

    public long getTotalEventos() { return totalEventos; }
    public void setTotalEventos(long totalEventos) { this.totalEventos = totalEventos; }

    public List<ActividadRecienteDTO> getActividadReciente() {
        return actividadReciente;
    }

    public void setActividadReciente(List<ActividadRecienteDTO> actividadReciente) {
        this.actividadReciente = actividadReciente;
    }
}
