package com.coordinacioncafesystem.Repository;

import com.coordinacioncafesystem.Entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    List<Asistencia> findByEstatus(String estatus);


    List<Asistencia> findByNombreInstitucionViculadaIsNotNull();


}
