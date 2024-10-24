CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    contrasena VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS naves (
    id_nave BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    numero_motores BIGINT NOT NULL
    );


INSERT INTO naves (nombre, numero_motores) VALUES ( 'Existent', 2);
INSERT INTO usuarios (nombre, contrasena) VALUES ('Existent', '$2a$12$c9k6Y3BvFQGv8HccyJPUluRwhgOLDcxculhDaGVtskuqUz80e2XeG'); -- Contraseña: existent123