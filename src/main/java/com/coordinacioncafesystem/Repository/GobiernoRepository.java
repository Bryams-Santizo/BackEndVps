package com.coordinacioncafesystem.Repository;

import com.coordinacioncafesystem.Entity.ActividadesdelCafe;
import com.coordinacioncafesystem.Entity.Gobierno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GobiernoRepository extends JpaRepository<Gobierno, Long> {
}
