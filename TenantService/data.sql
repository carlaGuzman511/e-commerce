INSERT INTO dbo.store (
    active, created_at, updated_at,
    currency, default_language,
    city, country, info, name, owner_info, owner_name,
    postal_code, state, street, tenant_id, timezone
)
VALUES
(true, NOW() - INTERVAL '30 days', NOW(),
 'BOB', 'es-BO', 'Cochabamba', 'Bolivia',
 'Tienda principal en Cochabamba', 'Kiosco Central', 'info@kioscocentral.bo', 'María Pérez',
 '0001', 'Cochabamba', 'Av. Blanco Galindo #100', 'tenant_001', 'America/La_Paz'),

(true, NOW() - INTERVAL '29 days', NOW(),
 'BOB', 'es-BO', 'La Paz', 'Bolivia',
 'Sucursal en La Paz', 'La Paz Market', 'contact@lapazmarket.bo', 'José Rojas',
 '0002', 'La Paz', 'Calle 16 de Obrajes #222', 'tenant_002', 'America/La_Paz'),

(true, NOW() - INTERVAL '28 days', NOW(),
 'BOB', 'es-BO', 'Santa Cruz', 'Bolivia',
 'Sucursal en Santa Cruz', 'Santa Cruz Shop', 'ventas@sczshop.bo', 'Ana Fernández',
 '0003', 'Santa Cruz', 'Av. San Martín #789', 'tenant_003', 'America/La_Paz'),

(true, NOW() - INTERVAL '27 days', NOW(),
 'BOB', 'es-BO', 'Sucre', 'Bolivia',
 'Sucursal en Sucre', 'Sucre Store', 'contact@sucrestore.bo', 'Carlos Romero',
 '0004', 'Chuquisaca', 'Calle Junín #150', 'tenant_004', 'America/La_Paz'),

(true, NOW() - INTERVAL '26 days', NOW(),
 'BOB', 'es-BO', 'Tarija', 'Bolivia',
 'Sucursal en Tarija', 'Tarija Market', 'ventas@tarijamarket.bo', 'Elena Vargas',
 '0005', 'Tarija', 'Av. Las Américas #500', 'tenant_005', 'America/La_Paz'),

(true, NOW() - INTERVAL '25 days', NOW(),
 'BOB', 'es-BO', 'Oruro', 'Bolivia',
 'Sucursal en Oruro', 'Oruro Shop', 'info@oruromarket.bo', 'Luis Molina',
 '0006', 'Oruro', 'Calle Adolfo Mier #321', 'tenant_006', 'America/La_Paz'),

(true, NOW() - INTERVAL '24 days', NOW(),
 'BOB', 'es-BO', 'Potosí', 'Bolivia',
 'Sucursal en Potosí', 'Potosí Store', 'ventas@potosistore.bo', 'Lucía Gutiérrez',
 '0007', 'Potosí', 'Av. Universitaria #210', 'tenant_007', 'America/La_Paz'),

(true, NOW() - INTERVAL '23 days', NOW(),
 'BOB', 'es-BO', 'Beni', 'Bolivia',
 'Sucursal en Beni', 'Beni Market', 'contact@benimarket.bo', 'Diego Castro',
 '0008', 'Beni', 'Calle Bolívar #440', 'tenant_008', 'America/La_Paz'),

(true, NOW() - INTERVAL '22 days', NOW(),
 'BOB', 'es-BO', 'Pando', 'Bolivia',
 'Sucursal en Pando', 'Pando Shop', 'info@pandoshop.bo', 'Sandra López',
 '0009', 'Pando', 'Av. Central #78', 'tenant_009', 'America/La_Paz'),

(true, NOW() - INTERVAL '21 days', NOW(),
 'BOB', 'es-BO', 'Trinidad', 'Bolivia',
 'Sucursal en Trinidad', 'Trinidad Store', 'ventas@trinidadstore.bo', 'Héctor Jiménez',
 '0010', 'Beni', 'Calle Suárez #321', 'tenant_010', 'America/La_Paz');

INSERT INTO dbo.branch (
    active, created_at, updated_at,
    street, city, country, postal_code, state,
    name, manager_name, manager_phone, opening_hours, closing_hours,
    store_id, tenant_id
)
VALUES
(true, NOW() - INTERVAL '30 days', NOW(),
 'Av. América #101', 'Cochabamba', 'Bolivia', '1001', 'Cochabamba',
 'Sucursal Norte', 'Laura Fernández', '+59170010001', '08:00', '20:00',
 1, 'tenant_001'),

(true, NOW() - INTERVAL '29 days', NOW(),
 'Av. Ayacucho #222', 'Cochabamba', 'Bolivia', '1002', 'Cochabamba',
 'Sucursal Sur', 'Miguel Rojas', '+59170010002', '08:00', '20:00',
 1, 'tenant_001'),

(true, NOW() - INTERVAL '28 days', NOW(),
 'Calle 16 de Obrajes #333', 'La Paz', 'Bolivia', '2001', 'La Paz',
 'Sucursal Central', 'Paola Vargas', '+59170020001', '08:00', '20:00',
 2, 'tenant_002'),

(true, NOW() - INTERVAL '27 days', NOW(),
 'Av. Busch #404', 'La Paz', 'Bolivia', '2002', 'La Paz',
 'Sucursal Miraflores', 'Sergio Guzmán', '+59170020002', '08:00', '20:00',
 2, 'tenant_002'),

(true, NOW() - INTERVAL '26 days', NOW(),
 'Av. San Martín #500', 'Santa Cruz', 'Bolivia', '3001', 'Santa Cruz',
 'Sucursal Equipetrol', 'Andrea Molina', '+59170030001', '08:00', '20:00',
 3, 'tenant_003'),

(true, NOW() - INTERVAL '25 days', NOW(),
 'Calle Velasco #600', 'Santa Cruz', 'Bolivia', '3002', 'Santa Cruz',
 'Sucursal Centro', 'Pablo Gutiérrez', '+59170030002', '08:00', '20:00',
 3, 'tenant_003'),

(true, NOW() - INTERVAL '24 days', NOW(),
 'Calle Junín #150', 'Sucre', 'Bolivia', '4001', 'Chuquisaca',
 'Sucursal Central', 'Lucía Rojas', '+59170040001', '08:00', '20:00',
 4, 'tenant_004'),

(true, NOW() - INTERVAL '23 days', NOW(),
 'Av. Las Américas #500', 'Tarija', 'Bolivia', '5001', 'Tarija',
 'Sucursal Este', 'Carlos Molina', '+59170050001', '08:00', '20:00',
 5, 'tenant_005'),

(true, NOW() - INTERVAL '22 days', NOW(),
 'Calle Adolfo Mier #321', 'Oruro', 'Bolivia', '6001', 'Oruro',
 'Sucursal Norte', 'Sandra López', '+59170060001', '08:00', '20:00',
 6, 'tenant_006'),

(true, NOW() - INTERVAL '21 days', NOW(),
 'Av. Universitaria #210', 'Potosí', 'Bolivia', '7001', 'Potosí',
 'Sucursal Sur', 'Luis Gutiérrez', '+59170070001', '08:00', '20:00',
 7, 'tenant_007');

 INSERT INTO dbo.branch (
    active, created_at, updated_at,
    tenant_id, store_id,
    name, manager_name, manager_phone,
    opening_hours, closing_hours,
    street, city, postal_code, state, country
)
VALUES
-- tenant_001 (Cochabamba)
(true, NOW() - INTERVAL '30 days', NOW(), 'tenant_001', 1, 'Sucursal Norte', 'Laura Fernández', '+59170010001', '08:00', '20:00', 'Av. América #101', 'Cochabamba', '1001', 'Cochabamba', 'Bolivia'),
(true, NOW() - INTERVAL '29 days', NOW(), 'tenant_001', 1, 'Sucursal Sur', 'Carlos Gutiérrez', '+59170010002', '09:00', '21:00', 'Av. Blanco Galindo #222', 'Cochabamba', '1002', 'Cochabamba', 'Bolivia'),
(true, NOW() - INTERVAL '28 days', NOW(), 'tenant_001', 1, 'Sucursal Este', 'María Vargas', '+59170010003', '08:30', '19:30', 'Av. Melchor Pérez #303', 'Cochabamba', '1003', 'Cochabamba', 'Bolivia'),
(true, NOW() - INTERVAL '27 days', NOW(), 'tenant_001', 1, 'Sucursal Oeste', 'José Flores', '+59170010004', '08:00', '20:00', 'Calle Punata #404', 'Cochabamba', '1004', 'Cochabamba', 'Bolivia'),
(true, NOW() - INTERVAL '26 days', NOW(), 'tenant_001', 1, 'Sucursal Central', 'Paola Ramírez', '+59170010005', '09:00', '21:00', 'Av. Ayacucho #505', 'Cochabamba', '1005', 'Cochabamba', 'Bolivia'),

-- tenant_002 (La Paz)
(true, NOW() - INTERVAL '25 days', NOW(), 'tenant_002', 2, 'Sucursal Sopocachi', 'Luis Salazar', '+59170020001', '08:00', '20:00', 'Av. 20 de Octubre #123', 'La Paz', '2001', 'La Paz', 'Bolivia'),
(true, NOW() - INTERVAL '24 days', NOW(), 'tenant_002', 2, 'Sucursal Miraflores', 'Sandra Rojas', '+59170020002', '09:00', '21:00', 'Calle Villalobos #456', 'La Paz', '2002', 'La Paz', 'Bolivia'),
(true, NOW() - INTERVAL '23 days', NOW(), 'tenant_002', 2, 'Sucursal Obrajes', 'Fernando Quispe', '+59170020003', '08:30', '19:30', 'Calle 16 de Obrajes #22', 'La Paz', '2003', 'La Paz', 'Bolivia'),
(true, NOW() - INTERVAL '22 days', NOW(), 'tenant_002', 2, 'Sucursal Alto San Pedro', 'Marta Arce', '+59170020004', '08:00', '20:00', 'Av. Montes #789', 'La Paz', '2004', 'La Paz', 'Bolivia'),
(true, NOW() - INTERVAL '21 days', NOW(), 'tenant_002', 2, 'Sucursal Central', 'Raúl Díaz', '+59170020005', '09:00', '21:00', 'Av. Camacho #101', 'La Paz', '2005', 'La Paz', 'Bolivia'),

-- tenant_003 (Santa Cruz)
(true, NOW() - INTERVAL '20 days', NOW(), 'tenant_003', 3, 'Sucursal Equipetrol', 'Daniela Méndez', '+59170030001', '08:00', '20:00', 'Av. San Martín #500', 'Santa Cruz', '3001', 'Santa Cruz', 'Bolivia'),
(true, NOW() - INTERVAL '19 days', NOW(), 'tenant_003', 3, 'Sucursal Sur', 'Hugo Flores', '+59170030002', '09:00', '21:00', 'Av. Santos Dumont #2100', 'Santa Cruz', '3002', 'Santa Cruz', 'Bolivia'),
(true, NOW() - INTERVAL '18 days', NOW(), 'tenant_003', 3, 'Sucursal Norte', 'Lorena García', '+59170030003', '08:30', '19:30', 'Av. Banzer #300', 'Santa Cruz', '3003', 'Santa Cruz', 'Bolivia'),
(true, NOW() - INTERVAL '17 days', NOW(), 'tenant_003', 3, 'Sucursal Este', 'Ricardo Ortiz', '+59170030004', '08:00', '20:00', 'Calle Ichilo #450', 'Santa Cruz', '3004', 'Santa Cruz', 'Bolivia'),
(true, NOW() - INTERVAL '16 days', NOW(), 'tenant_003', 3, 'Sucursal Central', 'Silvia Pinto', '+59170030005', '09:00', '21:00', 'Av. Cañoto #99', 'Santa Cruz', '3005', 'Santa Cruz', 'Bolivia'),

-- tenant_004 (Sucre)
(true, NOW() - INTERVAL '15 days', NOW(), 'tenant_004', 4, 'Sucursal Central', 'Marco Ruiz', '+59170040001', '08:00', '20:00', 'Calle Junín #321', 'Sucre', '4001', 'Chuquisaca', 'Bolivia'),
(true, NOW() - INTERVAL '14 days', NOW(), 'tenant_004', 4, 'Sucursal Norte', 'Rosa Pérez', '+59170040002', '09:00', '21:00', 'Av. Venezuela #210', 'Sucre', '4002', 'Chuquisaca', 'Bolivia'),
(true, NOW() - INTERVAL '13 days', NOW(), 'tenant_004', 4, 'Sucursal Sur', 'David Ríos', '+59170040003', '08:30', '19:30', 'Calle Calvo #456', 'Sucre', '4003', 'Chuquisaca', 'Bolivia'),
(true, NOW() - INTERVAL '12 days', NOW(), 'tenant_004', 4, 'Sucursal Este', 'Nadia Ramos', '+59170040004', '08:00', '20:00', 'Av. Germán Mendoza #33', 'Sucre', '4004', 'Chuquisaca', 'Bolivia'),
(true, NOW() - INTERVAL '11 days', NOW(), 'tenant_004', 4, 'Sucursal Oeste', 'Mario Flores', '+59170040005', '09:00', '21:00', 'Calle Bolívar #777', 'Sucre', '4005', 'Chuquisaca', 'Bolivia'),

-- tenant_005 (Tarija)
(true, NOW() - INTERVAL '10 days', NOW(), 'tenant_005', 5, 'Sucursal Central', 'Elena Vargas', '+59170050001', '08:00', '20:00', 'Av. Las Américas #501', 'Tarija', '5001', 'Tarija', 'Bolivia'),
(true, NOW() - INTERVAL '9 days', NOW(), 'tenant_005', 5, 'Sucursal Norte', 'Jorge Salinas', '+59170050002', '09:00', '21:00', 'Av. Panamericana #210', 'Tarija', '5002', 'Tarija', 'Bolivia'),
(true, NOW() - INTERVAL '8 days', NOW(), 'tenant_005', 5, 'Sucursal Sur', 'Maribel Flores', '+59170050003', '08:30', '19:30', 'Calle Madrid #56', 'Tarija', '5003', 'Tarija', 'Bolivia'),
(true, NOW() - INTERVAL '7 days', NOW(), 'tenant_005', 5, 'Sucursal Este', 'Nelson Roca', '+59170050004', '08:00', '20:00', 'Av. Tomatitas #77', 'Tarija', '5004', 'Tarija', 'Bolivia'),
(true, NOW() - INTERVAL '6 days', NOW(), 'tenant_005', 5, 'Sucursal Oeste', 'Gabriela Rojas', '+59170050005', '09:00', '21:00', 'Calle Colón #88', 'Tarija', '5005', 'Tarija', 'Bolivia');

