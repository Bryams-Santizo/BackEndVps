package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Entity.Asistencia;
import com.coordinacioncafesystem.Repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsistenciaServiceImpl implements AsistenciaService {

    @Autowired
    private AsistenciaRepository repository;

    @Autowired
    private JavaMailSender mailSender;

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

        // 1. Actualizamos los datos de vinculación
        solicitudExistente.setInstitucionId(datosAdmin.getInstitucionId());
        solicitudExistente.setTipoInstitucion(datosAdmin.getTipoInstitucion());
        solicitudExistente.setNombreInstitucionViculada(datosAdmin.getNombreInstitucionViculada());
        solicitudExistente.setEspecialistas(datosAdmin.getEspecialistas());
        solicitudExistente.setCostos(datosAdmin.getCostos());
        solicitudExistente.setEstatus("VINCULADO");

        // 2. Guardamos en la BD
        Asistencia actualizada = repository.save(solicitudExistente);

        // 3. Enviamos el correo
        enviarCorreoVinculacion(actualizada);

        return actualizada;
    }

    private void enviarCorreoVinculacion(Asistencia asis) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("adicamsistema@gmail.com");
        message.setTo(asis.getEmailSolicitante()); // Correo del productor
        message.setSubject("Actualización de su Asistencia Técnica - ADICAM");

        String cuerpo = "Estimado/a " + asis.getNombreSolicitante() + ",\n\n" +
                "Le informamos que su solicitud de asistencia técnica ha sido procesada con éxito.\n\n" +
                "--- DETALLES DE LA VINCULACIÓN ---\n" +
                "Tipo de Institución: " + asis.getTipoInstitucion() + "\n" +
                "Institución asignada: " + asis.getNombreInstitucionViculada() + "\n" +
                "Técnicos/Especialista: " + asis.getEspecialistas() + "\n\n" +
                "La institución se pondrá en contacto con usted.\n\n" +
                "Atentamente,\n" +
                "ADICAM - Alianza para el Desarrollo Integral de la Cafeticultura en México";

        message.setText(cuerpo);
        mailSender.send(message);
    }
}
