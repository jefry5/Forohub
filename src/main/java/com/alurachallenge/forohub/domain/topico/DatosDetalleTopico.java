package com.alurachallenge.forohub.domain.topico;

import com.alurachallenge.forohub.domain.curso.DatosDetalleCurso;
import com.alurachallenge.forohub.domain.usuario.DatosDetalleAutor;
import com.alurachallenge.forohub.domain.usuario.DatosDetalleUsuario;

import java.time.LocalDateTime;

public record DatosDetalleTopico(
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        DatosDetalleAutor autor,
        DatosDetalleCurso curso,
        boolean estado
) {
    public DatosDetalleTopico(Topico topico) {
        this(topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                new DatosDetalleAutor(topico.getAutor().getNombre(),
                        topico.getAutor().getPerfil().getNombre()),
                new DatosDetalleCurso(topico.getCurso().getNombre(),
                        topico.getCurso().getCategoria()),
                topico.getStatus());
    }
}
