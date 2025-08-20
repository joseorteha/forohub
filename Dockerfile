# Multi-stage build para Railway
# Etapa 1: Build
FROM eclipse-temurin:17-jdk-alpine AS builder

# Instalar Maven
RUN apk add --no-cache maven

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .

# Descargar dependencias (cache layer)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar aplicación
RUN mvn clean package -DskipTests -B

# Debug: verificar archivos generados
RUN echo "=== ARCHIVOS GENERADOS ===" && \
    ls -la target/ && \
    echo "=========================="

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine

# Instalar dependencias necesarias
RUN apk add --no-cache tzdata curl

# Crear directorio de trabajo
WORKDIR /app

# Crear usuario no privilegiado
RUN addgroup -g 1001 -S spring && \
    adduser -S spring -u 1001

# Copiar JAR desde la etapa de build 
COPY --from=builder /app/target/*.jar app.jar

# Verificar que el JAR se copió correctamente
RUN ls -la app.jar && \
    echo "JAR copiado exitosamente"

# Cambiar propietario del archivo
RUN chown spring:spring app.jar

# Cambiar a usuario no privilegiado
USER spring:spring

# Exponer puerto
EXPOSE 8080

# Variables de entorno
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseContainerSupport"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:${PORT:-8080}/actuator/health || exit 1

# Comando para ejecutar la aplicación
CMD ["sh", "-c", "echo 'Iniciando ForoHub...' && java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar app.jar"]
