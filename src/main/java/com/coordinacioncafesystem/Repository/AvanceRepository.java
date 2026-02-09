package com.coordinacioncafesystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coordinacioncafesystem.Entity.Avance;
import java.util.List;
public interface AvanceRepository extends JpaRepository<Avance, Long> {
    List<Avance> findByProyectoId(Long proyectoId);
    List<Avance> findTop5ByOrderByFechaRegistroDesc();

}
