-- Insertar datos en la tabla tipo_documento
INSERT INTO `tipo_documento` (`id`, `nombre`, `siglas`) VALUES
(1, 'Cédula de ciudadanía', 'CC'),
(2, 'Tarjeta de identidad', 'TI'),
(3, 'Pasaporte', 'PA'),
(4, 'Cédula de extranjería', 'CE');

-- Insertar datos en la tabla municipio
INSERT INTO municipio (id, codigo, nombre) VALUES
(1, '05001', 'Medellín'),
(2, '05002', 'Abejorral'),
(3, '05004', 'Abriaquí'),
(4, '05021', 'Alejandría'),
(5, '05030', 'Amagá'),
(6, '05042', 'Amalfi'),
(7, '05051', 'Andes'),
(8, '05060', 'Angelópolis'),
(9, '05061', 'Angostura'),
(10, '05062', 'Anzá'),
(11, '05065', 'Apartadó'),
(12, '05079', 'Argelia'),
(13, '05085', 'Armenia'),
(14, '05086', 'Barbosa'),
(15, '05088', 'Belmira'),
(16, '05091', 'Bello'),
(17, '05094', 'Betania'),
(18, '05095', 'Betulia'),
(19, '05101', 'Ciudad Bolívar'),
(20, '05104', 'Caicedo');


-- Insertar datos en la tabla ubicacion
INSERT INTO sala (id, nombre) VALUES
(1, 'Auditorio principal'),
(2, 'Aula 1'),
(3, 'Aula 2'),
(4, 'Laboratorio de informática'),
(5, 'Biblioteca'),
(6, 'Cancha'),
(7, 'Salón de usos múltiples'),
(8, 'Aula virtual'),
(9, 'Plataforma online'),
(10, 'Centro comunitario'),
(11, 'Parque'),
(12, 'Colegio A'),
(13, 'Colegio B'),
(14, 'Colegio C'),
(15, 'Colegio D'),
(16, 'Colegio E'),
(17, 'Colegio F'),
(18, 'Colegio G'),
(19, 'Colegio H'),
(20, 'Colegio I');

-- Tabla: tipo_oferta
INSERT INTO tipo_oferta (id, nombre) VALUES 
(1, 'Curso'),
(2, 'Taller'),
(3, 'Seminario');

-- Tabla: categoria_oferta
INSERT INTO categoria_oferta (id, nombre) VALUES 
(1, 'Tecnología'),
(2, 'Artes'),
(3, 'Ciencias');

-- Tabla: cargo
INSERT INTO cargo (id, nombre) VALUES 
(1, 'Instructor'),
(2, 'Coordinador'),
(3, 'Asistente');

-- Tabla: tipo_beneficiario
INSERT INTO tipo_beneficiario (id, nombre) VALUES 
(1, 'Estudiante'),
(2, 'Desempleado'),
(3, 'Emprendedor');

-- Tabla: estado_civil
INSERT INTO estado_civil (id, nombre) VALUES 
(1, 'Soltero'),
(2, 'Casado'),
(3, 'Unión libre'),
(4, 'Divorciado');

-- Tabla: modalidad
INSERT INTO modalidad (id, nombre) VALUES 
(1, 'Presencial'),
(2, 'Virtual'),
(3, 'Mixta');

-- Tabla: poblacion_especial
INSERT INTO poblacion_especial (id, nombre) VALUES 
(1, 'Ninguna'),
(2, 'Víctima del conflicto'),
(3, 'Discapacidad'),
(4, 'Desplazado');

INSERT INTO pais (id, codigo, nombre) VALUES 
(1, '170', 'Colombia'),
(2, '280', 'Estados Unidos'),
(3, '025', 'España'),
(4, '950', 'México'),
(5, '164', 'Argentina');

INSERT INTO institucion (id, nombre, tipo_institucion) VALUE 
(NULL, "Universidad 1", "UNIVERSIDAD"),
(NULL, "Colegio 1", "COLEGIO"),
(NULL, "Empresa 1", "EMPRESA"),
(NULL, "Indep 1", "INDEPENDIENTE");