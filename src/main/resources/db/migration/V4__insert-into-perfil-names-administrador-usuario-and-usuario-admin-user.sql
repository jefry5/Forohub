INSERT INTO perfil (id, nombre)
VALUES
    (1, "ADMINISTRADOR"),
    (2, "USUARIO");

INSERT INTO usuario (id, nombre, correo_electronico, contrasena, perfil_id)
VALUES
    (1, "admin", "admin.test@forohub.com", "$2a$10$5QgKgXSnqwwR14bWJUBz8uoADq.o7VKh5Ddu9mZg8hoU9EEV6rHde", 1),
    (2, "user", "user.test@forohub.com", "$2a$10$t4CA6Qk8RrULHbALZkHgLubqRc2YTzGERhzPxf/S26Iq7d8z9xdsW", 2);



