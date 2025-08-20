FROM openjdk:17-jre-slim

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivos del proyecto
COPY target/foro-hub-0.0.1-SNAPSHOT.jar app.jar

# Exponer puerto dinámico de Railway
EXPOSE $PORT

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=prod

# Comando para ejecutar la aplicación
CMD ["java", "-Dserver.port=$PORT", "-jar", "app.jar"]

# Metadatos
LABEL maintainer="ForoHub Team"
LABEL description="ForoHub API REST - Forum Management System"
LABEL version="1.0.0"
