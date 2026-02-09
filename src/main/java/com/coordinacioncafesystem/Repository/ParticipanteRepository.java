package com.coordinacioncafesystem.Repository;

import com.coordinacioncafesystem.Entity.Participantes;
import com.coordinacioncafesystem.enums.TipoParticipante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participantes, Long> {
    List<Participantes> findByTipo(TipoParticipante tipo);



}

