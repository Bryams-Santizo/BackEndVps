package com.coordinacioncafesystem.Repository;

import com.coordinacioncafesystem.Entity.Productores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductorRepository extends JpaRepository<Productores, Long> {
}
