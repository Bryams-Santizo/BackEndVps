package com.coordinacioncafesystem.Repository;

import com.coordinacioncafesystem.Entity.Certificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CertificacionRepository extends JpaRepository<Certificacion, Long> {

    List<Certificacion> findByMercado(String mercado);

}
