package com.forohub.controller;

import com.forohub.controller.dto.DatosActualizarTopico;
import com.forohub.controller.dto.DatosRegistroTopico;
import com.forohub.controller.dto.DatosRespuestaTopico;
import com.forohub.controller.dto.DatosRespuestaRespuesta;
import com.forohub.domain.usuario.Usuario;
import com.forohub.service.TopicoService;
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
@RequestMapping("/topicos")
@Tag(name = "Tópicos", description = "Endpoints para gestión de tópicos del foro")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private RespuestaService respuestaService;

    @PostMapping
    @Operation(summary = "Crear tópico", description = "Crea un nuevo tópico en el foro")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<DatosRespuestaTopico> crearTopico(
            @RequestBody @Valid DatosRegistroTopico datos,
            @AuthenticationPrincipal Usuario usuario,
            UriComponentsBuilder uriComponentsBuilder) {
        var topico = topicoService.crearTopico(datos, usuario);
        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosRespuestaTopico(topico));
    }

    @GetMapping
    @Operation(summary = "Listar tópicos", description = "Lista todos los tópicos ordenados por fecha de creación")
    public ResponseEntity<Page<DatosRespuestaTopico>> listarTopicos(
            @PageableDefault(size = 10) Pageable paginacion) {
        var topicos = topicoService.listarTopicos(paginacion);
        return ResponseEntity.ok(topicos.map(DatosRespuestaTopico::new));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tópico", description = "Obtiene un tópico específico por su ID")
    public ResponseEntity<DatosRespuestaTopico> obtenerTopico(@PathVariable Long id) {
        var topico = topicoService.obtenerTopicoPorId(id);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tópico", description = "Actualiza un tópico existente")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizarTopico datos,
            @AuthenticationPrincipal Usuario usuario) {
        var topico = topicoService.actualizarTopico(id, datos, usuario);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tópico", description = "Elimina un tópico existente")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> eliminarTopico(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario) {
        topicoService.eliminarTopico(id, usuario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/respuestas")
    @Operation(summary = "Listar respuestas por tópico", description = "Lista todas las respuestas de un tópico específico")
    public ResponseEntity<Page<DatosRespuestaRespuesta>> listarRespuestasPorTopico(
            @PathVariable Long id,
            @PageableDefault(size = 10) Pageable paginacion) {
        var respuestas = respuestaService.listarRespuestasPorTopico(id, paginacion);
        return ResponseEntity.ok(respuestas.map(DatosRespuestaRespuesta::new));
    }
}
