#!/bin/bash

echo "🚀 Building ForoHub for Render deployment..."

# Limpiar y compilar
mvn clean package -DskipTests

echo "✅ Build completed successfully!"
