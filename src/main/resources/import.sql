INSERT INTO rol (id_rol, nombre, descripcion) VALUES (1, 'CLIENTE', 'Usuario cliente del sistema');
INSERT INTO rol (id_rol, nombre, descripcion) VALUES (2, 'ADMINISTRADOR', 'Usuario administrador del sistema');





INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol)
VALUES ('Juan', 'Perez', '12345678', '987654321', 'jperez@correo.com', '123456', true, 'import.sql', CURRENT_TIMESTAMP, 1);
