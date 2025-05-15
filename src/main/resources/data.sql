-- Insertar datos en la tabla tipo_documento
INSERT INTO `tipo_documento` (`id`, `nombre`, `siglas`) VALUES
(1, 'Cédula de ciudadanía', 'CC'),
(2, 'Tarjeta de identidad', 'TI'),
(3, 'Pasaporte', 'PA'),
(4, 'Cédula de extranjería', 'CE');

-- Insertar datos en la tabla municipio
INSERT INTO municipio (id, codigo, nombre) VALUES
(1, '05001', 'Medellín'),
(2, '05002', 'Cúcuta');


-- Insertar datos en la tabla ubicacion
INSERT INTO sala (id, nombre) VALUES
(1, 'Sala CNC'),
(2, 'Sala Coworking'),
(3, 'Sala drones'),
(4, 'Salon GIDIS');
-- Tabla: tipo_oferta
INSERT INTO tipo_oferta (id, nombre) VALUES 
(1, 'Curso'),
(2, 'Taller'),
(3, 'Seminario');

-- Tabla: categoria_oferta
INSERT INTO categoria_oferta (id, nombre) VALUES 
(1, 'STEAM School'),
(2, 'STEAM Young');

-- Tabla: cargo
INSERT INTO cargo (id, nombre) VALUES 
(1, 'Auxiliar'),
(2, 'Mantenimiento'),
(3, 'Asistente');

-- Tabla: tipo_beneficiario
INSERT INTO tipo_beneficiario (id, nombre) VALUES 
(1, 'Estudiante'),
(2, 'Egresado'),
(3, 'Profesor');

-- Tabla: estado_civil
INSERT INTO estado_civil (id, nombre) VALUES 
(1, 'Soltero'),
(2, 'Casado'),
(3, 'Unión libre'),
(4, 'Divorciado');

-- Tabla: modalidad
INSERT INTO modalidad (id, nombre) VALUES 
(1, 'Practicante'),
(2, 'Pasante'),
(3, 'Becatrabajo');

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