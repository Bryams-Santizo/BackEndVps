package com.coordinacioncafesystem.Repository;

import com.coordinacioncafesystem.Entity.Eventos;
import com.coordinacioncafesystem.Entity.Proyectos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventosRepository extends JpaRepository<Eventos, Long> {

    // Todos los eventos ordenados por fechaInicio DESC
    List<Eventos> findAllByOrderByFechaInicioDesc();

    List<Eventos> findByTecnologicoId(Long tecnologicoId);

    // Últimos 3 eventos por fechaInicio DESC
    List<Eventos> findTop3ByOrderByFechaInicioDesc();

    // Próximos eventos: fechaFin después de la fecha indicada
    List<Eventos> findByFechaFinAfterOrderByFechaInicioAsc(Date fecha);

    List<Eventos> findAll();
}
