package com.alurachallenge.forohub.domain.perfil;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record DatosRegistroPerfil(
        @NotBlank
        @NotEmpty
        String nombre
) {}
