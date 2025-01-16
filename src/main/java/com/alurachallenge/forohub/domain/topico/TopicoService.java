package com.alurachallenge.forohub.domain.topico;

import com.alurachallenge.forohub.domain.ValidacionException;
import com.alurachallenge.forohub.domain.curso.CursoRepository;
import com.alurachallenge.forohub.domain.topico.validaciones.ValidadorTopico;
import com.alurachallenge.forohub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Topico actualizar(Long id, DatosActualizarTopico datos){
        //Verificamos y obtenemos el topico (si existe el topico con la ID capturada)
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe topico!!"));

        //Verificamos y obtenemos el curso (si existe y se actualiza)
        var curso = Optional.ofNullable(datos.cursoId())
                .flatMap(cursoRepository::findById)
                .orElse(null);

        //Validamos las reglas de negocio
        validador.forEach(v -> v.validar(datos));

        /*Actualizamos según sea el caso
         * 1er caso: No se envía a actualizar el curso
         * 2do caso: Se envía a actualizar el curso
         * */
        if(curso == null)
            topico.actualizar(datos);
        else
            topico.actualizar(datos, curso);

        //Devolvemos el topico con los cambios realizados
        return topico;
    }
}
