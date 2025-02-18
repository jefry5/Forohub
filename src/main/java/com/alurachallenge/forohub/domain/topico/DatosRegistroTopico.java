package com.alurachallenge.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotBlank
        @NotEmpty
        String titulo,

        @NotBlank
        @NotEmpty
        String mensaje,

        @NotNull
        Long autorId,

        @NotNull
        Long cursoId
) {}
