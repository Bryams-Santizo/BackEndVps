package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Asistencia;

import java.util.List;

public interface AsistenciaService {

    Asistencia guardarSolicitudProductor(Asistencia solicitud);
    List<Asistencia> listarSolicitudesPendientes();
    List<Asistencia> listarTodasLasVinculaciones();
    Asistencia vincularInstitucion(Long id, Asistencia datosAdmin);

}
