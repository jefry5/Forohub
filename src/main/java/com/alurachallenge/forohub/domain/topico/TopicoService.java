package com.alurachallenge.forohub.domain.topico;

import com.alurachallenge.forohub.domain.curso.CursoRepository;
import com.alurachallenge.forohub.domain.topico.validaciones.ValidadorTopico;
import com.alurachallenge.forohub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private List<ValidadorTopico> validador;

    public Topico registrar(DatosRegistroTopico datos){
        //Extraemos la informacion del autor y curso
        var autor = usuarioRepository.getReferenceById(datos.autorId());
        var curso = cursoRepository.getReferenceById(datos.cursoId());

        //validamos nuestras reglas de negocio
        validador.forEach(v -> v.validar(datos));

        //Creamos nuestro topico con los datos extraidos
        return new Topico(datos, autor, curso);
    }
}
