package com.forohub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forohub.controller.dto.DatosAutenticacionUsuario;
import com.forohub.controller.dto.DatosRegistroUsuario;
import com.forohub.domain.usuario.Rol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class AutenticacionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Debería registrar un usuario correctamente")
    void testRegistroUsuarioExitoso() throws Exception {
        var datosRegistro = new DatosRegistroUsuario(
                "Test User",
                "test@example.com",
                "password123",
                Rol.USER
        );

        mvc.perform(post("/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datosRegistro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.rol").value("USER"));
    }

    @Test
    @DisplayName("Debería fallar con email inválido")
    void testRegistroEmailInvalido() throws Exception {
        var datosRegistro = new DatosRegistroUsuario(
                "Test User",
                "email-invalido",
                "password123",
                Rol.USER
        );

        mvc.perform(post("/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datosRegistro)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debería fallar con contraseña corta")
    void testRegistroContraseñaCorta() throws Exception {
        var datosRegistro = new DatosRegistroUsuario(
                "Test User",
                "test@example.com",
                "123",
                Rol.USER
        );

        mvc.perform(post("/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datosRegistro)))
                .andExpect(status().isBadRequest());
    }
}
