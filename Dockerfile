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

# Crear script de inicio
RUN echo '#!/bin/sh' > start.sh && \
    echo 'echo "=== INICIANDO FOROHUB ==="' >> start.sh && \
    echo 'echo "PORT: $PORT"' >> start.sh && \
    echo 'echo "DATABASE_URL: $DATABASE_URL"' >> start.sh && \
    echo 'echo "JWT_SECRET: ${JWT_SECRET:0:20}..."' >> start.sh && \
    echo 'echo "========================="' >> start.sh && \
    echo 'if [ -z "$DATABASE_URL" ]; then echo "ERROR: DATABASE_URL no configurada"; exit 1; fi' >> start.sh && \
    echo 'if [ -z "$JWT_SECRET" ]; then echo "ERROR: JWT_SECRET no configurada"; exit 1; fi' >> start.sh && \
    echo 'if [ -z "$PORT" ]; then export PORT=10000; fi' >> start.sh && \
    echo 'exec java $JAVA_OPTS -Dserver.port=$PORT -jar app.jar' >> start.sh && \
    chmod +x start.sh

# Cambiar propietario del archivo
RUN chown spring:spring app.jar start.sh

# Cambiar a usuario no privilegiado
USER spring:spring

# Exponer puerto
EXPOSE 10000

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=render
ENV JAVA_OPTS="-Xms128m -Xmx512m -XX:+UseContainerSupport -XX:+UseG1GC"

# Health check optimizado
HEALTHCHECK --interval=60s --timeout=10s --start-period=60s --retries=5 \
  CMD curl -f http://localhost:$PORT/health || exit 1

# Comando para ejecutar la aplicación con mejor logging y resolución de variables
CMD ["./start.sh"]
