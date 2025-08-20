package com.forohub.controller.dto;

import com.forohub.domain.respuesta.Respuesta;

import java.time.LocalDateTime;

public record DatosRespuestaRespuesta(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        String nombreAutor,
        Long topicoId,
        String tituloTopico
) {
    public DatosRespuestaRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getAutor().getNombre(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo()
        );
    }
}
