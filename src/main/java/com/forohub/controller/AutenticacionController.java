package com.forohub.controller;

import com.forohub.controller.dto.DatosAutenticacionUsuario;
import com.forohub.controller.dto.DatosJWTToken;
import com.forohub.controller.dto.DatosRegistroUsuario;
import com.forohub.controller.dto.DatosRespuestaUsuario;
import com.forohub.domain.usuario.Usuario;
import com.forohub.infra.security.TokenService;
import com.forohub.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para registro y autenticación de usuarios")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuario", description = "Autentica un usuario y devuelve un JWT token")
    public ResponseEntity<DatosJWTToken> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        var authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.email(),
                datosAutenticacionUsuario.contraseña());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken(((Usuario) usuarioAutenticado.getPrincipal()).getEmail());
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }

    @PostMapping("/registro")
    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en el sistema")
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario) {
        var usuario = usuarioService.registrarUsuario(datosRegistroUsuario);
        return ResponseEntity.ok(new DatosRespuestaUsuario(usuario));
    }
}
