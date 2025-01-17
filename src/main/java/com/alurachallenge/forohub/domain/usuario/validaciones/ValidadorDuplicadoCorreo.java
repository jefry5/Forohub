package com.alurachallenge.forohub.domain.usuario.validaciones;

import com.alurachallenge.forohub.domain.ValidacionException;
import com.alurachallenge.forohub.domain.usuario.DatosRegistroUsuario;
import com.alurachallenge.forohub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDuplicadoCorreo implements ValidadorUsuario{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(DatosRegistroUsuario datos) {
        //Verificamos si existe el correo en la BD
        boolean existeCorreo = usuarioRepository.existsByCorreoElectronico(datos.correoElectronico());

        //Si existe lanzamos una excepción
        if(existeCorreo)
            throw new ValidacionException("Correo existente!");
    }
}
