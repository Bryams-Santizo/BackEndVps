package com.coordinacioncafesystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coordinacioncafesystem.Entity.Rol;
import java.util.Optional;
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}


