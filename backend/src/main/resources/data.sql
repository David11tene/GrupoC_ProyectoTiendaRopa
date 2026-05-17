INSERT INTO usuarios (nombre, correo, contrasena, rol, active)
SELECT 'admin', 'admin@gmail.com', 'admin', 'ADMIN', true
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE correo = 'admin@gmail.com');
