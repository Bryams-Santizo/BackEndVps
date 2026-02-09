package com.coordinacioncafesystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coordinacioncafesystem.Entity.Usuario;
import java.util.Optional;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}
