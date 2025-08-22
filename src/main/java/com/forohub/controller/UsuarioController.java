package com.forohub.controller;

import com.forohub.domain.usuario.DatosRegistroUsuario;
import com.forohub.domain.usuario.Usuario;
import com.forohub.domain.usuario.UsuarioRepository;
import com.forohub.domain.usuario.Rol;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<String> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario,
                                         UriComponentsBuilder uriComponentsBuilder) {
        
        // Verificar si el email ya existe
        if (usuarioRepository.findUsuarioByEmail(datosRegistroUsuario.email()).isPresent()) {
            return ResponseEntity.badRequest().body("El email ya está registrado");
        }

        // Crear el usuario con la contraseña encriptada y rol por defecto
        var usuario = new Usuario(
                datosRegistroUsuario.nombre(),
                datosRegistroUsuario.email(),
                passwordEncoder.encode(datosRegistroUsuario.password()),
                Rol.USER
        );

        usuarioRepository.save(usuario);

        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(url).body("Usuario creado exitosamente");
    }
}
