# 🗣️ ForoHub - API REST para Gestión de Foros

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![JWT](https://img.shields.io/badge/JWT-Auth-red)
![Maven](https://img.shields.io/badge/Maven-3.9-yellow)

## � Descripción

ForoHub es una API REST completa desarrollada con Spring Boot que permite gestionar un foro de discusiones online. La aplicación implementa un sistema de autenticación JWT robusto y proporciona operaciones CRUD completas para usuarios, tópicos y respuestas.

## 🏗️ Arquitectura

El proyecto sigue los principios de **Clean Architecture** y está estructurado en las siguientes capas:

```
src/main/java/com/forohub/
├── controller/          # Controladores REST
├── domain/             # Entidades de dominio
│   ├── usuario/        # Gestión de usuarios
│   ├── topico/         # Gestión de tópicos
│   └── respuesta/      # Gestión de respuestas
├── infra/              # Infraestructura
│   ├── security/       # Configuración de seguridad JWT
│   └── springdoc/      # Configuración de documentación
└── ForoHubApplication.java
```

## 🚀 Características Principales

### 🔐 Autenticación y Autorización
- **JWT (JSON Web Tokens)** para autenticación stateless
- **BCrypt** para hash seguro de contraseñas
- **Spring Security** para control de acceso
- Roles de usuario (ADMIN, USER)

### 📝 Gestión de Contenido
- **Tópicos**: Crear, leer, actualizar y eliminar temas de discusión
- **Respuestas**: Sistema completo de respuestas a tópicos
- **Usuarios**: Registro y gestión de perfiles de usuario
- **Paginación** en todas las consultas de listado

### 📊 Base de Datos
- **Migraciones automáticas** con Flyway
- **Modelo relacional** optimizado
- **Datos de prueba** incluidos para testing

### 📖 Documentación
- **Swagger UI** integrado para testing interactivo
- **OpenAPI 3.0** specification
- **Colección de Postman** incluida

## 🛠️ Tecnologías Utilizadas

| Categoría | Tecnología | Versión |
|-----------|------------|---------|
| **Runtime** | Java | 17 |
| **Framework** | Spring Boot | 3.2.0 |
| **Seguridad** | Spring Security | 6.1.5 |
| **Base de Datos** | MySQL/MariaDB | 10.4+ |
| **ORM** | Spring Data JPA | 3.1.5 |
| **Migraciones** | Flyway | 9.22.3 |
| **Documentación** | SpringDoc OpenAPI | 2.2.0 |
| **Build Tool** | Maven | 3.9+ |
| **Autenticación** | JWT | - |

## ⚡ Instalación y Configuración

### Prerrequisitos

- **Java 17** o superior
- **Maven 3.9** o superior
- **MySQL 8.0** o **MariaDB 10.4** o superior

### 1. Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/forohub.git
cd forohub
```

### 2. Configurar Base de Datos

```sql
-- Crear la base de datos
CREATE DATABASE forohub;

-- Crear usuario (opcional)
CREATE USER 'forohub_user'@'localhost' IDENTIFIED BY 'tu_password';
GRANT ALL PRIVILEGES ON forohub.* TO 'forohub_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configurar Variables de Entorno

Crear archivo `.env` o configurar las siguientes variables:

```properties
# Base de datos
DB_HOST=localhost
DB_PORT=3306
DB_NAME=forohub
DB_USERNAME=ortega
DB_PASSWORD=ortega2025

# JWT
JWT_SECRET=tu-secreto-jwt-muy-seguro-de-al-menos-256-bits
```

### 4. Ejecutar la Aplicación

```bash
# Compilar y ejecutar
mvn spring-boot:run

# O usando el script incluido (Windows)
.\run.bat
```

La aplicación estará disponible en: `http://localhost:8081`

## 📚 Documentación de la API

### 🌐 Swagger UI
Accede a la documentación interactiva en:
```
http://localhost:8081/swagger-ui/index.html
```

### 📋 Endpoints Principales

#### 🔐 Autenticación
```http
POST /auth/login
Content-Type: application/json

{
  "email": "admin@forohub.com",
  "contraseña": "password"
}
```

#### 📝 Tópicos
```http
GET    /topicos              # Listar tópicos (paginado)
POST   /topicos              # Crear tópico (requiere auth)
GET    /topicos/{id}         # Obtener tópico específico
PUT    /topicos/{id}         # Actualizar tópico (solo autor)
DELETE /topicos/{id}         # Eliminar tópico (solo autor)
```

#### 💬 Respuestas
```http
GET    /topicos/{id}/respuestas    # Listar respuestas de un tópico
POST   /topicos/{id}/respuestas    # Crear respuesta (requiere auth)
PUT    /respuestas/{id}            # Actualizar respuesta (solo autor)
DELETE /respuestas/{id}            # Eliminar respuesta (solo autor)
```

### 🔑 Autenticación

Para endpoints protegidos, incluir el header:
```http
Authorization: Bearer {tu-jwt-token}
```

## 🧪 Testing con Postman

### Importar Colección
1. Importa el archivo `ForoHub_Postman_Collection.json`
2. La colección incluye:
   - ✅ Autenticación automática
   - ✅ Variables de entorno configuradas
   - ✅ Tests de todos los endpoints
   - ✅ Casos de error incluidos

### Usuarios de Prueba

| Email | Contraseña | Rol |
|-------|------------|-----|
| `admin@forohub.com` | `password` | ADMIN |
| `juan.perez@email.com` | `password` | USER |
| `maria.garcia@email.com` | `password` | USER |

## �️ Estructura de Base de Datos

### Tablas Principales

```sql
usuarios
├── id (PK)
├── nombre
├── email (UNIQUE)
├── contraseña (BCrypt hash)
└── rol (ADMIN, USER)

topicos
├── id (PK)
├── titulo
├── mensaje
├── fecha_creacion
├── estado (ABIERTO, CERRADO)
├── autor_id (FK → usuarios.id)
└── curso

respuestas
├── id (PK)
├── mensaje
├── fecha_creacion
├── autor_id (FK → usuarios.id)
└── topico_id (FK → topicos.id)
```

## 🔒 Seguridad

- **Contraseñas**: Hash BCrypt con salt automático
- **JWT**: Tokens firmados con algoritmo HMAC256
- **CORS**: Configurado para desarrollo y producción
- **Validaciones**: Bean Validation en todos los DTOs
- **Autorización**: Control de acceso basado en roles y ownership
## 🚀 Despliegue

### Variables de Entorno para Producción

```bash
# Base de datos
SPRING_DATASOURCE_URL=jdbc:mysql://tu-host:3306/forohub
SPRING_DATASOURCE_USERNAME=tu-usuario
SPRING_DATASOURCE_PASSWORD=tu-password

# JWT
JWT_SECRET=tu-secreto-super-seguro-para-produccion

# Perfil
SPRING_PROFILES_ACTIVE=prod
```

### Docker (Opcional)

```dockerfile
FROM openjdk:17-jre-slim

WORKDIR /app
COPY target/foro-hub-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]
```

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver `LICENSE` para más detalles.

## � Contacto

**Desarrollador**: Tu Nombre  
**Email**: tu-email@ejemplo.com  
**LinkedIn**: [tu-perfil-linkedin](https://linkedin.com/in/tu-perfil)

## 🏆 Reconocimientos

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [SpringDoc OpenAPI](https://springdoc.org/)
- [JWT.io](https://jwt.io/)

---

⭐ **¡Si este proyecto te fue útil, no olvides darle una estrella!** ⭐
