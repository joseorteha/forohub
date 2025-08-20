package com.forohub.service;

import com.forohub.controller.dto.DatosActualizarTopico;
import com.forohub.controller.dto.DatosRegistroTopico;
import com.forohub.domain.topico.Topico;
import com.forohub.domain.topico.TopicoRepository;
import com.forohub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    public Topico crearTopico(DatosRegistroTopico datos, Usuario autor) {
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new RuntimeException("Ya existe un tópico con el mismo título y mensaje");
        }

        var topico = new Topico(datos.titulo(), datos.mensaje(), autor, datos.curso());
        return topicoRepository.save(topico);
    }

    public Page<Topico> listarTopicos(Pageable paginacion) {
        return topicoRepository.findAllOrderByFechaCreacionDesc(paginacion);
    }

    public Topico obtenerTopicoPorId(Long id) {
        return topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));
    }

    public Topico actualizarTopico(Long id, DatosActualizarTopico datos, Usuario usuario) {
        var topico = obtenerTopicoPorId(id);
        validarPermisos(topico, usuario);
        topico.actualizar(datos.titulo(), datos.mensaje(), datos.curso());
        return topicoRepository.save(topico);
    }

    public void eliminarTopico(Long id, Usuario usuario) {
        var topico = obtenerTopicoPorId(id);
        validarPermisos(topico, usuario);
        topicoRepository.delete(topico);
    }

    private void validarPermisos(Topico topico, Usuario usuario) {
        if (!topico.getAutor().getId().equals(usuario.getId()) && 
            !usuario.getRol().name().equals("ADMIN")) {
            throw new RuntimeException("No tienes permisos para realizar esta acción");
        }
    }
}
