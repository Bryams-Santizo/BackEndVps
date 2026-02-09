package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Asistencia;
import com.coordinacioncafesystem.Repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsistenciaServiceImpl implements AsistenciaService {

    @Autowired
    private AsistenciaRepository repository;

    @Override
    @Transactional
    public Asistencia guardarSolicitudProductor(Asistencia solicitud) {
        solicitud.setEstatus("PENDIENTE");

        // CORRECCIÓN: Usar el nombre exacto de la entidad
        solicitud.setNombreInstitucionViculada(null);
        solicitud.setInstitucionId(null);
        solicitud.setTipoInstitucion(null);

        return repository.save(solicitud);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asistencia> listarSolicitudesPendientes() {
        return repository.findByEstatus("PENDIENTE");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asistencia> listarTodasLasVinculaciones() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Asistencia vincularInstitucion(Long id, Asistencia datosAdmin) {
        Asistencia solicitudExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la solicitud con ID: " + id));

        // Vincular los IDs de referencia
        solicitudExistente.setInstitucionId(datosAdmin.getInstitucionId());
        solicitudExistente.setTipoInstitucion(datosAdmin.getTipoInstitucion());

        // CORRECCIÓN: Usar setNombreInstitucionViculada para que coincida con tu Entity
        solicitudExistente.setNombreInstitucionViculada(datosAdmin.getNombreInstitucionViculada());

        // Estos se mantienen igual
        solicitudExistente.setEspecialistas(datosAdmin.getEspecialistas());
        solicitudExistente.setCostos(datosAdmin.getCostos());

        solicitudExistente.setEstatus("VINCULADO");

        return repository.save(solicitudExistente);
    }
}