package com.coordinacioncafesystem.Repository;

import com.coordinacioncafesystem.Entity.GaleriaEvidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GaleriaRepository extends JpaRepository<GaleriaEvidencia, Long> {
    // Para listar evidencias por participante



    List<GaleriaEvidencia> findByTecnologicoId(Long tecnologicoId);
}
