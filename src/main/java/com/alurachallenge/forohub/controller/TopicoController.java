package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.domain.ValidacionException;
import com.alurachallenge.forohub.domain.curso.DatosDetalleCurso;
import com.alurachallenge.forohub.domain.topico.*;
import com.alurachallenge.forohub.domain.usuario.DatosDetalleUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService servicio;

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<DatosDetalleTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datos,
                                                              UriComponentsBuilder uriComponentsBuilder){
        //Obtenemos y validamos el topico y luego lo guardamos en la BD
        Topico topico = servicio.registrar(datos);
        topicoRepository.save(topico);

        //Convertimos el topico a detalles de topico
        DatosDetalleTopico datosDetalleTopico = convertirTopicoATopicoDetalle(topico);

        //Obtenemos la URI del topico creado
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        //Devolvemos los detalles de topico y una respuesta HTTP 200
        return ResponseEntity.created(uri).body(datosDetalleTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleTopico>> listarTopicos(Pageable paginacion){
        return ResponseEntity.ok(topicoRepository.findByStatusTrue(paginacion)
                .map(DatosDetalleTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> obtenerTopico(@PathVariable Long id){
        //verificamos si el id esta en blanco o si existe en la BD
        var topicoOptional = topicoRepository.findById(id);
        if(topicoOptional.isEmpty())
            throw new ValidacionException("No existe el topico ../{" + id + "}!");

        //Obtenemos los detalles del topico /{id}
        Topico topico = topicoOptional.get();

        //Convertimos el topico a detalles de topico
        DatosDetalleTopico datosDetalleTopico = convertirTopicoATopicoDetalle(topico);

        //Devolvemos los detalles de topico y una respuesta HTTP 200
        return ResponseEntity.ok(datosDetalleTopico);
    }


    //Convierte el topico enviado a detalles de topico
    private DatosDetalleTopico convertirTopicoATopicoDetalle(Topico topico){
        return new DatosDetalleTopico(
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                new DatosDetalleUsuario(
                        topico.getAutor().getNombre(),
                        topico.getAutor().getPerfil().getNombre()
                ),
                new DatosDetalleCurso(
                        topico.getCurso().getNombre(),
                        topico.getCurso().getCategoria()
                ),
                topico.isStatus()
        );
    }
}
