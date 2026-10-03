-- =========================================================
-- SECURITY: ROLES Y USUARIOS (Spring Security + JWT)
-- =========================================================
INSERT INTO roles (id, nombre) VALUES (1, 'CLIENTE');
INSERT INTO roles (id, nombre) VALUES (2, 'ADMINISTRADOR');

-- Password: admin123 (BCrypt encoded)
INSERT INTO users (id, username, password, role_id) VALUES (1, 'admin', '$2a$12$mY87ekM5Y.vBC0x10edqbeZ/UzuhGLo1IZQSPldWHQ.ZHgUF6j2QG', 2);
-- Password: cliente123 (BCrypt encoded)
INSERT INTO users (id, username, password, role_id) VALUES (2, 'cliente', '$2a$12$Zhd/sqdj7vpQnNbW8tMm.OQCBHXnfUP9d3WOHIjQw1TIV1tolXJ5a', 1);

-- =========================================================
-- ROLES (business)
-- =========================================================
INSERT INTO rol (id_rol, nombre, descripcion) VALUES (1, 'CLIENTE', 'Usuario cliente del sistema');
INSERT INTO rol (id_rol, nombre, descripcion) VALUES (2, 'ADMINISTRADOR', 'Usuario administrador del sistema');

-- =========================================================
-- USUARIOS (id 1 = Juan Perez ya existia, se conserva)
-- =========================================================
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Juan', 'Perez', '12345678', '987654321', 'jperez@correo.com', '123456', true, 'import.sql', CURRENT_TIMESTAMP, 1);
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Maria', 'Gomez', '10000002', '900000002', 'mgomez@correo.com', '123456', true, 'import.sql', CURRENT_TIMESTAMP, 2);
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Carlos', 'Ramirez', '10000003', '900000003', 'cramirez@correo.com', '123456', true, 'import.sql', CURRENT_TIMESTAMP, 2);
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Lucia', 'Torres', '10000004', '900000004', 'ltorres@correo.com', '123456', true, 'import.sql', CURRENT_TIMESTAMP, 1);
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Diego', 'Flores', '10000005', '900000005', 'dflores@correo.com', '123456', true, 'import.sql', CURRENT_TIMESTAMP, 1);
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Ana', 'Castillo', '10000006', '900000006', 'acastillo@correo.com', '123456', true, 'import.sql', CURRENT_TIMESTAMP, 1);
INSERT INTO usuario (nombre, apellido, dni, telefono, correo, contrasena_hash, activo, creado_por, f_creacion, id_rol) VALUES ('Pedro', 'Huaman', '10000007', '900000007', 'phuaman@correo.com', '123456', false, 'import.sql', CURRENT_TIMESTAMP, 1);

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
INSERT INTO sede (nombre, distrito, direccion, url_ubicacion, id_usuario) VALUES ('Sede Surco', 'Surco', 'Av. Los Pinos 120', 'https://maps.google.com/?q=-12.1469,-76.9706', 2);
INSERT INTO sede (nombre, distrito, direccion, url_ubicacion, id_usuario) VALUES ('Sede San Miguel', 'San Miguel', 'Calle La Paz 345', 'https://maps.google.com/?q=-12.0821,-77.0035', 2);
INSERT INTO sede (nombre, distrito, direccion, url_ubicacion, id_usuario) VALUES ('Sede Miraflores', 'Miraflores', 'Av. Arequipa 1500', 'https://maps.google.com/?q=-12.1280,-77.0300', 3);
INSERT INTO sede (nombre, distrito, direccion, url_ubicacion, id_usuario) VALUES ('Sede Barranco', 'Barranco', 'Malecón 99', 'https://maps.google.com/?q=-12.1440,-77.0200', 3);

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
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-09-30', '07:00:00', '09:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 5, 9);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-09-30', '08:00:00', '10:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 6, 10);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-09-30', '12:00:00', '13:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 1, 7);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-09-30', '15:00:00', '17:00:00', 'cancelada', 'import.sql', CURRENT_TIMESTAMP, 7, 1);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-09-30', '19:00:00', '21:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 4, 3);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-02', '08:00:00', '10:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 5, 4);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-02', '11:00:00', '13:00:00', 'cancelada', 'import.sql', CURRENT_TIMESTAMP, 6, 9);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-02', '18:00:00', '20:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 7, 11);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-05', '09:00:00', '11:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 1, 1);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-05', '10:00:00', '12:00:00', 'rechazada', 'import.sql', CURRENT_TIMESTAMP, 4, 12);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-05', '14:00:00', '16:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 5, 5);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-05', '16:00:00', '18:00:00', 'cancelada', 'import.sql', CURRENT_TIMESTAMP, 6, 10);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-10', '07:00:00', '09:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 7, 9);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-10', '09:00:00', '11:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 1, 2);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-10', '13:00:00', '15:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 4, 4);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-10', '20:00:00', '22:00:00', 'rechazada', 'import.sql', CURRENT_TIMESTAMP, 5, 6);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-15', '08:00:00', '10:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 6, 1);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-15', '11:00:00', '13:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 7, 3);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-15', '15:00:00', '17:00:00', 'solicitada', 'import.sql', CURRENT_TIMESTAMP, 1, 9);
INSERT INTO reserva (f_reserva, h_inicio, h_fin, estado, creado_por, f_creacion, id_usuario, id_sede_cancha) VALUES ('2026-10-15', '18:00:00', '20:00:00', 'confirmada', 'import.sql', CURRENT_TIMESTAMP, 4, 10);

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
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (33.00, 'Plin', 'https://comprobantes/017', 23);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (38.00, 'Tarjeta', 'https://comprobantes/018', 24);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (30.00, 'Yape', 'https://comprobantes/019', 25);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (45.00, 'Tarjeta', 'https://comprobantes/020', 4);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (38.00, 'Yape', 'https://comprobantes/021', 9);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (55.00, 'Plin', 'https://comprobantes/022', 12);
INSERT INTO pago (monto_total, metodo, url_comprobante, id_reserva) VALUES (40.00, 'Efectivo', null, 17);

-- =========================================================
-- CANCELACIONES (solo reservas con estado cancelada)
-- tipos: No podre asistir | Problemas de salud | Problemas de transporte | Reserve por error
-- =========================================================
INSERT INTO cancelacion (tipo_cancelacion, f_cancelacion, id_reserva) VALUES ('No podré asistir', CURRENT_TIMESTAMP, 4);
INSERT INTO cancelacion (tipo_cancelacion, f_cancelacion, id_reserva) VALUES ('Problemas de salud', CURRENT_TIMESTAMP, 9);
INSERT INTO cancelacion (tipo_cancelacion, f_cancelacion, id_reserva) VALUES ('Problemas de transporte', CURRENT_TIMESTAMP, 12);
INSERT INTO cancelacion (tipo_cancelacion, f_cancelacion, id_reserva) VALUES ('Reservé por error', CURRENT_TIMESTAMP, 17);

-- =========================================================
-- NOTIFICACIONES
-- =========================================================
INSERT INTO notificacion (tipo, titulo, mensaje, f_notificacion, leido, id_usuario) VALUES ('Reserva', 'Reserva confirmada', 'Tu reserva del 30/09 en Sede Surco quedo confirmada', CURRENT_TIMESTAMP, true, 4);
INSERT INTO notificacion (tipo, titulo, mensaje, f_notificacion, leido, id_usuario) VALUES ('Reserva', 'Reserva cancelada', 'Tu reserva del 28/09 fue cancelada por que no podras asistir', CURRENT_TIMESTAMP, false, 7);
INSERT INTO notificacion (tipo, titulo, mensaje, f_notificacion, leido, id_usuario) VALUES ('Pago', 'Pago registrado', 'Registramos el pago de tu reserva en Sede Barranco', CURRENT_TIMESTAMP, true, 5);
INSERT INTO notificacion (tipo, titulo, mensaje, f_notificacion, leido, id_usuario) VALUES ('Reserva', 'Cancha no disponible', 'La cancha de tenis de Miraflores esta en mantenimiento', CURRENT_TIMESTAMP, false, 6);
INSERT INTO notificacion (tipo, titulo, mensaje, f_notificacion, leido, id_usuario) VALUES ('Promocion', 'Promo futsal', 'Reserva 2 horas de futsal y la tercera es gratis', CURRENT_TIMESTAMP, false, 1);

-- =========================================================
-- INCIDENCIAS
-- tipos: Tecnico | Instalaciones | Preguntas
-- estados: Abierta | En proceso | Cerrada
-- =========================================================
INSERT INTO incidencia (tipo, asunto, descripcion, estado, f_creacion, id_usuario) VALUES ('Técnico', 'Tablero de basket sin subir', 'El tablero de la cancha de basket de San Miguel no sube del todo', 'Abierta', CURRENT_TIMESTAMP, 4);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, f_creacion, id_usuario) VALUES ('Técnico', 'Punto de la red de voley', 'La red de la cancha de voley de Surco tiene un punto que cede', 'En proceso', CURRENT_TIMESTAMP, 6);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, f_creacion, id_usuario) VALUES ('Instalaciones', 'Pasto del futbol en mal estado', 'El pasto de la cancha de futbol de Surco tiene un roto que hay que reparar', 'Cerrada', CURRENT_TIMESTAMP, 7);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, respuesta_admin, f_creacion, f_respuesta, id_usuario) VALUES ('Instalaciones', 'Duchas sin agua caliente', 'Las duchas de la sede San Miguel salen con agua fria', 'Cerrada', 'Revisamos la caldera y ya se normalizo el agua caliente', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, respuesta_admin, f_creacion, f_respuesta, id_usuario) VALUES ('Preguntas', 'Horario de la sede Barranco', 'La sede Barranco cierra a las 10pm los domingos?', 'Cerrada', 'Si, la sede Barranco cierra a las 22:00 los domingos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
INSERT INTO incidencia (tipo, asunto, descripcion, estado, f_creacion, id_usuario) VALUES ('Preguntas', 'Uso de cancha sin pago', 'Se puede usar la cancha sin pagar la reserva del dia?', 'Abierta', CURRENT_TIMESTAMP, 5);
