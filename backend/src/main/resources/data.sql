INSERT INTO usuarios (nombre, correo, contrasena, rol, active)
SELECT 'admin', 'admin@gmail.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOnu', 'ADMIN', true
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE correo = 'admin@gmail.com');
