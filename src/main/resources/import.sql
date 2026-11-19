-- =========================================================
-- ROLES (business)
-- =========================================================
INSERT INTO rol (id_rol, nombre, descripcion) VALUES (1, 'ROLE_CLIENTE', 'Usuario cliente del sistema');
INSERT INTO rol (id_rol, nombre, descripcion) VALUES (2, 'ROLE_ADMINISTRADOR', 'Usuario administrador del sistema');

-- =========================================================
-- USUARIOS DE SEGURIDAD (para login/JWT)
-- Passwords hasheados con BCrypt strength 10
-- admin123 -> $2a$10$mjtz6jbcUGJekzBSgDE0uOxp4xYVTmSLXai2EFhEOv5BoKrdXDOxG
-- cliente123 -> $2a$10$73qbe7brB/2QaIvYyq44Suf5aE2txQ6OhqwjAAAzEnBtlZnZZEJSW
-- =========================================================
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Admin', 'Sistema', '00000000', '000000000', 'admin@sportfit.com', '$2a$10$mjtz6jbcUGJekzBSgDE0uOxp4xYVTmSLXai2EFhEOv5BoKrdXDOxG', true, 'import.sql', CURRENT_TIMESTAMP, 2);
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Cliente', 'Prueba', '00000001', '000000001', 'cliente@sportfit.com', '$2a$10$73qbe7brB/2QaIvYyq44Suf5aE2txQ6OhqwjAAAzEnBtlZnZZEJSW', true, 'import.sql', CURRENT_TIMESTAMP, 1);

-- =========================================================
-- USUARIOS DE NEGOCIO (existentes)
-- Password: 123456 -> $2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW
-- =========================================================
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Juan', 'Perez', '12345678', '987654321', 'jperez@correo.com', '$2a$12$R/CseIQ.R/ElyHhZvFNkmuLxC35yWMO9ILU8rCAYFZTBb8DIylFg2', true, 'import.sql', CURRENT_TIMESTAMP, 1);
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Maria', 'Gomez', '10000002', '900000002', 'mgomez@correo.com', '$2a$12$R/CseIQ.R/ElyHhZvFNkmuLxC35yWMO9ILU8rCAYFZTBb8DIylFg2', true, 'import.sql', CURRENT_TIMESTAMP, 2);
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Carlos', 'Ramirez', '10000003', '900000003', 'cramirez@correo.com', '$2a$12$R/CseIQ.R/ElyHhZvFNkmuLxC35yWMO9ILU8rCAYFZTBb8DIylFg2', true, 'import.sql', CURRENT_TIMESTAMP, 2);
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Lucia', 'Torres', '10000004', '900000004', 'ltorres@correo.com', '$2a$12$R/CseIQ.R/ElyHhZvFNkmuLxC35yWMO9ILU8rCAYFZTBb8DIylFg2', true, 'import.sql', CURRENT_TIMESTAMP, 1);
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Diego', 'Flores', '10000005', '900000005', 'dflores@correo.com', '$2a$12$R/CseIQ.R/ElyHhZvFNkmuLxC35yWMO9ILU8rCAYFZTBb8DIylFg2', true, 'import.sql', CURRENT_TIMESTAMP, 1);
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Ana', 'Castillo', '10000006', '900000006', 'acastillo@correo.com', '$2a$12$R/CseIQ.R/ElyHhZvFNkmuLxC35yWMO9ILU8rCAYFZTBb8DIylFg2', true, 'import.sql', CURRENT_TIMESTAMP, 1);
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Pedro', 'Huaman', '10000007', '900000007', 'phuaman@correo.com', '$2a$12$R/CseIQ.R/ElyHhZvFNkmuLxC35yWMO9ILU8rCAYFZTBb8DIylFg2', false, 'import.sql', CURRENT_TIMESTAMP, 1);

-- =========================================================
-- TIPOS DE CANCHA
-- =========================================================
INSERT INTO tipo_cancha (deporte, descripcion, aforo, f_creacion) VALUES ('FUTBOL', 'Cancha de grass sintetico con arcos reglamentarios', 22, '2026-01-10');
INSERT INTO tipo_cancha (deporte, descripcion, aforo, f_creacion) VALUES ('BASKET', 'Cancha techada con tablero profesional', 10, '2026-01-12');
INSERT INTO tipo_cancha (deporte, descripcion, aforo, f_creacion) VALUES ('VOLEY', 'Cancha de piso de cemento con red entrenada', 12, '2026-01-15');
INSERT INTO tipo_cancha (deporte, descripcion, aforo, f_creacion) VALUES ('TENIS', 'Cancha de cemento con superficie markings', 4, '2026-01-18');
INSERT INTO tipo_cancha (deporte, descripcion, aforo, f_creacion) VALUES ('FUTSAL', 'Cancha cubierta con porterias metalicas', 12, '2026-01-20');

-- =========================================================
-- SEDES
-- =========================================================
INSERT INTO sede (nombre, distrito, direccion, url_ubicacion, id_usuario) VALUES ('Sede Surco', 'Surco', 'Av. Los Pinos 120', 'https://maps.google.com/?q=-12.1469,-76.9706', 1);
INSERT INTO sede (nombre, distrito, direccion, url_ubicacion, id_usuario) VALUES ('Sede San Miguel', 'San Miguel', 'Calle La Paz 345', 'https://maps.google.com/?q=-12.0821,-77.0035', 4);
INSERT INTO sede (nombre, distrito, direccion, url_ubicacion, id_usuario) VALUES ('Sede Miraflores', 'Miraflores', 'Av. Arequipa 1500', 'https://maps.google.com/?q=-12.1280,-77.0300', 5);
INSERT INTO sede (nombre, distrito, direccion, url_ubicacion, id_usuario) VALUES ('Sede Barranco', 'Barranco', 'Malecón 99', 'https://maps.google.com/?q=-12.1440,-77.0200', 5);

-- =========================================================
-- CANCHAS POR SEDE
-- =========================================================
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Futbol Surco A', true, 45.00, 1, 1);
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Voley Surco', true, 30.00, 1, 3);
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Basket Surco', true, 35.00, 1, 2);
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Futbol San Miguel A', true, 50.00, 2, 1);
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Futsal San Miguel', true, 40.00, 2, 5);
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Basket San Miguel', true, 35.00, 2, 2);
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Voley Miraflores', true, 30.00, 3, 3);
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Tenis Miraflores', false, 55.00, 3, 4);
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Futbol Barranco A', true, 42.00, 4, 1);
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Futsal Barranco', true, 38.00, 4, 5);
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Tenis Barranco', true, 60.00, 4, 4);
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Basket Barranco', true, 33.00, 4, 2);
-- Canchas extra de FUTBOL en Sede Surco (varias filas en el cuadro)
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Futbol Surco B', true, 25.00, 1, 1);
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Futbol Surco C', true, 45.00, 1, 1);
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Futbol Surco D', true, 45.00, 1, 1);

-- =========================================================
-- RESERVAS (ids 1 a 25)
-- estados: solicitada | confirmada | cancelada | rechazada | completada
-- completada = 3 (1,2,3) | cancelada = 4 (4,9,12,17) | rechazada = 2 (15,21)
-- confirmada = 10 | solicitada = 6
-- =========================================================
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-09-25', '08:00:00', '10:00:00', 'completada', 'import.sql', CURRENT_TIMESTAMP, 4, 1);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-09-25', '16:00:00', '18:00:00', 'completada', 'import.sql', CURRENT_TIMESTAMP, 5, 4);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-09-28', '09:00:00', '11:00:00', 'completada', 'import.sql', CURRENT_TIMESTAMP, 6, 2);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-09-28', '10:00:00', '12:00:00', 'cancelada', 'import.sql', CURRENT_TIMESTAMP, 7, 5);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-09-28', '17:00:00', '19:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 4, 6);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-09-30', '08:00:00', '10:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 5, 9);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-09-30', '08:00:00', '10:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 6, 10);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-09-30', '12:00:00', '13:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 1, 7);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-09-30', '15:00:00', '17:00:00', 'completada', 'import.sql', CURRENT_TIMESTAMP, 7, 1);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-09-30', '19:00:00', '21:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 4, 3);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-02', '08:00:00', '10:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 5, 4);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-02', '11:00:00', '13:00:00', 'cancelada', 'import.sql', CURRENT_TIMESTAMP, 6, 9);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-02', '18:00:00', '20:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 7, 11);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-05', '09:00:00', '11:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 1, 1);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-05', '10:00:00', '12:00:00', 'rechazada', 'import.sql', CURRENT_TIMESTAMP, 4, 12);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-05', '14:00:00', '16:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 5, 5);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-05', '16:00:00', '18:00:00', 'cancelada', 'import.sql', CURRENT_TIMESTAMP, 6, 10);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-10', '08:00:00', '10:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 7, 9);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-10', '09:00:00', '11:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 1, 2);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-10', '13:00:00', '15:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 4, 4);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-10', '20:00:00', '22:00:00', 'rechazada', 'import.sql', CURRENT_TIMESTAMP, 5, 6);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-15', '08:00:00', '10:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 6, 1);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-15', '11:00:00', '13:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 7, 3);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-15', '15:00:00', '17:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 1, 9);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-15', '18:00:00', '20:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 4, 10);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-15', '21:00:00', '23:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 4, 10);
-- Corregir fechas de reservas para que estén en el rango de fechas actual
-- (evitar cambair todo a mano y de igual manera pasen los dias y tenga q cambair denuevo)
UPDATE reserva SET f_reserva = CURRENT_DATE + (id_reserva % 7 + 1) WHERE id_reserva <= 26 AND estado IN ('confirmada', 'solicitada');
UPDATE reserva SET f_reserva = CURRENT_DATE - (id_reserva % 20 + 1) WHERE id_reserva <= 26 AND estado IN ('completada', 'cancelada', 'rechazada');
-- RESERVAS CON EL CLIENTE PRUEBA (id_usuario = 2) PARA PRUEBAS DE FRONTEND
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES (CURRENT_DATE + 3, '09:00:00', '11:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 2, 1);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES (CURRENT_DATE + 3, '18:00:00', '20:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 2, 4);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES (CURRENT_DATE + 5, '10:00:00', '12:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 2, 2);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES (CURRENT_DATE + 4, '15:00:00', '17:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 2, 3);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES (CURRENT_DATE + 6, '19:00:00', '21:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 2, 11);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES (CURRENT_DATE - 3, '08:00:00', '10:00:00', 'completada', 'import.sql', CURRENT_TIMESTAMP, 2, 9);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES (CURRENT_DATE - 10, '16:00:00', '18:00:00', 'completada', 'import.sql', CURRENT_TIMESTAMP, 2, 10);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES (CURRENT_DATE - 1, '20:00:00', '22:00:00', 'cancelada', 'import.sql', CURRENT_TIMESTAMP, 2, 6);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES (CURRENT_DATE - 5, '13:00:00', '15:00:00', 'rechazada', 'import.sql', CURRENT_TIMESTAMP, 2, 12);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES (CURRENT_DATE + 7, '21:00:00', '23:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 2, 14);

-- =========================================================
-- PAGOS (reservas solicitada, confirmada, completada y tambien cancelada: esas son el monto perdido)
-- =========================================================
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (45.00, 'Tarjeta', 'https://comprobantes/001', 1);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (50.00, 'Yape', 'https://comprobantes/002', 2);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (30.00, 'Plin', 'https://comprobantes/003', 3);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (35.00, 'Efectivo', null, 5);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (42.00, 'Tarjeta', 'https://comprobantes/005', 6);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (38.00, 'Yape', 'https://comprobantes/006', 7);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (30.00, 'Plin', 'https://comprobantes/007', 8);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (33.00, 'Yape', 'https://comprobantes/008', 10);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (50.00, 'Tarjeta', 'https://comprobantes/009', 11);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (60.00, 'Plin', 'https://comprobantes/010', 13);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (45.00, 'Yape', 'https://comprobantes/011', 14);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (33.00, 'Efectivo', null, 16);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (42.00, 'Yape', 'https://comprobantes/012', 18);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (30.00, 'Plin', 'https://comprobantes/013', 19);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (50.00, 'Tarjeta', 'https://comprobantes/014', 20);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (35.00, 'Yape', 'https://comprobantes/015', 22);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (45.00, 'Tarjeta', 'https://comprobantes/016', 23);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (33.00, 'Plin', 'https://comprobantes/017', 15);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (38.00, 'Tarjeta', 'https://comprobantes/018', 24);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (30.00, 'Yape', 'https://comprobantes/019', 25);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (45.00, 'Tarjeta', 'https://comprobantes/020', 4);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (38.00, 'Yape', 'https://comprobantes/021', 9);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (55.00, 'Plin', 'https://comprobantes/022', 12);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (40.00, 'Efectivo', null, 17);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (70.00, 'Yape', 'https://comprobantes/023', 21);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (76.00, 'Plin', 'https://comprobantes/024', 26);
-- Pagos de reservas del cliente prueba (id_usuario = 2)
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (90.00, 'Yape', 'https://comprobantes/027', 27);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (100.00, 'Plin', 'https://comprobantes/028', 28);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (60.00, 'Yape', 'https://comprobantes/029', 29);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (70.00, 'Yape', 'https://comprobantes/030', 30);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (120.00, 'Plin', 'https://comprobantes/031', 31);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (84.00, 'Tarjeta', 'https://comprobantes/032', 32);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (76.00, 'Yape', 'https://comprobantes/033', 33);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (70.00, 'Yape', 'https://comprobantes/034', 34);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (66.00, 'Plin', 'https://comprobantes/035', 35);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (90.00, 'Yape', 'https://comprobantes/036', 36);

-- =========================================================
-- CANCELACIONES (solo reservas con estado cancelada)
-- tipos: No podre asistir | Problemas de salud | Problemas de transporte | Reserve por error | Otro
-- =========================================================
INSERT INTO cancelacion (tipo_cancelacion, f_cancelacion, id_reserva) VALUES ('No podré asistir', CURRENT_TIMESTAMP, 4);
INSERT INTO cancelacion (tipo_cancelacion, f_cancelacion, id_reserva) VALUES ('Problemas de salud', CURRENT_TIMESTAMP, 9);
INSERT INTO cancelacion (tipo_cancelacion, f_cancelacion, id_reserva) VALUES ('Problemas de transporte', CURRENT_TIMESTAMP, 12);
INSERT INTO cancelacion (tipo_cancelacion, f_cancelacion, id_reserva) VALUES ('Reservé por error', CURRENT_TIMESTAMP, 17);
INSERT INTO cancelacion (tipo_cancelacion, f_cancelacion, id_reserva) VALUES ('No podré asistir', CURRENT_TIMESTAMP, 34);

-- =========================================================
-- NOTIFICACIONES
-- =========================================================
INSERT INTO notificacion (tipo, titulo, mensaje, f_notificacion, leido, id_usuario) VALUES ('Confirmación', 'Confirmación de reserva', 'Tu reserva del 30/09 en Sede Surco quedó confirmada', CURRENT_TIMESTAMP, true, 4);
INSERT INTO notificacion (tipo, titulo, mensaje, f_notificacion, leido, id_usuario) VALUES ('Cancelación', 'Cancelación de reserva', 'Tu reserva del 28/09 fue cancelada por no poder asistir', CURRENT_TIMESTAMP, false, 7);
INSERT INTO notificacion (tipo, titulo, mensaje, f_notificacion, leido, id_usuario) VALUES ('Confirmación', 'Confirmación de pago', 'Registramos el pago de tu reserva en Sede Barranco', CURRENT_TIMESTAMP, true, 5);
INSERT INTO notificacion (tipo, titulo, mensaje, f_notificacion, leido, id_usuario) VALUES ('Recordatorio', 'Recordatorio de reserva', 'Mañana tienes reserva en Sede Miraflores a las 09:00', CURRENT_TIMESTAMP, false, 6);
INSERT INTO notificacion (tipo, titulo, mensaje, f_notificacion, leido, id_usuario) VALUES ('Recordatorio', 'Recordatorio de reserva', 'Tu reserva en Sede Barranco es en 2 horas', CURRENT_TIMESTAMP, false, 1);
INSERT INTO notificacion (tipo, titulo, mensaje, f_notificacion, leido, id_usuario) VALUES ('Confirmación', 'Confirmación de reserva', 'Tu reserva ha sido confirmada', CURRENT_TIMESTAMP, false, 3);
INSERT INTO notificacion (tipo, titulo, mensaje, f_notificacion, leido, id_usuario) VALUES ('Cancelación', 'Cancelación de reserva', 'Tu reserva fue cancelada', CURRENT_TIMESTAMP, true, 3);

-- =========================================================
-- INCIDENCIAS
-- tipos: Tecnico | Instalaciones | Preguntas
-- estados: Pendiente | Atendido
-- =========================================================

INSERT INTO incidencia (tipo, asunto, descripcion, estado, respuesta_admin, f_creacion, f_respuesta, id_usuario) VALUES ('Tecnico', 'Falla en iluminacion', 'Las luces del campo 2 parpadean', 'Atendido', 'Se revisaron los reflectores', '2026-09-10 14:00:00', '2026-09-11 10:00:00', 2);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, respuesta_admin, f_creacion, f_respuesta, id_usuario) VALUES ('Instalaciones', 'Malla rota en arco', 'La red del arco norte esta descolgada', 'Atendido', 'Se coloco red nueva', '2026-09-12 11:30:00', '2026-09-13 09:00:00', 3);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, respuesta_admin, f_creacion, f_respuesta, id_usuario) VALUES ('Preguntas', 'Consulta sobre horarios', 'Quisiera saber si atienden feriados', 'Atendido', 'Atendemos con normalidad en feriados', '2026-09-15 16:20:00', '2026-09-15 17:00:00', 5);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, respuesta_admin, f_creacion, f_respuesta, id_usuario) VALUES ('Tecnico', 'Tablero electronico no enciende', 'No marca el tiempo ni marcador', 'Pendiente', null, '2026-09-22 18:00:00', null, 6);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, respuesta_admin, f_creacion, f_respuesta, id_usuario) VALUES ('Instalaciones', 'Piso resbaloso en cancha tenis', 'Se empoza agua cerca a la linea', 'Pendiente', null, '2026-09-25 15:45:00', null, 2);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, respuesta_admin, f_creacion, f_respuesta, id_usuario) VALUES ('Tecnico', 'Problema con red de voley', 'La tension de la red es muy baja', 'Atendido', 'Se ajustaron los tensores', '2026-09-28 09:15:00', '2026-09-28 11:00:00', 5);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, respuesta_admin, f_creacion, f_respuesta, id_usuario) VALUES ('Tecnico', 'Falla en ducha camerinos', 'No sale agua caliente', 'Atendido', 'Se reparo la terma', '2026-10-02 08:30:00', '2026-10-02 12:00:00', 3);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, respuesta_admin, f_creacion, f_respuesta, id_usuario) VALUES ('Instalaciones', 'Candado trabado', 'La puerta del vestuario no abria', 'Atendido', 'Se cambio el candado', '2026-10-05 10:00:00', '2026-10-05 10:30:00', 5);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, respuesta_admin, f_creacion, f_respuesta, id_usuario) VALUES ('Preguntas', 'Tarifa por grupos grandes', 'Hay descuento por torneos?', 'Pendiente', null, '2026-10-10 14:15:00', null, 6);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, respuesta_admin, f_creacion, f_respuesta, id_usuario) VALUES ('Tecnico', 'Sonido de parlantes distorsionado', 'El altavoz de la cancha 1 chilla', 'Atendido', 'Se cambio el cable de audio', '2026-10-18 16:00:00', '2026-10-19 10:00:00', 2);