    SELECT * from rol;
	SELECT * from usuario;
    SELECT * from usuario_roles;

---
-- funcion para retornar los roles
DROP FUNCTION obtener_roles()
--
CREATE OR REPLACE FUNCTION obtener_roles()
RETURNS TABLE (
    id INT,
    rol_nombre VARCHAR,
    fe_creacion TIMESTAMP,
    fe_actualizacion TIMESTAMP,
    estado VARCHAR
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        r.id,
        r.rol_nombre,
        r.fe_creacion,
        r.fe_actualizacion,
        r.estado
    FROM
        rol r;
END;
$$ LANGUAGE plpgsql;

--
SELECT * FROM obtener_roles();
-----------
--procedure
CREATE OR REPLACE PROCEDURE obtener_roles_procedure()
LANGUAGE plpgsql
AS $$
DECLARE
    result_table RECORD;
    result_cursor CURSOR FOR
        SELECT
            r.id,
            r.rol_nombre,
            r.fe_creacion,
            r.fe_actualizacion,
            r.estado
        FROM
            rol r;
BEGIN
    OPEN result_cursor;
    FETCH ALL FROM result_cursor INTO result_table;
    CLOSE result_cursor;
    
    -- Devolver el resultado
    RETURN QUERY
    SELECT
        result_table.id,
        result_table.rol_nombre,
        result_table.fe_creacion,
        result_table.fe_actualizacion,
        result_table.estado;
END;
$$;

---------
CALL obtener_roles_procedure();
call get_all_users();
---	
	INSERT INTO rol (rol_nombre, fe_creacion, fe_actualizacion, estado)
VALUES ('ROLE_ADMIN', NOW(), NOW(), 'A');

INSERT INTO rol (rol_nombre, fe_creacion, fe_actualizacion, estado)
VALUES ('ROLE_CREADOR', NOW(), NOW(), 'A');

INSERT INTO rol (rol_nombre, fe_creacion, fe_actualizacion, estado)
VALUES ('ROLE_CONSUMIDOR', NOW(), NOW(), 'A');

--
drop function insinsertar_usuario()
--- crear usuario
 CREATE OR REPLACE FUNCTION insertar_usuario(
    p_nombre VARCHAR,
    p_apellido VARCHAR,
    p_email VARCHAR,
    p_password VARCHAR,
    p_estado VARCHAR
) RETURNS usuario AS $$
DECLARE
    new_user usuario;
BEGIN
    -- Insertar el usuario con campos de auditoría
    INSERT INTO usuario (nombre, apellido, email, password, estado, fe_creacion, fe_actualizacion)
    VALUES (p_nombre, p_apellido, p_email, p_password, p_estado, now(), now())
    RETURNING * INTO new_user;

    RETURN new_user;
END;
$$ LANGUAGE plpgsql;

--
-- Ejemplo de llamada a la función insertar_usuario con datos de prueba
SELECT insertar_usuario('John', 'Doe', 'dev_@example.com', 'contraseña123', 'A');

---
CREATE OR REPLACE PROCEDURE get_all_users()
LANGUAGE plpgsql
AS $$
DECLARE
    result_set RECORD;
BEGIN
    FOR result_set IN (SELECT
        id,
        nombre,
        apellido,
        email,
        password,
        estado,
        fe_creacion,
        fe_actualizacion
    FROM
        usuario)
    LOOP
         RAISE NOTICE 'ID: %, Nombre: %, Apellido: %, Email: %', result_set.id, result_set.nombre, result_set.apellido, result_set.email;
    END LOOP;
END;
$$;

---
CREATE OR REPLACE PROCEDURE delete_user_by_id(user_id integer)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE usuario
    SET estado = NULL
    WHERE id = user_id;
END;
$$;

----
CREATE OR REPLACE PROCEDURE update_usuario(id integer, nombre text, apellido text, email text, password text)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE usuario
    SET
        nombre = COALESCE(nombre, usuario.nombre),
        apellido = COALESCE(apellido, usuario.apellido),
        email = COALESCE(email, usuario.email),
        password = COALESCE(password, usuario.password),
        fe_actualizacion = NOW()
    WHERE
        usuario.id = id;
END;
$$;
----
CREATE OR REPLACE PROCEDURE insert_usuario(nombre varchar, apellido varchar, email varchar, password varchar)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO usuario (nombre, apellido, email, password, estado, fe_creacion, fe_actualizacion)
    VALUES (nombre, apellido, email, password, 'A', NOW(), NOW());
END;
$$;
---
-- Llamando al procedimiento insert_usuario con datos inventados
BEGIN;
CALL insert_usuario('John', 'Doe', 'johndoe@example.com', 'password123');
COMMIT;

----
CREATE OR REPLACE PROCEDURE find_user_by_id(user_id INT)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT
        id,
        nombre,
        apellido,
        email,
        password,
        estado,
        fe_creacion,
        fe_actualizacion
    FROM usuario
    WHERE id = user_id;
END;
$$;
----
CREATE OR REPLACE function find_user_by_email(email_to_search VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT
        id,
        nombre,
        apellido,
        email,
        password,
        estado,
        fe_creacion,
        fe_actualizacion
    FROM usuario
    WHERE email = email_to_search;
END;
$$;
------------
CREATE OR REPLACE FUNCTION find_user_by_email(email_to_search VARCHAR)
RETURNS TABLE (
    usuario_id INT,
    nombre VARCHAR,
    apellido VARCHAR,
    email VARCHAR,
    password VARCHAR,
    estado VARCHAR,
    fe_creacion TIMESTAMP,
    fe_actualizacion TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        u.id AS usuario_id,
        u.nombre,
        u.apellido,
        u.email,
        u.password,
        u.estado,
        u.fe_creacion,
        u.fe_actualizacion
    FROM usuario u
    WHERE u.email = email_to_search;
END;
$$ LANGUAGE plpgsql;

-------------
SELECT * FROM find_user_by_email('correo@example.com');
-------------
SELECT id, apellido, email, estado, fe_actualizacion, fe_creacion, nombre, password
	FROM public.usuario;
	
	SELECT * from rol;
	SELECT * from usuario;
		SELECT * from usuario_roles;

---
-- funcion para retornar los roles
DROP FUNCTION obtener_roles()
--
CREATE OR REPLACE FUNCTION obtener_roles()
RETURNS TABLE (
    id INT,
    rol_nombre VARCHAR,
    fe_creacion TIMESTAMP,
    fe_actualizacion TIMESTAMP,
    estado VARCHAR
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        r.id,
        r.rol_nombre,
        r.fe_creacion,
        r.fe_actualizacion,
        r.estado
    FROM
        rol r;
END;
$$ LANGUAGE plpgsql;
--
SELECT * FROM obtener_roles();
-----------
--procedure
CREATE OR REPLACE PROCEDURE obtener_roles_procedure()
LANGUAGE plpgsql
AS $$
DECLARE
    result_table RECORD;
    result_cursor CURSOR FOR
        SELECT
            r.id,
            r.rol_nombre,
            r.fe_creacion,
            r.fe_actualizacion,
            r.estado
        FROM
            rol r;
BEGIN
    OPEN result_cursor;
    FETCH ALL FROM result_cursor INTO result_table;
    CLOSE result_cursor;
    
    -- Devolver el resultado
    RETURN QUERY
    SELECT
        result_table.id,
        result_table.rol_nombre,
        result_table.fe_creacion,
        result_table.fe_actualizacion,
        result_table.estado;
END;
$$;

---------
	CALL obtener_roles_procedure();
call get_all_users();
---	
	INSERT INTO rol (rol_nombre, fe_creacion, fe_actualizacion, estado)
VALUES ('ROLE_ADMIN', NOW(), NOW(), 'A');

INSERT INTO rol (rol_nombre, fe_creacion, fe_actualizacion, estado)
VALUES ('ROLE_CREADOR', NOW(), NOW(), 'A');

INSERT INTO rol (rol_nombre, fe_creacion, fe_actualizacion, estado)
VALUES ('ROLE_CONSUMIDOR', NOW(), NOW(), 'A');

--
drop function insinsertar_usuario()
CREATE OR REPLACE FUNCTION insertar_usuario(
    p_nombre VARCHAR,
    p_apellido VARCHAR,
    p_email VARCHAR,
    p_password VARCHAR,
    p_estado VARCHAR
) RETURNS usuario AS $$
DECLARE
    new_user usuario;
BEGIN
    -- Insertar el usuario con campos de auditoría
    INSERT INTO usuario (nombre, apellido, email, password, estado, fe_creacion, fe_actualizacion)
    VALUES (p_nombre, p_apellido, p_email, p_password, p_estado, now(), now())
    RETURNING * INTO new_user;

    RETURN new_user;
END;
$$ LANGUAGE plpgsql;


--
-- Ejemplo de llamada a la función insertar_usuario con datos de prueba
SELECT insertar_usuario('John', 'Doe', 'dev_____@example.com', 'contraseña123', 'A');

---
CREATE OR REPLACE PROCEDURE get_all_users()
LANGUAGE plpgsql
AS $$
DECLARE
    result_set RECORD;
BEGIN
    FOR result_set IN (SELECT
        id,
        nombre,
        apellido,
        email,
        password,
        estado,
        fe_creacion,
        fe_actualizacion
    FROM
        usuario)
    LOOP
         RAISE NOTICE 'ID: %, Nombre: %, Apellido: %, Email: %', result_set.id, result_set.nombre, result_set.apellido, result_set.email;
    END LOOP;
END;
$$;

---
CREATE OR REPLACE PROCEDURE delete_user_by_id(user_id integer)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE usuario
    SET estado = NULL
    WHERE id = user_id;
END;
$$;

----
CREATE OR REPLACE PROCEDURE update_usuario(id integer, nombre text, apellido text, email text, password text)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE usuario
    SET
        nombre = COALESCE(nombre, usuario.nombre),
        apellido = COALESCE(apellido, usuario.apellido),
        email = COALESCE(email, usuario.email),
        password = COALESCE(password, usuario.password),
        fe_actualizacion = NOW()
    WHERE
        usuario.id = id;
END;
$$;
----
CREATE OR REPLACE PROCEDURE insert_usuario(nombre varchar, apellido varchar, email varchar, password varchar)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO usuario (nombre, apellido, email, password, estado, fe_creacion, fe_actualizacion)
    VALUES (nombre, apellido, email, password, 'A', NOW(), NOW());
END;
$$;
---
-- Llamando al procedimiento insert_usuario con datos inventados
BEGIN;
CALL insert_usuario('John', 'Doe', 'johndoe@example.com', 'password123');
COMMIT;

----
CREATE OR REPLACE PROCEDURE find_user_by_id(user_id INT)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT
        id,
        nombre,
        apellido,
        email,
        password,
        estado,
        fe_creacion,
        fe_actualizacion
    FROM usuario
    WHERE id = user_id;
END;
$$;
----
CREATE OR REPLACE function find_user_by_email(email_to_search VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT
        id,
        nombre,
        apellido,
        email,
        password,
        estado,
        fe_creacion,
        fe_actualizacion
    FROM usuario
    WHERE email = email_to_search;
END;
$$;

--
------------
CREATE OR REPLACE FUNCTION find_user_by_email(email_to_search VARCHAR)
RETURNS TABLE (
    usuario_id INT,
    nombre VARCHAR,
    apellido VARCHAR,
    email VARCHAR,
    password VARCHAR,
    estado VARCHAR,
    fe_creacion TIMESTAMP,
    fe_actualizacion TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        u.id AS usuario_id,
        u.nombre,
        u.apellido,
        u.email,
        u.password,
        u.estado,
        u.fe_creacion,
        u.fe_actualizacion
    FROM usuario u
    WHERE u.email = email_to_search;
END;
$$ LANGUAGE plpgsql;

-------------
SELECT * FROM find_user_by_email('johe@example.com');
--------
CREATE OR REPLACE FUNCTION insertar_rol(nombre_rol VARCHAR, fe_creacion VARCHAR, fe_actualizacion VARCHAR, estado VARCHAR)
RETURNS INTEGER AS $$
DECLARE
    id_rol INTEGER;
BEGIN
    -- Verificar si el rol ya existe
    SELECT id INTO id_rol FROM rol WHERE nombre = nombre_rol;
    
    -- Si el rol no existe, insertarlo
    IF id_rol IS NULL THEN
        INSERT INTO rol (nombre, fe_creacion, fe_actualizacion, estado)
        VALUES (nombre_rol, fe_creacion, fe_actualizacion, estado) RETURNING id INTO id_rol;
    END IF;

    RETURN id_rol;
END;
$$ LANGUAGE plpgsql;
---------
--- crear curso
CREATE OR REPLACE FUNCTION crear_curso(
    p_nombre VARCHAR,
    p_creador_id INT,
    p_estado VARCHAR
) RETURNS curso AS $$
DECLARE
    new_curso curso;
BEGIN
    -- Insertar el curso con campos de auditoría
    INSERT INTO curso (nombre, creador_id, estado)
    VALUES (p_nombre, p_creador_id, 'A')
    RETURNING * INTO new_curso;

    RETURN new_curso;
END;
$$ LANGUAGE plpgsql;
----
--- get all

CREATE OR REPLACE FUNCTION getAllcursos()
RETURNS SETOF curso AS $$
BEGIN
    RETURN QUERY SELECT * FROM curso;
END;
$$ LANGUAGE plpgsql;

---
CREATE OR REPLACE FUNCTION actualizarCurso(p_curso_id INT, p_nombre VARCHAR, p_estado VARCHAR)
RETURNS VOID AS $$
BEGIN
    UPDATE curso SET nombre = p_nombre, estado = p_estado WHERE id = p_curso_id;
END;
$$ LANGUAGE plpgsql;
--
CREATE OR REPLACE FUNCTION obtener_curso_por_id(p_curso_id INT)
RETURNS curso AS $$
DECLARE
    curso_rec curso;
BEGIN
    SELECT * INTO curso_rec FROM curso WHERE id = p_curso_id;
    RETURN curso_rec;
END;
$$ LANGUAGE plpgsql;


---- Incripciones 

-- create 
CREATE OR REPLACE FUNCTION crear_inscripcion_curso(
    p_curso_id INT,
    p_consumidor_id INT
) RETURNS inscripcion_curso AS $$
DECLARE
    new_inscripcion inscripcion_curso;
BEGIN
    INSERT INTO inscripcion_curso (curso_id, consumidor_id, activo)
    VALUES (p_curso_id, p_consumidor_id, true)
    RETURNING * INTO new_inscripcion;

    RETURN new_inscripcion;
END;
$$ LANGUAGE plpgsql;
----
--- getAll 
-- Obtener todas las inscripciones de un curso específico
CREATE OR REPLACE FUNCTION obtener_inscripciones_de_curso(p_curso_id INT)
RETURNS SETOF inscripcion_curso AS $$
BEGIN
    RETURN QUERY
    SELECT *
    FROM inscripcion_curso
    WHERE curso_id = p_curso_id;
END;
$$ LANGUAGE plpgsql;

-----
--- getById
CREATE OR REPLACE FUNCTION obtener_inscripcion_por_id(p_inscripcion_id INT)
RETURNS inscripcion_curso AS $$
DECLARE
    inscripcion inscripcion_curso;
BEGIN
    SELECT *
    INTO inscripcion
    FROM inscripcion_curso
    WHERE id = p_inscripcion_id;

    RETURN inscripcion;
END;
$$ LANGUAGE plpgsql;
-----
---- update 
CREATE OR REPLACE FUNCTION actualizar_estado_inscripcion(
    p_inscripcion_id INT,
    p_activo BOOLEAN
) RETURNS inscripcion_curso AS $$
DECLARE
    inscripcion inscripcion_curso;
BEGIN
    UPDATE inscripcion_curso
    SET activo = p_activo
    WHERE id = p_inscripcion_id
    RETURNING * INTO inscripcion;

    RETURN inscripcion;
END;
$$ LANGUAGE plpgsql;

-----
---usuarios

CREATE OR REPLACE FUNCTION obtener_usuarios()
RETURNS TABLE (
    id INT,
    nombre VARCHAR,
    apellido VARCHAR,
    email VARCHAR,
    password VARCHAR,
    estado VARCHAR,
    fe_creacion TIMESTAMP,
    fe_actualizacion TIMESTAMP
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT
        u.id,
        u.nombre,
        u.apellido,
        u.email,
        u.password,
        u.estado,
        u.fe_creacion,
        u.fe_actualizacion
    FROM
        usuario u;
END;
$$;

SELECT * FROM obtener_usuarios();