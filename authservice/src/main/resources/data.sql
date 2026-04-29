INSERT INTO public.roles (
  name, 
  tenant_id, 
  description, 
  created_at, 
  updated_at
) VALUES
(
  'SUPER_ADMIN', 
  NULL, 
  'Global platform administrator with full access', 
  NOW(), 
  NOW()
),
(
  'TENANT_ADMIN', 
  NULL, 
  'Store administrator with full access to their tenant', 
  NOW(), 
  NOW()
),
(
  'SELLER',
   NULL, 
   'Sales/Operator with limited permissions in their tenant', 
   NOW(), 
   NOW()
),
(
  'CLIENT', 
  NULL, 
  'End customer who makes purchases', 
  NOW(), 
  NOW()
);

INSERT INTO public.permissions (resource, action, description, created_at) VALUES
('users', 'create', 'Create users', NOW()),
('users', 'read', 'View users', NOW()),
('users', 'update', 'Update users', NOW()),
('users', 'delete', 'Delete/Deactivate users', NOW()),
('roles', 'create', 'Create roles', NOW()),
('roles', 'read', 'View roles', NOW()),
('roles', 'update', 'Update roles', NOW()),
('roles', 'delete', 'Delete roles', NOW()),
('roles', 'assign', 'Assign roles to users', NOW()),
('permissions', 'create', 'Create permissions', NOW()),
('permissions', 'read', 'View permissions', NOW()),
('permissions', 'update', 'Update permissions', NOW()),
('permissions', 'delete', 'Delete permissions', NOW()),
('permissions', 'assign', 'Assign permissions to roles', NOW()),
('logs', 'read', 'View audit logs', NOW()),
('logs', 'export', 'Export logs', NOW()),
('profile', 'read', 'View own profile', NOW()),
('profile', 'update', 'Update own profile', NOW()),
('profile', 'change_password', 'Change own password', NOW()),
('orders', 'create', 'Create orders', NOW()),
('orders', 'read', 'View orders', NOW()),
('orders', 'update', 'Update orders', NOW()),
('orders', 'cancel', 'Cancel orders', NOW()),
('products', 'create', 'Create products', NOW()),
('products', 'read', 'View products', NOW()),
('products', 'update', 'Update products', NOW()),
('products', 'delete', 'Delete products', NOW()),
('analytics', 'read', 'View analytics dashboard', NOW()),
('analytics', 'export', 'Export reports', NOW());

INSERT INTO public.role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM public.roles r
CROSS JOIN public.permissions p
WHERE r.name = 'SUPER_ADMIN';

INSERT INTO public.role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM public.roles r
CROSS JOIN public.permissions p
WHERE r.name = 'TENANT_ADMIN'
  AND p.resource IN ('users', 'roles', 'orders', 'products', 'analytics', 'logs', 'profile');

INSERT INTO public.role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM public.roles r
CROSS JOIN public.permissions p
WHERE r.name = 'SELLER'
  AND (
    (p.resource = 'products' AND p.action IN ('read', 'update')) OR
    (p.resource = 'orders' AND p.action IN ('read', 'update')) OR
    (p.resource = 'profile')
  );

INSERT INTO public.role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM public.roles r
CROSS JOIN public.permissions p
WHERE r.name = 'CLIENT'
  AND (
    (p.resource = 'products' AND p.action = 'read') OR
    (p.resource = 'orders' AND p.action IN ('create', 'read', 'cancel')) OR
    (p.resource = 'profile')
  );

INSERT INTO public.users (id, active, auth_provider, created_at, email, email_verified, failed_login_attempts, image, last_login_at, last_login_ip, password, provider_id, tenant_id, updated_at, username) VALUES
--TENANT ADMIN
(1, TRUE, 'local', '2025-10-29 15:54:14.816229', 'n11@test.com', FALSE, 0, NULL, '2025-10-29 15:54:44.531979', NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, '35c4eace-5c84-4ba1-bb5d-01f08ffe8bb7', '2025-10-29 15:54:44.549965', 'n11'),
(2, TRUE, 'local', '2025-10-29 14:26:02.11157', 'us3@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, '5e2791b8-ba1f-4327-a878-1edbb1ac4db6', '2025-10-29 14:26:02.11157', 'ts3'),
(3, TRUE, 'local', '2025-10-29 14:26:10.786355', 'us4@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, 'be50ebc3-b836-43bf-9ec1-9b9d04bb4ed5', '2025-10-29 14:26:10.786355', 'ts4'),
(4, TRUE, 'local', '2025-10-29 14:26:18.310663', 'us5@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, 'cb996239-b054-4f5c-91bf-e23eaec2f1a2', '2025-10-29 14:26:18.310663', 'ts5'),
--store 2 clients
(5, TRUE, 'local', '2025-10-29 14:26:26.401614', 'us6@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, '5e2791b8-ba1f-4327-a878-1edbb1ac4db6', '2025-10-29 14:26:26.401614', 'ts6'),
(6, TRUE, 'local', '2025-10-29 14:26:34.619178', 'us7@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, '5e2791b8-ba1f-4327-a878-1edbb1ac4db6', '2025-10-29 14:26:34.619178', 'ts7'),
(7, TRUE, 'local', '2025-10-29 14:26:42.008598', 'us8@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, '5e2791b8-ba1f-4327-a878-1edbb1ac4db6', '2025-10-29 14:26:42.008598', 'ts8'),
(8, TRUE, 'local', '2025-10-29 14:26:50.872145', 'us9@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, '5e2791b8-ba1f-4327-a878-1edbb1ac4db6', '2025-10-29 14:26:50.872145', 'ts9'),
--store 3 clients
(9, TRUE, 'local', '2025-10-29 14:26:59.795574', 'us10@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, 'be50ebc3-b836-43bf-9ec1-9b9d04bb4ed5', '2025-10-29 14:26:59.795574', 'ts10'),
(10, TRUE, 'local', '2025-10-29 14:38:03.77677', 'us13@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, 'be50ebc3-b836-43bf-9ec1-9b9d04bb4ed5', '2025-10-29 14:38:03.77677', 'ts13'),
(11, TRUE, 'local', '2025-10-29 14:38:11.915486', 'us14@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, 'be50ebc3-b836-43bf-9ec1-9b9d04bb4ed5', '2025-10-29 14:38:11.915486', 'ts14'),
(12, TRUE, 'local', '2025-10-29 14:38:18.44895', 'us15@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, 'be50ebc3-b836-43bf-9ec1-9b9d04bb4ed5', '2025-10-29 14:38:18.44895', 'ts15'),
--store 4 clients
(13, TRUE, 'local', '2025-10-29 14:38:25.733805', 'us16@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, 'cb996239-b054-4f5c-91bf-e23eaec2f1a2', '2025-10-29 14:38:25.733805', 'ts16'),
(14, TRUE, 'local', '2025-10-29 14:39:20.315363', 'us17@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, 'cb996239-b054-4f5c-91bf-e23eaec2f1a2', '2025-10-29 14:39:20.315363', 'ts17'),
(15, TRUE, 'local', '2025-10-29 14:39:27.119933', 'us18@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, 'cb996239-b054-4f5c-91bf-e23eaec2f1a2', '2025-10-29 14:39:27.119933', 'ts18'),
(16, TRUE, 'local', '2025-10-29 14:39:35.020058', 'us19@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, 'cb996239-b054-4f5c-91bf-e23eaec2f1a2', '2025-10-29 14:39:35.020058', 'ts19'),
(17, TRUE, 'local', '2025-10-29 14:39:42.905226', 'us20@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, 'cb996239-b054-4f5c-91bf-e23eaec2f1a2', '2025-10-29 14:39:42.905226', 'ts20'),
--store 1 clients
(18, TRUE, 'local', '2025-10-29 14:37:51.988359', 'us11@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, '35c4eace-5c84-4ba1-bb5d-01f08ffe8bb7', '2025-10-29 14:37:51.988359', 'ts11'),
(19, TRUE, 'local', '2025-10-29 14:37:57.815367', 'us12@test.com', FALSE, 0, NULL, NULL, NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, '35c4eace-5c84-4ba1-bb5d-01f08ffe8bb7', '2025-10-29 14:37:57.815367', 'ts12'),
(20, TRUE, 'local', '2025-10-29 14:25:47.432157', 'us1@test.com', FALSE, 0, NULL, '2025-11-04 21:24:31.101621', NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, '35c4eace-5c84-4ba1-bb5d-01f08ffe8bb7', '2025-11-04 21:24:31.144513', 'ts1'),
(21, TRUE, 'local', '2025-10-29 14:25:55.304905', 'us2@test.com', FALSE, 0, NULL, '2025-10-29 15:43:06.782731', NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, '35c4eace-5c84-4ba1-bb5d-01f08ffe8bb7', '2025-10-29 15:43:06.795803', 'ts2'),
-- admin
(22, TRUE, 'local', '2025-10-17 03:54:19.099289', 'admin123@test.com', FALSE, 0, NULL, '2025-11-08 03:35:29.573841', NULL, '$2a$12$vqPG9ip5nvloJSDv5OZ3Q.nuZy3FG8ga1PQV62wx0.f/n30QrXSkS', NULL, NULL, '2025-11-08 03:35:29.727369', 'admin123');


INSERT INTO user_role(user_id, role_id)
VALUES
(22, 1),
(1, 2),
(2, 2),
(3, 2),
(4, 2),
(5, 4),
(6, 4),
(7, 4),
(8, 4),
(9, 4),
(10, 4),
(11, 4),
(12, 4),
(13, 4),
(14, 4),
(15, 4),
(16, 4),
(17, 4),
(18, 4),
(19, 4),
(20, 4),
(21, 4),
(22, 4);