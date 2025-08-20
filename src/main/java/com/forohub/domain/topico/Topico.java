package com.forohub.domain.topico;

import com.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "El mensaje es obligatorio")
    @Column(columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)")
    private EstadoTopico estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @NotBlank(message = "El curso es obligatorio")
    private String curso;

    // Constructores
    public Topico() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoTopico.ABIERTO;
    }

    public Topico(String titulo, String mensaje, Usuario autor, String curso) {
        this();
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.autor = autor;
        this.curso = curso;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstadoTopico getEstado() {
        return estado;
    }

    public void setEstado(EstadoTopico estado) {
        this.estado = estado;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void actualizar(String titulo, String mensaje, String curso) {
        if (titulo != null) {
            this.titulo = titulo;
        }
        if (mensaje != null) {
            this.mensaje = mensaje;
        }
        if (curso != null) {
            this.curso = curso;
        }
    }

    public void cerrar() {
        this.estado = EstadoTopico.CERRADO;
    }
}
