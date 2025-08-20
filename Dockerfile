# Multi-stage build para Railway
# Etapa 1: Build
FROM eclipse-temurin:17-jdk-alpine AS builder

# Instalar Maven
RUN apk add --no-cache maven

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

# Descargar dependencias (cache layer)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar aplicación
RUN mvn clean package -DskipTests -B

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine

# Instalar dependencias necesarias
RUN apk add --no-cache tzdata

# Crear directorio de trabajo
WORKDIR /app

# Crear usuario no privilegiado
RUN addgroup -g 1001 -S spring && \
    adduser -S spring -u 1001

# Copiar JAR desde la etapa de build
COPY --from=builder /app/target/foro-hub-0.0.1-SNAPSHOT.jar app.jar

# Cambiar propietario del archivo
RUN chown spring:spring app.jar

# Cambiar a usuario no privilegiado
USER spring:spring

# Exponer puerto dinámico de Railway
EXPOSE $PORT

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Comando para ejecutar la aplicación con optimizaciones
CMD ["sh", "-c", "java $JAVA_OPTS -Dserver.port=$PORT -jar app.jar"]

# Metadatos
LABEL maintainer="ForoHub Team"
LABEL description="ForoHub API REST - Forum Management System"
LABEL version="1.0.0"
