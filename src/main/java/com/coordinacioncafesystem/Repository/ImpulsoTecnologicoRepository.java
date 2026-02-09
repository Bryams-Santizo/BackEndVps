package com.coordinacioncafesystem.Repository;


import com.coordinacioncafesystem.Entity.ImpulsoTecnologico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpulsoTecnologicoRepository extends JpaRepository<ImpulsoTecnologico, Long> {
}
