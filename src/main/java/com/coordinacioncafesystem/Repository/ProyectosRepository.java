package com.coordinacioncafesystem.Repository;

import com.coordinacioncafesystem.Entity.Proyectos;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProyectosRepository extends JpaRepository<Proyectos, Long> {
    List<Proyectos> findByTecnologicoId(Long tecnologicoId);
    List<Proyectos> findByActividad(String actividad);
    long countByEstadoProyecto(String estadoProyecto);
    List<Proyectos> findTop5ByOrderByFechaInicioDesc();

}
