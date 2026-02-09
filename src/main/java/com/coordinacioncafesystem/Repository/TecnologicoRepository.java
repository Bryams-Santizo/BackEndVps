package com.coordinacioncafesystem.Repository;

import com.coordinacioncafesystem.Entity.Tecnologicos;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coordinacioncafesystem.Entity.Tecnologicos;
import java.util.List;
import java.util.Optional;

public interface TecnologicoRepository extends JpaRepository<Tecnologicos, Long> {
    /**
     * Busca una lista de entidades Tecnologicos filtradas por el estado especificado.
     * Este método sigue la convención de Spring Data JPA.
     * @param estado El valor del estado a buscar (ej. "Activo", "Inactivo").
     * @return Una lista de Tecnologicos que coinciden con el estado.
     */
    List<Tecnologicos> findByEstado(String estado);
    Optional<Tecnologicos> findByNombre(String nombre);

}
