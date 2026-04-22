package com.coordinacioncafesystem.Service;

import com.coordinacioncafesystem.Dto.DatosAceptacionDTO;
import com.coordinacioncafesystem.Entity.Inscripcion;
import com.coordinacioncafesystem.Repository.InscripcionRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InscripcionService {

    private final InscripcionRepository repository;
    private final JavaMailSender mailSender;

    public InscripcionService(InscripcionRepository repository, JavaMailSender mailSender) {
        this.repository = repository;
        this.mailSender = mailSender;
    }

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

    // ✅ BORRAR INSCRIPCIONES DE UNA CAPACITACIÓN (para luego borrar la capacitación)
    @Transactional
    public long eliminarPorCapacitacion(Long capacitacionId) {
        return repository.deleteByCapacitacionId(capacitacionId);
    }

    // Opcional: borrar una inscripción individual
    public void eliminarInscripcion(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("No existe la inscripción con ID: " + id);
        }
        repository.deleteById(id);
    }

    private void enviarCorreoConfirmacion(Inscripcion ins, DatosAceptacionDTO datos) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("adicamsistema@gmail.com");
        message.setTo(ins.getEmailAlumno());
        message.setSubject("¡Inscripción Aceptada! - ADICAM");

        String nombreCurso = (ins.getCapacitacion() != null) ? ins.getCapacitacion().getNombre() : "(curso)";
        String cuerpo = "Hola " + ins.getNombreAlumno() + ",\n\n" +
                "Tu solicitud para el curso: '" + nombreCurso + "' ha sido APROBADA.\n\n" +
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

