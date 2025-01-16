package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.domain.usuario.DatosAutenticarUsuario;
import com.alurachallenge.forohub.domain.usuario.Usuario;
import com.alurachallenge.forohub.infra.security.DatosJWTToken;
import com.alurachallenge.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DatosJWTToken> autenticarUsuario(@RequestBody @Valid DatosAutenticarUsuario datos){
        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.correo(),
                datos.contrasena());
        Usuario usuario = (Usuario) authenticationManager.authenticate(authToken).getPrincipal();

        String tokenJWT = tokenService.generarToken(usuario);
        return ResponseEntity.ok(new DatosJWTToken(tokenJWT));
    }

}
