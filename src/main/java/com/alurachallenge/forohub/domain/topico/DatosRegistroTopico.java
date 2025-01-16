package com.alurachallenge.forohub.domain.topico;

import com.alurachallenge.forohub.domain.curso.DatosDetalleCurso;
import com.alurachallenge.forohub.domain.usuario.DatosDetalleUsuario;
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
        DatosDetalleUsuario autor,

        @NotNull
        DatosDetalleCurso curso
) {}
