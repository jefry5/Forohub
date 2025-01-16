package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.domain.ValidacionException;
import com.alurachallenge.forohub.domain.curso.DatosDetalleCurso;
import com.alurachallenge.forohub.domain.topico.*;
import com.alurachallenge.forohub.domain.usuario.DatosDetalleUsuario;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
    public ResponseEntity<Page<DatosDetalleTopico>> listarTopicos(@PageableDefault(sort = "fechaCreacion",
                                                                                direction = Sort.Direction.DESC)
                                                                      Pageable paginacion){
        //Devolvemos los detalles del topico paginados
        return ResponseEntity.ok(topicoRepository.findByStatusTrue(paginacion)
                .map(DatosDetalleTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> obtenerTopico(@PathVariable Long id){
        //Verificamos el topico y si existe obtenemos los detalles del topico /{id}
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe el topico ../" + id));

        //Convertimos el topico a detalles de topico
        DatosDetalleTopico datosDetalleTopico = convertirTopicoATopicoDetalle(topico);

        //Devolvemos los detalles de topico y una respuesta HTTP 200
        return ResponseEntity.ok(datosDetalleTopico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetalleTopico> actualizarTopico(@PathVariable Long id,
                                                               @RequestBody DatosActualizarTopico datos){
        //Actualizamos el topico
        Topico topico = servicio.actualizar(id, datos);

        //Convertimos el topico a detalles de topico
        DatosDetalleTopico datosDetalleTopico = convertirTopicoATopicoDetalle(topico);

        //Devolvemos los detalles de topico y una respuesta HTTP 200
        return ResponseEntity.ok(datosDetalleTopico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        //Obtenemos el topico y lo eliminamos definitivamente de la BD
        boolean existeTopico = topicoRepository.existsById(id);
        if(existeTopico)
            topicoRepository.deleteById(id);
        else
            throw new ValidacionException("No existe topico!");

        return ResponseEntity.noContent().build();
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
                topico.getStatus()
        );
    }
}
