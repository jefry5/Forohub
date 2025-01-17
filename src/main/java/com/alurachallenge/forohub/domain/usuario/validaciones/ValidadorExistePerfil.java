package com.alurachallenge.forohub.domain.usuario.validaciones;

import com.alurachallenge.forohub.domain.ValidacionException;
import com.alurachallenge.forohub.domain.perfil.PerfilRepository;
import com.alurachallenge.forohub.domain.usuario.DatosRegistroUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorExistePerfil implements ValidadorUsuario{

    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public void validar(DatosRegistroUsuario datos) {
        //Verificamos si el perfil existe en la BD
        boolean existePerfil = perfilRepository.existsById(datos.perfilId());

        //Si no existe lanzamos una excepcion
        if(!existePerfil)
            throw new ValidacionException("Perfil no existe!!");
    }
}
