package com.alurachallenge.forohub.domain.usuario;

import com.alurachallenge.forohub.domain.perfil.DatosRegistroPerfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DatosAutenticarUsuario(
        @NotBlank
        @NotEmpty
        String nombre,

        @NotBlank
        @NotEmpty
        String contrasena
) {}
