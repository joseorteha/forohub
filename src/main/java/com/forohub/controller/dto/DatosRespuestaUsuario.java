package com.forohub.controller.dto;

import com.forohub.domain.usuario.Rol;
import com.forohub.domain.usuario.Usuario;

public record DatosRespuestaUsuario(
        Long id,
        String nombre,
        String email,
        Rol rol
) {
    public DatosRespuestaUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getRol());
    }
}
