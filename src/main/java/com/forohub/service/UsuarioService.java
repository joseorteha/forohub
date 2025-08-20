package com.forohub.service;

import com.forohub.controller.dto.DatosRegistroUsuario;
import com.forohub.domain.usuario.Rol;
import com.forohub.domain.usuario.Usuario;
import com.forohub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(DatosRegistroUsuario datos) {
        if (usuarioRepository.existsByEmail(datos.email())) {
            throw new RuntimeException("Ya existe un usuario con este email");
        }

        var usuario = new Usuario(
                datos.nombre(),
                datos.email(),
                passwordEncoder.encode(datos.contraseña()),
                datos.rol() != null ? datos.rol() : Rol.USER
        );

        return usuarioRepository.save(usuario);
    }
}
