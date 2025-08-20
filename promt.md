📝 Prompt — Backend Foro Hub (Spring Boot + MariaDB)

Quiero que desarrolles un backend completo en Java 17 con Spring Boot 3 para un proyecto llamado Foro Hub, basado en el challenge descrito.

🎯 Objetivo

Construir una API REST segura y documentada para gestionar usuarios, tópicos y respuestas en un foro.

🔹 Requisitos técnicos

Framework: Spring Boot 3

Lenguaje: Java 17

Base de datos: MariaDB (puede usarse MySQL compatible)

Dependencias necesarias:

Spring Web

Spring Data JPA

Spring Security

JWT (Json Web Token)

Validation (Jakarta Validation)

Swagger/OpenAPI para documentación

Flyway (opcional, para migraciones)

🔹 Entidades del sistema

Usuario

id (Long, autogenerado)

nombre (String, obligatorio)

email (String, único, validado)

contraseña (String, encriptada con BCrypt)

rol (enum: ADMIN, USER)

Tópico

id (Long, autogenerado)

título (String, obligatorio)

mensaje (String, obligatorio)

fechaCreacion (LocalDateTime)

estado (enum: ABIERTO, CERRADO)

autor (Usuario)

curso (String, obligatorio)

Respuesta

id (Long, autogenerado)

mensaje (String, obligatorio)

fechaCreacion (LocalDateTime)

autor (Usuario)

tópico (Tópico)

🔹 Funcionalidades requeridas
✅ Autenticación y seguridad

Registro de usuarios.

Login con email + contraseña que devuelve JWT.

Seguridad:

Solo usuarios autenticados pueden crear, actualizar o eliminar tópicos/respuestas.

Los endpoints públicos permiten solo listar.

Roles: ADMIN puede eliminar cualquier recurso, USER solo los suyos.

✅ Gestión de tópicos

GET /topicos → Listar todos.

GET /topicos/{id} → Mostrar uno.

POST /topicos → Crear nuevo (requiere autenticación).

PUT /topicos/{id} → Actualizar (solo autor o admin).

DELETE /topicos/{id} → Eliminar (solo autor o admin).

✅ Gestión de respuestas

POST /respuestas → Crear una respuesta vinculada a un tópico.

GET /topicos/{id}/respuestas → Listar respuestas de un tópico.

✅ Validaciones

Emails únicos en usuarios.

Contraseñas mín. 8 caracteres.

Campos requeridos no vacíos.

Devolver status codes correctos (201 Created, 200 OK, 400 Bad Request, 403 Forbidden, etc.).

✅ Extras opcionales

Manejo centralizado de errores con @ControllerAdvice.

Tests unitarios con JUnit + Mockito.

Datos iniciales de prueba (usuarios, tópicos).

Documentación con Swagger accesible en /swagger-ui.

🔹 Buenas prácticas

Arquitectura en capas (Controller, Service, Repository).

Uso de DTOs para requests/responses (no exponer entidades directamente).

Token JWT como Bearer token en headers.

Uso de Trello/kanban para gestionar tareas.

⚡ Con todo esto, quiero que me generes el código necesario para un backend completo, seguro, documentado y funcional de un foro estilo Alura, siguiendo las mejores prácticas de Spring Boot y REST.