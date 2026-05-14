-- Insertar usuarios de prueba (ajusta según tu esquema de base de datos)
-- Asegúrate de que la tabla se llame 'usuario' y tenga las columnas: id, email, password, rol
-- La contraseña está encriptada con BCrypt. Para pruebas rápidas puedes usar {noop}1234

-- Usando BCrypt (reemplaza los hashes por los que genere tu aplicación)
INSERT INTO usuario (id, email, password, rol) VALUES
(1, 'admin@mail.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EprM8kZzqT7zjZ2I0Qk9iS', 'ADMIN'),
(2, 'profesor1@mail.com', '$2a$10$GtJ5yJ6lK7hL8jK9lQwErTyUiOpAsDfGhJkLzXcVbNmQwErTyUiOp', 'PROFESOR'),
(3, 'profesor2@mail.com', '$2a$10$GtJ5yJ6lK7hL8jK9lQwErTyUiOpAsDfGhJkLzXcVbNmQwErTyUiOp', 'PROFESOR'),
(4, 'alumno1@mail.com', '$2a$10$FhJkLzXcVbNmQwErTyUiOpAsDfGhJkLzXcVbNmQwErTyUiOp', 'ALUMNO');

-- Si estás en desarrollo y usas el encoder {noop}, puedes poner:
-- INSERT INTO usuario (id, email, password, rol) VALUES
-- (1, 'admin@mail.com', '{noop}admin123', 'ADMIN'),
-- (2, 'profesor1@mail.com', '{noop}1234', 'PROFESOR'),
-- (3, 'profesor2@mail.com', '{noop}1234', 'PROFESOR'),
-- (4, 'alumno1@mail.com', '{noop}1234', 'ALUMNO');