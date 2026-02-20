package com.coordinacioncafesystem.Repository;


import com.coordinacioncafesystem.Entity.Capacitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapacitacionRepository extends JpaRepository<Capacitacion, Long> {
    List<Capacitacion> findByActivoTrue();

}
