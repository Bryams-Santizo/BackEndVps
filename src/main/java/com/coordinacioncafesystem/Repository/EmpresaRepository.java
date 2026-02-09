package com.coordinacioncafesystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coordinacioncafesystem.Entity.Empresas;
public interface EmpresaRepository extends JpaRepository<Empresas, Long> {}
