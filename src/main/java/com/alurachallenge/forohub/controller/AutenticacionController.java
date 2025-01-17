package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.domain.ValidacionException;
import com.alurachallenge.forohub.domain.usuario.*;
import com.alurachallenge.forohub.infra.security.DatosJWTToken;
import com.alurachallenge.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    private final String ROLE_ADMINISTRADOR = "hasRole('ADMINISTRADOR')";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService servicio;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/autenticar")
    public ResponseEntity<DatosJWTToken> autenticarUsuario(@RequestBody @Valid DatosAutenticarUsuario datos){
        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.correo(),
                datos.contrasena());
        Usuario usuario = (Usuario) authenticationManager.authenticate(authToken).getPrincipal();

        String tokenJWT = tokenService.generarToken(usuario);
        return ResponseEntity.ok(new DatosJWTToken(tokenJWT));
    }

    @PreAuthorize(ROLE_ADMINISTRADOR)
    @PostMapping("/registrar")
    public ResponseEntity<DatosDetalleUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datos,
                                                                UriComponentsBuilder uriComponentsBuilder){
        //Obtenemos y guardamos al usuario despues de validar las reglas de negocio
        Usuario usuario = servicio.registrar(datos);
        usuarioRepository.save(usuario);

        //Convertimos el usuario a detalles de usuario
        DatosDetalleUsuario datosDetalleUsuario = convertirUsuarioADetalleUsuario(usuario);

        //Creamos la URI que nos dirige al usuario creado
        URI uri = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();

        //Devolvemos una respuesta HTTP 201
        return ResponseEntity.created(uri).body(datosDetalleUsuario);
    }

    @PreAuthorize(ROLE_ADMINISTRADOR)
    @GetMapping("/usuario/{id}")
    public ResponseEntity<DatosDetalleUsuario> obtenerUsuario(@PathVariable Long id){
        //Verificamos el usuario y si existe obtenemos los detalles del usuario /{id}
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe el usuario ../" + id));

        //Convertimos el usuario a detalles de usuario
        DatosDetalleUsuario datosDetalleUsuario = convertirUsuarioADetalleUsuario(usuario);

        //Devolvemos los detalles del usuario y una respuesta HTTP 200
        return ResponseEntity.ok(datosDetalleUsuario);
    }

    private DatosDetalleUsuario convertirUsuarioADetalleUsuario(Usuario usuario){
        return new DatosDetalleUsuario(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoElectronico(),
                usuario.getPerfil().getNombre()
        );
    }
}
