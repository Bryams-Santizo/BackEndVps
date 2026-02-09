package com.coordinacioncafesystem.Repository;

import com.coordinacioncafesystem.Entity.Participantes;
import com.coordinacioncafesystem.Entity.Proyectos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadisticasRepository extends JpaRepository<Proyectos, Long> {


    /* 1. Proyectos por mes */
    @Query(value = """
        SELECT TO_CHAR(p.fecha_inicio, 'YYYY-MM') as mes, COUNT(*) 
        FROM proyectos p 
        GROUP BY mes 
        ORDER BY mes
    """, nativeQuery = true)
    List<Object[]> proyectosPorMes();

    /* 2. Participantes por Tipo (Ya que no hay fecha) */
    @Query(value = """
    SELECT pa.tipo, COUNT(*) 
    FROM participantes pa 
    GROUP BY pa.tipo
""", nativeQuery = true)
    List<Object[]> participantesPorMes();

    /* 3. Proyectos por estado (Región) */
    @Query(value = """
        SELECT r.nombre, COUNT(p.id) 
        FROM proyectos p 
        JOIN regiones r ON p.region_id = r.id 
        GROUP BY r.nombre
    """, nativeQuery = true)
    List<Object[]> proyectosPorEstado();

    /* 4. Tipos de proyectos */
    @Query(value = """
        SELECT p.actividad, COUNT(*) 
        FROM proyectos p 
        GROUP BY p.actividad
    """, nativeQuery = true)
    List<Object[]> tiposProyecto();

    /* 5. Estado de proyectos */
    @Query(value = """
        SELECT p.estado_proyecto, COUNT(*) 
        FROM proyectos p 
        GROUP BY p.estado_proyecto
    """, nativeQuery = true)
    List<Object[]> estadoProyectos();


    //rendimiento
    @Query("SELECT COUNT(p) FROM Proyectos p")
    long countTotalProyectos();

    // Nota: Aunque el repo es de Proyectos, puedes contar Participantes
    // si la entidad está mapeada en el sistema.
    @Query("SELECT COUNT(pa) FROM Participantes pa")
    long countTotalParticipantes();

}