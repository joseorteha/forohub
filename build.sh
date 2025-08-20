#!/bin/bash
echo "🚀 Building ForoHub for Railway deployment..."

# Instalar dependencias y compilar
mvn clean package -DskipTests

echo "✅ Build completed successfully!"
