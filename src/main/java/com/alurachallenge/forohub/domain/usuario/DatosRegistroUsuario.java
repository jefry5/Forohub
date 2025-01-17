package com.alurachallenge.forohub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroUsuario(
        @NotBlank
        String nombre,

        @NotBlank
        @Email
        String correoElectronico,

        @NotBlank
        String contrasena,

        @NotNull
        Long perfilId
) {}
