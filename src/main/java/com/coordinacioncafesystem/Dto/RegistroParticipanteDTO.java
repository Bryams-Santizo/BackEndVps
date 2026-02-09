package com.coordinacioncafesystem.Dto;

import com.coordinacioncafesystem.enums.TipoParticipante;

public record RegistroParticipanteDTO(
        String nombre,
        TipoParticipante tipo,
        String correo,
        String telefono,
        String password,
        String estado,
        String ciudad,
        Long rolId
) {}