-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-08-2025 a las 04:53:52
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `forohub`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flyway_schema_history`
--

CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `flyway_schema_history`
--

INSERT INTO `flyway_schema_history` (`installed_rank`, `version`, `description`, `type`, `script`, `checksum`, `installed_by`, `installed_on`, `execution_time`, `success`) VALUES
(1, '1', 'create-table-usuarios', 'SQL', 'V1__create-table-usuarios.sql', -1969083865, 'ortega', '2025-08-20 02:18:16', 57, 1),
(2, '2', 'create-table-topicos', 'SQL', 'V2__create-table-topicos.sql', -615874857, 'ortega', '2025-08-20 02:18:16', 60, 1),
(3, '3', 'create-table-respuestas', 'SQL', 'V3__create-table-respuestas.sql', -1949905301, 'ortega', '2025-08-20 02:18:16', 73, 1),
(4, '4', 'insert-data-inicial', 'SQL', 'V4__insert-data-inicial.sql', 78429067, 'ortega', '2025-08-20 02:18:16', 55, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `respuestas`
--

CREATE TABLE `respuestas` (
  `id` bigint(20) NOT NULL,
  `mensaje` text NOT NULL,
  `fecha_creacion` datetime NOT NULL,
  `autor_id` bigint(20) NOT NULL,
  `topico_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `respuestas`
--

INSERT INTO `respuestas` (`id`, `mensaje`, `fecha_creacion`, `autor_id`, `topico_id`) VALUES
(1, 'Puedes usar Spring Initializr en https://start.spring.io/ para generar tu proyecto fácilmente', '2025-08-19 20:18:16', 1, 1),
(2, '@Entity marca la clase como entidad JPA, @Table especifica el nombre de la tabla en la base de datos', '2025-08-19 20:18:16', 1, 2),
(3, 'Revisa tu archivo application.properties, asegúrate de tener las credenciales correctas', '2025-08-19 20:18:16', 3, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `topicos`
--

CREATE TABLE `topicos` (
  `id` bigint(20) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `mensaje` text NOT NULL,
  `fecha_creacion` datetime NOT NULL,
  `estado` varchar(20) NOT NULL,
  `autor_id` bigint(20) NOT NULL,
  `curso` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `topicos`
--

INSERT INTO `topicos` (`id`, `titulo`, `mensaje`, `fecha_creacion`, `estado`, `autor_id`, `curso`) VALUES
(1, '¿Cómo instalar Spring Boot?', 'Tengo problemas para configurar un proyecto con Spring Boot desde cero. ¿Alguien me puede ayudar?', '2025-08-19 20:18:16', 'ABIERTO', 2, 'Spring Framework'),
(2, 'Dudas sobre JPA', '¿Cuál es la diferencia entre @Entity y @Table en JPA?', '2025-08-19 20:18:16', 'ABIERTO', 3, 'Java Persistence API'),
(3, 'Configuración de base de datos', 'No puedo conectar mi aplicación Spring Boot con MySQL', '2025-08-19 20:18:16', 'ABIERTO', 2, 'Spring Boot');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `contraseña` varchar(300) NOT NULL,
  `rol` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `email`, `contraseña`, `rol`) VALUES
(1, 'Administrador', 'admin@forohub.com', '$2a$10$qo86s/bjtX/eRIhH3GOxV.Vg87jwUleyeODyYxF8ctN9LgnJboeM.', 'ADMIN'),
(2, 'Juan Pérez', 'juan.perez@email.com', '$2a$10$qo86s/bjtX/eRIhH3GOxV.Vg87jwUleyeODyYxF8ctN9LgnJboeM.', 'USER'),
(3, 'María García', 'maria.garcia@email.com', '$2a$10$qo86s/bjtX/eRIhH3GOxV.Vg87jwUleyeODyYxF8ctN9LgnJboeM.', 'USER');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `flyway_schema_history`
--
ALTER TABLE `flyway_schema_history`
  ADD PRIMARY KEY (`installed_rank`),
  ADD KEY `flyway_schema_history_s_idx` (`success`);

--
-- Indices de la tabla `respuestas`
--
ALTER TABLE `respuestas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `autor_id` (`autor_id`),
  ADD KEY `topico_id` (`topico_id`);

--
-- Indices de la tabla `topicos`
--
ALTER TABLE `topicos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `autor_id` (`autor_id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `respuestas`
--
ALTER TABLE `respuestas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `topicos`
--
ALTER TABLE `topicos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `respuestas`
--
ALTER TABLE `respuestas`
  ADD CONSTRAINT `respuestas_ibfk_1` FOREIGN KEY (`autor_id`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `respuestas_ibfk_2` FOREIGN KEY (`topico_id`) REFERENCES `topicos` (`id`);

--
-- Filtros para la tabla `topicos`
--
ALTER TABLE `topicos`
  ADD CONSTRAINT `topicos_ibfk_1` FOREIGN KEY (`autor_id`) REFERENCES `usuarios` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
