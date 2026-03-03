package com.coordinacioncafesystem.Repository;

import com.coordinacioncafesystem.Entity.Colaboracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboracionRepository extends JpaRepository<Colaboracion, Long> {
}
