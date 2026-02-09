package com.coordinacioncafesystem.Dto;

import java.util.Date;

public record ParticipanteDTO(
        Long id,
        String nombre,
        String tipo,
        String correo,
        String telefono,
        String estado, // 🚩 AÑADIDO
        Long tecnologicoId,
        String tecnologicoNombre // 🚩 Esto facilitará mostrar el nombre en el front

) {}