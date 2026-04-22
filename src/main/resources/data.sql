-- MERCADO USA
INSERT INTO certificaciones (nombre, mercado, descripcion)
SELECT 'Certificación Orgánica (USDA Organic)', 'USA', 'Es la más solicitada. Obligatoria para etiquetar café como Organic en EE. UU.'
WHERE NOT EXISTS (SELECT 1 FROM certificaciones WHERE nombre = 'Certificación Orgánica (USDA Organic)');

INSERT INTO certificaciones (nombre, mercado, descripcion)
SELECT 'Comercio Justo (Fairtrade)', 'USA', 'Común en café de especialidad y compras corporativas con responsabilidad social.'
WHERE NOT EXISTS (SELECT 1 FROM certificaciones WHERE nombre = 'Comercio Justo (Fairtrade)');

INSERT INTO certificaciones (nombre, mercado, descripcion)
SELECT 'Rainforest Alliance', 'USA', 'Valorada por consumidores de grandes cadenas (Starbucks, Whole Foods).'
WHERE NOT EXISTS (SELECT 1 FROM certificaciones WHERE nombre = 'Rainforest Alliance' AND mercado = 'USA');


-- MERCADO EUROPA (UE)
INSERT INTO certificaciones (nombre, mercado, descripcion)
SELECT 'Certificación Orgánica (EU Organic)', 'Europa', 'Obligatoria para el mercado verde europeo. Estándar de la hoja verde.'
WHERE NOT EXISTS (SELECT 1 FROM certificaciones WHERE nombre = 'Certificación Orgánica (EU Organic)');

INSERT INTO certificaciones (nombre, mercado, descripcion)
SELECT 'UTZ / Rainforest Alliance', 'Europa', 'Estándar de entrada para tostadores masivos en Europa. Exige trazabilidad total.'
WHERE NOT EXISTS (SELECT 1 FROM certificaciones WHERE nombre = 'UTZ / Rainforest Alliance');

INSERT INTO certificaciones (nombre, mercado, descripcion)
SELECT 'Indicación Geográfica Protegida (IGP)', 'Europa', 'Protege la denominación de origen y el precio frente a imitaciones.'
WHERE NOT EXISTS (SELECT 1 FROM certificaciones WHERE nombre = 'Indicación Geográfica Protegida (IGP)');


-- MERCADO ASIA
INSERT INTO certificaciones (nombre, mercado, descripcion)
SELECT 'Certificación Orgánica (JAS)', 'Asia', 'Específica para Japón. Inspecciones muy rigurosas y alto estándar de calidad.'
WHERE NOT EXISTS (SELECT 1 FROM certificaciones WHERE nombre = 'Certificación Orgánica (JAS)');

INSERT INTO certificaciones (nombre, mercado, descripcion)
SELECT 'UTZ (Buenas Prácticas)', 'Asia', 'Valorada en Japón y Corea para asegurar Buenas Prácticas Agrícolas (BPA).'
WHERE NOT EXISTS (SELECT 1 FROM certificaciones WHERE nombre = 'UTZ (Buenas Prácticas)' AND mercado = 'Asia');


-- MERCADO NACIONAL / LIBRE COMERCIO
INSERT INTO certificaciones (nombre, mercado, descripcion)
SELECT 'Certificación Orgánica (Sagarpa México)', 'Nacional', 'Base legal nacional para exportar posteriormente como orgánico.'
WHERE NOT EXISTS (SELECT 1 FROM certificaciones WHERE nombre = 'Certificación Orgánica (Sagarpa México)');

INSERT INTO certificaciones (nombre, mercado, descripcion)
SELECT 'Comercio Justo (Nacional)', 'Nacional', 'Ayuda a cooperativas de pequeños productores a organizarse y obtener financiamiento.'
WHERE NOT EXISTS (SELECT 1 FROM certificaciones WHERE nombre = 'Comercio Justo (Nacional)');

INSERT INTO certificaciones (nombre, mercado, descripcion)
SELECT 'Denominación de Origen (DO)', 'Nacional', 'Protege legalmente al Café Chiapas, Veracruz y Pluma (Oaxaca).'
WHERE NOT EXISTS (SELECT 1 FROM certificaciones WHERE nombre = 'Denominación de Origen (DO)');
