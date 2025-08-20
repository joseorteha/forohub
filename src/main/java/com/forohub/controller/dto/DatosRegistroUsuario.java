package com.forohub.controller.dto;

import com.forohub.domain.usuario.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DatosRegistroUsuario(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        
        @Email(message = "Formato de email inválido")
        @NotBlank(message = "El email es obligatorio")
        String email,
        
        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String contraseña,
        
        Rol rol
) {}
