package com.alurachallenge.forohub.domain.topico.validaciones;

import com.alurachallenge.forohub.domain.ValidacionException;
import com.alurachallenge.forohub.domain.curso.CursoRepository;
import com.alurachallenge.forohub.domain.topico.DatosRegistroTopico;
import com.alurachallenge.forohub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorExistenAutorYCurso implements ValidadorTopico{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public void validar(DatosRegistroTopico datos) {
        boolean existeAutor = usuarioRepository.existsById(datos.autorId());
        boolean existeCurso = cursoRepository.existsById(datos.cursoId());

        if(!existeAutor || !existeCurso)
            throw new ValidacionException("Autor y/o Curso invalidos!!");
    }
}
