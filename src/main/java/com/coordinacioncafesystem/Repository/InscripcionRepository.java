package com.coordinacioncafesystem.Repository;

import com.coordinacioncafesystem.Entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    List<Inscripcion> findByEstado(String estado);

    long deleteByCapacitacionId(Long capacitacionId);
}
