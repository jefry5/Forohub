package com.alurachallenge.forohub.domain.topico.validaciones;

import com.alurachallenge.forohub.domain.topico.DatosActualizarTopico;
import com.alurachallenge.forohub.domain.topico.DatosRegistroTopico;

public interface ValidadorTopico {
    void validar(DatosRegistroTopico datos);
    void validar(DatosActualizarTopico datos);
}
