package com.coordinacioncafesystem.Service;


import com.coordinacioncafesystem.Dto.DatosAceptacionDTO;
import com.coordinacioncafesystem.Entity.Inscripcion;
import com.coordinacioncafesystem.Repository.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository repository;

    @Autowired
    private JavaMailSender mailSender;

    public Inscripcion solicitarInscripcion(Inscripcion inscripcion) {
        inscripcion.setEstado("PENDIENTE");
        return repository.save(inscripcion);
    }

    public List<Inscripcion> listarPendientes() {
        return repository.findByEstado("PENDIENTE");
    }

    public void aceptarInscripcion(Long id, DatosAceptacionDTO datos) {
        Inscripcion inscripcion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        inscripcion.setEstado("ACEPTADO");
        repository.save(inscripcion);

        enviarCorreoConfirmacion(inscripcion, datos);
    }

    private void enviarCorreoConfirmacion(Inscripcion ins, DatosAceptacionDTO datos) {
        SimpleMailMessage message = new SimpleMailMessage();

        // REMITENTE OFICIAL DEL SISTEMA
        message.setFrom("adicamsistema@gmail.com");
        message.setTo(ins.getEmailAlumno());
        message.setSubject("¡Inscripción Aceptada! - ADICAM");

        String cuerpo = "Hola " + ins.getNombreAlumno() + ",\n\n" +
                "Tu solicitud para el curso: '" + ins.getCapacitacion().getNombre() + "' ha sido APROBADA.\n\n" +
                "--- DETALLES DEL EVENTO ---\n" +
                "Fecha: " + datos.getFechaInicio() + "\n" +
                "Hora: " + datos.getHora() + "\n" +
                "Lugar/Enlace: " + datos.getLugar() + "\n\n" +
                "Mensaje del Coordinador:\n" + datos.getMensajeAdicional() + "\n\n" +
                "---------------------------------------------------\n" +
                "Atentamente,\n" +
                "ADICAM - Alianza para el Desarrollo Integral de la Cafeticultura en México";

        message.setText(cuerpo);
        mailSender.send(message);
    }
}
