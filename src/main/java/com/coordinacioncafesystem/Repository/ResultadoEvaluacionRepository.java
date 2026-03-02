package com.coordinacioncafesystem.Repository;


import com.coordinacioncafesystem.Entity.ResultadoEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoEvaluacionRepository extends JpaRepository<ResultadoEvaluacion, Long> {
}
