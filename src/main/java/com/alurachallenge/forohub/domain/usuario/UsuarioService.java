package com.alurachallenge.forohub.domain.usuario;

import com.alurachallenge.forohub.domain.perfil.PerfilRepository;
import com.alurachallenge.forohub.domain.usuario.validaciones.ValidadorUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private List<ValidadorUsuario> validador;

    @Autowired
    private PerfilRepository perfilRepository;

    public Usuario registrar(DatosRegistroUsuario datos){
        //Validamos las reglas de negocio
        validador.forEach(v -> v.validar(datos));

        //Obtenemos el perfil relacionado a la ID
        var perfil = perfilRepository.getReferenceById(datos.perfilId());

        //Encriptamos la contrasena
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var password = passwordEncoder.encode(datos.contrasena());

        //Devolvemos el usuario creado
        return new Usuario(datos, perfil, password);
    }
}
