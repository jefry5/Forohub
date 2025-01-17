# Forohub

Este es un Alura Challenge, en la cual se desarrolló una RestAPI sobre un foro  que permite realizar CRUD del topico, asimismo permite registrar un usuario y autenticarlo con JWT Token.

---

## Tabla de Contenidos

1. [Características](#características)
2. [Requisitos Previos](#requisitos-previos)
3. [Documentacion](#documentacion)
4. [Login](#Login)
5. [Propiedades](#Aplication-properties)

---

## Características

- CRUD para topico.
- Registrar y listar usuarios.
- Soporte para roles y permisos.
- Respuesta en formato JSON.

---

## Requisitos Previos

- **Lenguaje:** [Java 17]
- **Framework:** [Spring Boot]
- **Base de Datos:** [MySQL]

---

## Documentacion
Se utilizo Spring Doc para realizar la documentación de los request de la RestAPI. Acceda a la documentacion haciendo click.

---

## Login
Los siguientes usuarios se prueba en el endpoint **../login/autenticar**. Estos son para testear el rol de "ADMINISTRADOR" y "USUARIO".

```bash
ADMINISTRADOR:
- Correo: admin.test@forohub.com
- Contrasena: admin

USUARIO:
- Correo: user.test@forohub.com
- Contrasena: user
```

## Aplication Properties
Se deben definir las siguientes variables de entorno, así como crear la base de datos en MySQL.

```bash
-DB_NAME = forohub | "spring.datasource.url = .../forohub"
-DB_HOST
-DB_PASSWORD
-JWT_SECRET = 123456
```