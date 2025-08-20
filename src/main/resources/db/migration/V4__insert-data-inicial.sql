-- Insertar usuarios de prueba
INSERT INTO usuarios (nombre, email, contraseña, rol) VALUES
('Administrador', 'admin@forohub.com', '$2a$10$Y50UaMFOxteibQEYLrwuAuoC..8QQTXGmfXEZWnPL2MpxpzQUTEKq', 'ADMIN'),
('Juan Pérez', 'juan@email.com', '$2a$10$Y50UaMFOxteibQEYLrwuAuoC..8QQTXGmfXEZWnPL2MpxpzQUTEKq', 'USER'),
('María García', 'maria@email.com', '$2a$10$Y50UaMFOxteibQEYLrwuAuoC..8QQTXGmfXEZWnPL2MpxpzQUTEKq', 'USER');

-- Insertar tópicos de prueba
INSERT INTO topicos (titulo, mensaje, fecha_creacion, estado, autor_id, curso) VALUES
('¿Cómo instalar Spring Boot?', 'Tengo problemas para configurar un proyecto con Spring Boot desde cero. ¿Alguien me puede ayudar?', NOW(), 'ABIERTO', 2, 'Spring Framework'),
('Dudas sobre JPA', '¿Cuál es la diferencia entre @Entity y @Table en JPA?', NOW(), 'ABIERTO', 3, 'Java Persistence API'),
('Configuración de base de datos', 'No puedo conectar mi aplicación Spring Boot con MySQL', NOW(), 'ABIERTO', 2, 'Spring Boot');

-- Insertar respuestas de prueba
INSERT INTO respuestas (mensaje, fecha_creacion, autor_id, topico_id) VALUES
('Puedes usar Spring Initializr en https://start.spring.io/ para generar tu proyecto fácilmente', NOW(), 1, 1),
('@Entity marca la clase como entidad JPA, @Table especifica el nombre de la tabla en la base de datos', NOW(), 1, 2),
('Revisa tu archivo application.properties, asegúrate de tener las credenciales correctas', NOW(), 3, 3);
