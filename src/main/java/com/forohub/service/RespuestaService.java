package com.forohub.service;

import com.forohub.controller.dto.DatosRegistroRespuesta;
import com.forohub.domain.respuesta.Respuesta;
import com.forohub.domain.respuesta.RespuestaRepository;
import com.forohub.domain.topico.TopicoRepository;
import com.forohub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    public Respuesta crearRespuesta(DatosRegistroRespuesta datos, Usuario autor) {
        var topico = topicoRepository.findById(datos.topicoId())
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));

        var respuesta = new Respuesta(datos.mensaje(), autor, topico);
        return respuestaRepository.save(respuesta);
    }

    public Page<Respuesta> listarRespuestasPorTopico(Long topicoId, Pageable paginacion) {
        var topico = topicoRepository.findById(topicoId)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));
        return respuestaRepository.findByTopicoOrderByFechaCreacionAsc(topico, paginacion);
    }
}
