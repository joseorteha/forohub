@echo off
echo ======================================
echo     ForoHub - Configuracion Inicial
echo ======================================

echo.
echo Verificando Java...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java no encontrado. Instala Java 17 o superior.
    pause
    exit /b 1
)

echo.
echo Verificando Maven...
mvn -version
if %errorlevel% neq 0 (
    echo ERROR: Maven no encontrado. Instala Maven 3.6 o superior.
    pause
    exit /b 1
)

echo.
echo ======================================
echo Compilando proyecto...
echo ======================================
mvn clean compile
if %errorlevel% neq 0 (
    echo ERROR: Fallo en la compilacion.
    pause
    exit /b 1
)

echo.
echo ======================================
echo Ejecutando tests...
echo ======================================
mvn test
if %errorlevel% neq 0 (
    echo WARNING: Algunos tests fallaron.
)

echo.
echo ======================================
echo Iniciando aplicacion...
echo ======================================
echo La aplicacion estara disponible en:
echo - API: http://localhost:8080
echo - Swagger: http://localhost:8080/swagger-ui.html
echo.
echo Presiona Ctrl+C para detener la aplicacion
echo.

mvn spring-boot:run


