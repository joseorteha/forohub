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
EXPOSE 10000

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=render
ENV JAVA_OPTS="-Xms128m -Xmx512m -XX:+UseContainerSupport -XX:+UseG1GC"
ENV SERVER_PORT=10000

# Health check optimizado
HEALTHCHECK --interval=60s --timeout=10s --start-period=60s --retries=5 \
  CMD curl -f http://localhost:${PORT:-10000}/actuator/health || exit 1

# Comando para ejecutar la aplicación con mejor logging
CMD ["sh", "-c", "echo 'Iniciando ForoHub con perfil render...' && echo 'Variables: PORT=${PORT} DATABASE_URL=${DATABASE_URL}' && java $JAVA_OPTS -Dserver.port=${PORT:-10000} -jar app.jar"]
