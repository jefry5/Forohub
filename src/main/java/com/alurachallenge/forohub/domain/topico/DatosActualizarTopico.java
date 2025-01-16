package com.alurachallenge.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record DatosActualizarTopico(
        String titulo,

        String mensaje,

        Long cursoId,

        Boolean status
) {}
