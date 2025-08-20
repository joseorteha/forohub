package com.forohub.controller;

import com.forohub.controller.dto.DatosRegistroRespuesta;
import com.forohub.controller.dto.DatosRespuestaRespuesta;
import com.forohub.domain.usuario.Usuario;
import com.forohub.service.RespuestaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/respuestas")
@Tag(name = "Respuestas", description = "Endpoints para gestión de respuestas a tópicos")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @PostMapping
    @Operation(summary = "Crear respuesta", description = "Crea una nueva respuesta a un tópico")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<DatosRespuestaRespuesta> crearRespuesta(
            @RequestBody @Valid DatosRegistroRespuesta datos,
            @AuthenticationPrincipal Usuario usuario,
            UriComponentsBuilder uriComponentsBuilder) {
        var respuesta = respuestaService.crearRespuesta(datos, usuario);
        var uri = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosRespuestaRespuesta(respuesta));
    }

    @GetMapping("/topico/{topicoId}")
    @Operation(summary = "Listar respuestas por tópico", description = "Lista todas las respuestas de un tópico específico")
    public ResponseEntity<Page<DatosRespuestaRespuesta>> listarRespuestasPorTopico(
            @PathVariable Long topicoId,
            @PageableDefault(size = 10) Pageable paginacion) {
        var respuestas = respuestaService.listarRespuestasPorTopico(topicoId, paginacion);
        return ResponseEntity.ok(respuestas.map(DatosRespuestaRespuesta::new));
    }
}
