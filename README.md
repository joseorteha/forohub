# ForoHub - API REST

## 📖 Descripción

ForoHub es una API REST completa desarrollada con Spring Boot 3 y Java 17 para gestionar un foro de discusión. Permite a los usuarios registrarse, autenticarse, crear tópicos y responder a ellos con un sistema de seguridad robusto basado en JWT.

## 🚀 Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** (con JWT)
- **Spring Data JPA**
- **MariaDB/MySQL**
- **Flyway** (migraciones de base de datos)
- **Swagger/OpenAPI** (documentación)
- **Maven** (gestión de dependencias)
- **JUnit & Mockito** (testing)

## 📋 Características

### ✅ Autenticación y Seguridad
- Registro de usuarios con validaciones
- Login con JWT token
- Autorización basada en roles (ADMIN, USER)
- Encriptación de contraseñas con BCrypt

### ✅ Gestión de Tópicos
- Crear nuevos tópicos (requiere autenticación)
- Listar todos los tópicos (público)
- Ver detalles de un tópico específico (público)
- Actualizar tópicos (solo autor o admin)
- Eliminar tópicos (solo autor o admin)

### ✅ Gestión de Respuestas
- Crear respuestas a tópicos (requiere autenticación)
- Listar respuestas por tópico (público)

### ✅ Extras
- Manejo centralizado de errores
- Documentación interactiva con Swagger
- Validaciones robustas
- Datos de prueba incluidos

## 🛠️ Configuración del Proyecto

### Prerrequisitos
- Java 17 o superior
- Maven 3.6+
- MariaDB/MySQL 8.0+

### Base de Datos

1. Crear la base de datos:
```sql
CREATE DATABASE forohub;
CREATE USER 'forohub_user'@'localhost' IDENTIFIED BY 'forohub_password';
GRANT ALL PRIVILEGES ON forohub.* TO 'forohub_user'@'localhost';
FLUSH PRIVILEGES;
```

2. Configurar las credenciales en `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mariadb://localhost:3306/forohub
    username: forohub_user
    password: forohub_password
```

### Instalación

1. Clonar el repositorio:
```bash
git clone <repository-url>
cd ForoHub
```

2. Ejecutar el proyecto:
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 📚 Documentación de la API

### Swagger UI
Accede a la documentación interactiva en: `http://localhost:8080/swagger-ui.html`

### Endpoints Principales

#### Autenticación
- `POST /auth/registro` - Registrar nuevo usuario
- `POST /auth/login` - Autenticar usuario

#### Tópicos
- `GET /topicos` - Listar todos los tópicos
- `GET /topicos/{id}` - Obtener tópico específico
- `POST /topicos` - Crear nuevo tópico ⚠️ Requiere autenticación
- `PUT /topicos/{id}` - Actualizar tópico ⚠️ Requiere autenticación
- `DELETE /topicos/{id}` - Eliminar tópico ⚠️ Requiere autenticación

#### Respuestas
- `POST /respuestas` - Crear respuesta ⚠️ Requiere autenticación
- `GET /respuestas/topico/{topicoId}` - Listar respuestas por tópico

## 🔐 Autenticación

Para endpoints protegidos, incluir el header:
```
Authorization: Bearer <JWT_TOKEN>
```

### Usuarios de Prueba

El sistema incluye usuarios predefinidos para testing:

| Email | Contraseña | Rol |
|-------|------------|-----|
| admin@forohub.com | password123 | ADMIN |
| juan@email.com | password123 | USER |
| maria@email.com | password123 | USER |

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/forohub/
│   │   ├── controller/          # Controladores REST
│   │   ├── domain/              # Entidades y repositorios
│   │   │   ├── usuario/
│   │   │   ├── topico/
│   │   │   └── respuesta/
│   │   ├── service/             # Lógica de negocio
│   │   ├── infra/               # Configuraciones
│   │   │   ├── security/        # Seguridad y JWT
│   │   │   ├── errores/         # Manejo de errores
│   │   │   └── swagger/         # Configuración Swagger
│   │   └── ForoHubApplication.java
│   └── resources/
│       ├── db/migration/        # Scripts Flyway
│       └── application.yml      # Configuración
└── test/                        # Tests unitarios
```

## 🧪 Testing

Ejecutar los tests:
```bash
mvn test
```

## 📦 Build y Deploy

Generar JAR:
```bash
mvn clean package
```

Ejecutar JAR:
```bash
java -jar target/foro-hub-0.0.1-SNAPSHOT.jar
```

## 🔒 Seguridad

- Contraseñas encriptadas con BCrypt
- Tokens JWT con expiración de 2 horas
- Validación de permisos por endpoint
- Protección CSRF deshabilitada para API REST

## 📝 Variables de Entorno

Para producción, configurar:
```bash
export JWT_SECRET=your-super-secret-jwt-key-here
export DB_URL=jdbc:mariadb://your-db-host:3306/forohub
export DB_USERNAME=your-db-user
export DB_PASSWORD=your-db-password
```

## 🤝 Contribución

1. Fork del proyecto
2. Crear feature branch (`git checkout -b feature/nueva-caracteristica`)
3. Commit de cambios (`git commit -am 'Agregar nueva característica'`)
4. Push del branch (`git push origin feature/nueva-caracteristica`)
5. Crear Pull Request

## 📄 Licencia

Este proyecto está bajo la licencia Apache 2.0. Ver el archivo `LICENSE` para más detalles.

## 👥 Autor

Desarrollado como parte del challenge Alura - ONE
