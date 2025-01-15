-- Crea la tabla perfil
CREATE TABLE perfil (
    id bigint not null auto_increment,
    nombre varchar(50) not null,
    primary key(id)
);

-- Crea la tabla usuario
CREATE TABLE usuario (
    id bigint not null auto_increment,
    nombre varchar(50) not null,
    correoElectronico varchar(80) not null,
    contrasena varchar(100) not null,
    perfil_id bigint not null,
    primary key(id),
    constraint fk_usuario_perfil_id foreign key(perfil_id) references perfil(id)
);

-- Crea la tabla curso
CREATE TABLE curso (
    id bigint not null auto_increment,
    nombre varchar(50) not null,
    categoria varchar(100) not null,
    primary key(id)
);

-- Crea la tabla topico
CREATE TABLE topico (
    id bigint not null auto_increment,
    titulo varchar(50) not null,
    mensaje varchar(300) not null,
    fechaCreacion datetime not null,
    status tinyint not null,
    autor_id bigint not null,
    curso_id bigint not null,
    respuestas varchar(300) not null,
    primary key(id),
    constraint fk_topico_autor_id foreign key(autor_id) references usuario(id),
    constraint fk_topico_curso_id foreign key(curso_id) references curso(id)
);

-- Crea la tabla respuesta
CREATE TABLE respuesta (
    id bigint not null auto_increment,
    mensaje varchar(300) not null,
    topico_id bigint not null,
    fechaCreacion datetime not null,
    autor_id bigint not null,
    solucion varchar(300) not null,
    primary key(id),
    constraint fk_respuesta_topico_id foreign key(topico_id) references topico(id),
    constraint fk_respuesta_autor_id foreign key(autor_id) references usuario(id)
);
