FROM openjdk:17-jdk-slim

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivos del proyecto
COPY target/foro-hub-0.0.1-SNAPSHOT.jar app.jar

# Exponer puerto
EXPOSE 8080

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=production
ENV JWT_SECRET=default-secret-change-in-production

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

# Metadatos
LABEL maintainer="ForoHub Team"
LABEL description="ForoHub API REST - Forum Management System"
LABEL version="1.0.0"
