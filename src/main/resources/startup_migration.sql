INSERT INTO role(id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

INSERT INTO users (id, username, email, password)
VALUES (1, 'user', 'user@gmail.com', '$2a$12$PR3XTxCMnKkVqKi0WXSj8.0Il68Lg60Y5yWiDHntODL9E.PGvJXJ.'),
       (2, 'admin', 'admin@gmail.com', '$2a$12$Y0d0Y78DfI9g4JlPmwc64.VSYR4OBpqYho0NoDZPTBKqswI69.9KG');

INSERT INTO users_roles(users_id, roles_id)
VALUES (1, 1),
       (2, 2);

