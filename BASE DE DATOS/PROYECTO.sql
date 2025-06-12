CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL
);

CREATE TABLE sneakers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    talla VARCHAR(10) NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE transacciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sneaker_id INT NOT NULL,
    comprador_id INT NOT NULL,
    fecha DATE,
    FOREIGN KEY (sneaker_id) REFERENCES sneakers(id),
    FOREIGN KEY (comprador_id) REFERENCES usuarios(id)
);

-- DCL

-- Crear usuario administrador
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin123';
GRANT ALL PRIVILEGES ON PROYECTO.* TO 'admin'@'localhost';
FLUSH PRIVILEGES;

-- Crear usuario para la aplicación (acceso limitado)
CREATE USER 'usuario'@'localhost' IDENTIFIED BY 'user123';
GRANT SELECT, INSERT, UPDATE ON PROYECTO.* TO 'usuario'@'localhost';
FLUSH PRIVILEGES;


--DML

-- Insertar usuarios

INSERT INTO usuarios (nombre, correo, contraseña) VALUES 
('Alejandro Martinez', 'alejandro@email.com', '1DAM'),
('Pablo Sogorb', 'pablo@email.com', '2DAM'),
('Adrian Sanchez', 'adrian@email.com', '3DAM'),
('Juan Carlos', 'juancarlos@email.com', '4DAM');

-- Insertar zapatillas
INSERT INTO sneakers (nombre, marca, talla, usuario_id) VALUES 
('Adidas Campus', 'Adidas', '40', 1),
('Jordan 1 Retro', 'Nike', '42', 2),
('New balance 520', 'New balance', '38', 3),
('Nike Air force 1', 'Nike', '44', 4);

-- Cambiar la talla de una zapatilla
UPDATE sneakers
SET talla = '43'
WHERE id = 1;

-- Eliminar una transacción concreta
DELETE FROM transacciones
WHERE id = 2;



-- Buscar todas las zapatillas de un vendedor
SELECT * FROM sneakers WHERE usuario_id = 1;

-- Buscar Marca
SELECT * FROM sneakers WHERE marca = 'Nike';

-- Buscar usuario
SELECT * FROM usuarios WHERE nombre = 'Alejandro Martinez';

