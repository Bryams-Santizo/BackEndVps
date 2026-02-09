package com.coordinacioncafesystem.Repository;

import com.coordinacioncafesystem.Entity.ActividadesdelCafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends JpaRepository<ActividadesdelCafe, Long> {


}
