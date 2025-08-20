package com.forohub.domain.respuesta;

import com.forohub.domain.topico.Topico;
import com.forohub.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    
    @Query("""
            SELECT r FROM Respuesta r 
            WHERE r.topico = :topico 
            ORDER BY r.fechaCreacion ASC
            """)
    Page<Respuesta> findByTopicoOrderByFechaCreacionAsc(Topico topico, Pageable pageable);
    
    Page<Respuesta> findByAutor(Usuario autor, Pageable pageable);
}
