package com.forohub.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario(
        @Email(message = "Formato de email inválido")
        @NotBlank(message = "El email es obligatorio")
        String email,
        
        @NotBlank(message = "La contraseña es obligatoria")
        String contraseña
) {}
