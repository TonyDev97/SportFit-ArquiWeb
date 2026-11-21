-- SportFit: datos iniciales para una base recién creada.
-- Cada sentencia ocupa una sola línea para el lector de import.sql de Hibernate.

-- ROLES
INSERT INTO rol (id_rol, nombre, descripcion) VALUES (1, 'ROLE_CLIENTE', 'Usuario cliente del sistema'), (2, 'ROLE_ADMINISTRADOR', 'Usuario administrador del sistema');

-- USUARIOS
-- Se conserva el orden de inserción y se mantienen los hashes BCrypt existentes.
-- Administradores: 1, 4 y 5. Clientes: 2, 3, 6, 7 y 8 (inactivo).

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
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol, img_usuario) VALUES ('Lucia', 'Torres', '10000004', '900000004', 'ltorres@correo.com', '$2a$12$R/CseIQ.R/ElyHhZvFNkmuLxC35yWMO9ILU8rCAYFZTBb8DIylFg2', true, 'import.sql', CURRENT_TIMESTAMP, 1, 'https://drive.google.com/thumbnail?id=1TFHRfUT0CtzxpV-4Kys_As8A6YMjjWWN&sz=w1000');
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Diego', 'Flores', '10000005', '900000005', 'dflores@correo.com', '$2a$12$R/CseIQ.R/ElyHhZvFNkmuLxC35yWMO9ILU8rCAYFZTBb8DIylFg2', true, 'import.sql', CURRENT_TIMESTAMP, 1);
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Ana', 'Castillo', '10000006', '900000006', 'acastillo@correo.com', '$2a$12$R/CseIQ.R/ElyHhZvFNkmuLxC35yWMO9ILU8rCAYFZTBb8DIylFg2', true, 'import.sql', CURRENT_TIMESTAMP, 1);
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Pedro', 'Huaman', '10000007', '900000007', 'phuaman@correo.com', '$2a$12$R/CseIQ.R/ElyHhZvFNkmuLxC35yWMO9ILU8rCAYFZTBb8DIylFg2', false, 'import.sql', CURRENT_TIMESTAMP, 1);

-- TIPOS DE CANCHA
INSERT INTO tipo_cancha (deporte, descripcion, aforo, f_creacion) VALUES ('FUTBOL', 'Cancha de grass sintetico con arcos reglamentarios', 22, '2026-01-10');
INSERT INTO tipo_cancha (deporte, descripcion, aforo, f_creacion) VALUES ('BASKET', 'Cancha techada con tablero profesional', 10, '2026-01-12');
INSERT INTO tipo_cancha (deporte, descripcion, aforo, f_creacion) VALUES ('VOLEY', 'Cancha de piso de cemento con red entrenada', 12, '2026-01-15');
INSERT INTO tipo_cancha (deporte, descripcion, aforo, f_creacion) VALUES ('TENIS', 'Cancha de cemento con superficie markings', 4, '2026-01-18');
INSERT INTO tipo_cancha (deporte, descripcion, aforo, f_creacion) VALUES ('FUTSAL', 'Cancha cubierta con porterias metalicas', 12, '2026-01-20');

-- SEDES (id_usuario identifica al administrador responsable)
INSERT INTO sede (nombre, distrito, direccion, url_ubicacion, id_usuario) VALUES ('Sede Surco', 'Surco', 'Av. Los Pinos 120', 'https://maps.google.com/?q=-12.1469,-76.9706', 1);
INSERT INTO sede (nombre, distrito, direccion, url_ubicacion, id_usuario) VALUES ('Sede San Miguel', 'San Miguel', 'Calle La Paz 345', 'https://maps.google.com/?q=-12.0821,-77.0035', 4);
INSERT INTO sede (nombre, distrito, direccion, url_ubicacion, id_usuario) VALUES ('Sede Miraflores', 'Miraflores', 'Av. Arequipa 1500', 'https://maps.google.com/?q=-12.1280,-77.0300', 5);
INSERT INTO sede (nombre, distrito, direccion, url_ubicacion, id_usuario) VALUES ('Sede Barranco', 'Barranco', 'Malecón 99', 'https://maps.google.com/?q=-12.1440,-77.0200', 5);

-- CANCHAS POR SEDE
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
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Futbol Surco B', true, 25.00, 1, 1);
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Futbol Surco C', true, 45.00, 1, 1);
INSERT INTO sede_cancha (nombre, estado, precio, id_sede, id_tipo_cancha) VALUES ('Cancha Futbol Surco D', true, 45.00, 1, 1);

-- RESERVAS
-- Todas pertenecen a clientes activos. La reserva 9 figura como cancelada para coincidir con su detalle.
-- Las fechas se ajustan al mes actual en Lima. Se conserva el orden de inserción (IDs del 1 al 36).
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date - 2, '08:00:00', '10:00:00', 'completada', 'import.sql', CURRENT_TIMESTAMP, 3, 1), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date - 3, '16:00:00', '18:00:00', 'completada', 'import.sql', CURRENT_TIMESTAMP, 6, 4), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date - 4, '09:00:00', '11:00:00', 'completada', 'import.sql', CURRENT_TIMESTAMP, 6, 2);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date - 5, '10:00:00', '12:00:00', 'cancelada', 'import.sql', CURRENT_TIMESTAMP, 7, 5), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 6, '17:00:00', '19:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 3, 6), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 7, '08:00:00', '10:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 6, 9);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 1, '08:00:00', '10:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 6, 10), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 2, '12:00:00', '13:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 2, 7), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date - 10, '15:00:00', '17:00:00', 'cancelada', 'import.sql', CURRENT_TIMESTAMP, 7, 1);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 4, '19:00:00', '21:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 3, 3), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 5, '08:00:00', '10:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 6, 4), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date - 13, '11:00:00', '13:00:00', 'cancelada', 'import.sql', CURRENT_TIMESTAMP, 6, 9);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 7, '18:00:00', '20:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 7, 11), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 1, '09:00:00', '11:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 2, 1), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date - 16, '10:00:00', '12:00:00', 'rechazada', 'import.sql', CURRENT_TIMESTAMP, 3, 12);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 3, '14:00:00', '16:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 6, 5), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date - 18, '16:00:00', '18:00:00', 'cancelada', 'import.sql', CURRENT_TIMESTAMP, 6, 10), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 5, '08:00:00', '10:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 7, 9);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 6, '09:00:00', '11:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 2, 2), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 7, '13:00:00', '15:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 3, 4), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date - 2, '20:00:00', '22:00:00', 'rechazada', 'import.sql', CURRENT_TIMESTAMP, 6, 6);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 2, '08:00:00', '10:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 6, 1), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 3, '11:00:00', '13:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 7, 3), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 4, '15:00:00', '17:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 3, 9);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 5, '18:00:00', '20:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 3, 10), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 6, '21:00:00', '23:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 3, 10);

-- Reservas de Cliente Prueba (IDs del 27 al 36).
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 3, '09:00:00', '11:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 2, 1), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 3, '18:00:00', '20:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 2, 4), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 5, '10:00:00', '12:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 2, 2);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 4, '15:00:00', '17:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 2, 3), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 6, '19:00:00', '21:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 2, 11), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date - 3, '08:00:00', '10:00:00', 'completada', 'import.sql', CURRENT_TIMESTAMP, 2, 9);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date - 10, '16:00:00', '18:00:00', 'completada', 'import.sql', CURRENT_TIMESTAMP, 2, 10), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date - 1, '20:00:00', '22:00:00', 'cancelada', 'import.sql', CURRENT_TIMESTAMP, 2, 6), ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date - 5, '13:00:00', '15:00:00', 'rechazada', 'import.sql', CURRENT_TIMESTAMP, 2, 12);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ((CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date + 7, '21:00:00', '23:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 2, 14);

-- Las fechas quedan en el mes vigente. Cerca del fin de mes, las reservas activas se desplazan juntas para conservar su separación.
WITH calendario AS (SELECT (CURRENT_TIMESTAMP AT TIME ZONE 'America/Lima')::date AS hoy), mes AS (SELECT hoy, date_trunc('month', hoy)::date AS inicio, (date_trunc('month', hoy) + INTERVAL '1 month' - INTERVAL '1 day')::date AS fin FROM calendario) UPDATE reserva r SET f_reserva = CASE WHEN r.estado IN ('confirmada', 'solicitada') THEN r.f_reserva - GREATEST(0, mes.hoy + 8 - mes.fin) ELSE GREATEST(mes.inicio, r.f_reserva) END FROM mes WHERE r.creado_por = 'import.sql' AND r.id_reserva BETWEEN 1 AND 36;
UPDATE reserva SET f_creacion = LEAST(CURRENT_TIMESTAMP, (f_reserva + h_inicio - INTERVAL '1 hour') AT TIME ZONE 'America/Lima') WHERE creado_por = 'import.sql' AND id_reserva BETWEEN 1 AND 36;

-- PAGOS: se mantienen los montos, métodos, comprobantes y reservas del import original.
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (45.00, 'Tarjeta', 'https://comprobantes/001', 1), (50.00, 'Yape', 'https://comprobantes/002', 2), (30.00, 'Plin', 'https://comprobantes/003', 3), (35.00, 'Efectivo', null, 5), (42.00, 'Tarjeta', 'https://comprobantes/005', 6);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (38.00, 'Yape', 'https://comprobantes/006', 7), (30.00, 'Plin', 'https://comprobantes/007', 8), (33.00, 'Yape', 'https://comprobantes/008', 10), (50.00, 'Tarjeta', 'https://comprobantes/009', 11), (60.00, 'Plin', 'https://comprobantes/010', 13);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (45.00, 'Yape', 'https://comprobantes/011', 14), (33.00, 'Efectivo', null, 16), (42.00, 'Yape', 'https://comprobantes/012', 18), (30.00, 'Plin', 'https://comprobantes/013', 19), (50.00, 'Tarjeta', 'https://comprobantes/014', 20);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (35.00, 'Yape', 'https://comprobantes/015', 22), (45.00, 'Tarjeta', 'https://comprobantes/016', 23), (33.00, 'Plin', 'https://comprobantes/017', 15), (38.00, 'Tarjeta', 'https://comprobantes/018', 24), (30.00, 'Yape', 'https://comprobantes/019', 25);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (45.00, 'Tarjeta', 'https://comprobantes/020', 4), (38.00, 'Yape', 'https://comprobantes/021', 9), (55.00, 'Plin', 'https://comprobantes/022', 12), (40.00, 'Efectivo', null, 17), (70.00, 'Yape', 'https://comprobantes/023', 21);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (76.00, 'Plin', 'https://comprobantes/024', 26), (90.00, 'Yape', 'https://comprobantes/027', 27), (100.00, 'Plin', 'https://comprobantes/028', 28), (60.00, 'Yape', 'https://comprobantes/029', 29), (70.00, 'Yape', 'https://comprobantes/030', 30);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (120.00, 'Plin', 'https://comprobantes/031', 31), (84.00, 'Tarjeta', 'https://comprobantes/032', 32), (76.00, 'Yape', 'https://comprobantes/033', 33), (70.00, 'Yape', 'https://comprobantes/034', 34), (66.00, 'Plin', 'https://comprobantes/035', 35);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (90.00, 'Yape', 'https://comprobantes/036', 36);

-- CANCELACIONES: se registran únicamente para reservas con estado cancelada.
INSERT INTO cancelacion (tipo_cancelacion, f_cancelacion, id_reserva) SELECT v.motivo, r.f_creacion + INTERVAL '30 minutes', r.id_reserva FROM (VALUES (4, 'No podré asistir'), (9, 'Problemas de salud'), (12, 'Problemas de transporte'), (17, 'Reservé por error'), (34, 'No podré asistir')) AS v(id_reserva, motivo) JOIN reserva r ON r.id_reserva = v.id_reserva WHERE r.estado = 'cancelada' ORDER BY r.id_reserva;

-- NOTIFICACIONES PARA ADMINISTRADORES: solicitudes pendientes de las sedes a su cargo.
INSERT INTO notificacion (tipo, titulo, mensaje, f_notificacion, leido, id_usuario) SELECT 'Solicitud', 'Nueva solicitud de reserva', format('La reserva #%s en %s para el %s a las %s está pendiente de revisión.', r.id_reserva, s.nombre, to_char(r.f_reserva, 'DD/MM/YYYY'), left(r.h_inicio::text, 5)), r.f_creacion, false, admin.id_usuario FROM reserva r JOIN sede_cancha sc ON sc.id_sede_cancha = r.id_sede_cancha JOIN sede s ON s.id_sede = sc.id_sede JOIN usuario admin ON admin.id_usuario = s.id_usuario WHERE r.creado_por = 'import.sql' AND r.estado = 'solicitada' AND admin.id_rol = 2 ORDER BY r.id_reserva;

-- NOTIFICACIONES PARA CLIENTES: recordatorios de sus reservas confirmadas.
INSERT INTO notificacion (tipo, titulo, mensaje, f_notificacion, leido, id_usuario) SELECT 'Recordatorio', 'Recordatorio de reserva', format('Tu reserva #%s en %s es el %s a las %s.', r.id_reserva, s.nombre, to_char(r.f_reserva, 'DD/MM/YYYY'), left(r.h_inicio::text, 5)), CURRENT_TIMESTAMP, false, cliente.id_usuario FROM reserva r JOIN sede_cancha sc ON sc.id_sede_cancha = r.id_sede_cancha JOIN sede s ON s.id_sede = sc.id_sede JOIN usuario cliente ON cliente.id_usuario = r.id_usuario WHERE r.creado_por = 'import.sql' AND r.estado = 'confirmada' AND cliente.id_rol = 1 AND (r.f_reserva + r.h_inicio) AT TIME ZONE 'America/Lima' > CURRENT_TIMESTAMP ORDER BY r.id_reserva;

-- NOTIFICACIONES PARA CLIENTES: cancelaciones y motivo de su propia reserva.
INSERT INTO notificacion (tipo, titulo, mensaje, f_notificacion, leido, id_usuario) SELECT 'Cancelación', 'Cancelación de reserva', format('Tu reserva #%s en %s para el %s fue cancelada. Motivo: %s.', r.id_reserva, s.nombre, to_char(r.f_reserva, 'DD/MM/YYYY'), c.tipo_cancelacion), c.f_cancelacion, false, cliente.id_usuario FROM cancelacion c JOIN reserva r ON r.id_reserva = c.id_reserva JOIN sede_cancha sc ON sc.id_sede_cancha = r.id_sede_cancha JOIN sede s ON s.id_sede = sc.id_sede JOIN usuario cliente ON cliente.id_usuario = r.id_usuario WHERE r.creado_por = 'import.sql' AND r.estado = 'cancelada' AND cliente.id_rol = 1 ORDER BY r.id_reserva;

-- INCIDENCIAS DEL USUARIO 6
INSERT INTO incidencia (tipo, asunto, descripcion, estado, respuesta_admin, f_creacion, f_respuesta, id_usuario) VALUES ('Tecnico', 'Error al visualizar mis reservas', 'Al ingresar a Mis reservas, algunas reservas confirmadas no aparecen en el historial.', 'Pendiente', NULL, CURRENT_TIMESTAMP, NULL, 6);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, respuesta_admin, f_creacion, f_respuesta, id_usuario) VALUES ('Instalaciones', 'Iluminacion defectuosa en cancha', 'Durante mi reserva en la sede Surco, dos reflectores dejaron de funcionar y dificultaron el partido.', 'Pendiente', NULL, CURRENT_TIMESTAMP, NULL, 6);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, respuesta_admin, f_creacion, f_respuesta, id_usuario) VALUES ('Preguntas', 'Consulta sobre cambio de horario', 'Quisiera saber si puedo modificar la fecha y hora de una reserva confirmada sin realizar un pago adicional.', 'Pendiente', NULL, CURRENT_TIMESTAMP, NULL, 6);