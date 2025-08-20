package com.forohub.domain.topico;

import com.forohub.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    
    @Query("""
            SELECT t FROM Topico t 
            ORDER BY t.fechaCreacion DESC
            """)
    Page<Topico> findAllOrderByFechaCreacionDesc(Pageable pageable);
    
    Page<Topico> findByAutor(Usuario autor, Pageable pageable);
    
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
}
