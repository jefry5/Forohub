package com.alurachallenge.forohub.domain.topico.validaciones;

import com.alurachallenge.forohub.domain.ValidacionException;
import com.alurachallenge.forohub.domain.topico.DatosActualizarTopico;
import com.alurachallenge.forohub.domain.topico.DatosRegistroTopico;
import com.alurachallenge.forohub.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDuplicacionDeTopico implements ValidadorTopico{

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DatosRegistroTopico datos) {
        verificar(datos.titulo(), datos.mensaje());
    }

    @Override
    public void validar(DatosActualizarTopico datos) {
        verificar(datos.titulo(), datos.mensaje());
    }

    private void verificar(String titulo, String mensaje){
        System.out.println("Titulo: " + titulo + " ,mensaje: " + mensaje);
        boolean mismoTituloYMensaje = topicoRepository.existsByTituloAndMensaje(titulo,
                mensaje);

        if(mismoTituloYMensaje)
            throw new ValidacionException("Ya existe un topico con el mismo asunto!!!");
    }
}
