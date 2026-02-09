package com.coordinacioncafesystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coordinacioncafesystem.Entity.Organizacion;
import java.util.List;
public interface OrganizacionRepository extends JpaRepository<Organizacion, Long> {
    List<Organizacion> findByParentId(Long parentId);
}

