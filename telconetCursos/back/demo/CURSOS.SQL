
 SELECT * from rol;
	SELECT * from usuario;
    SELECT * from usuario_rol;


---------------------ROLES----------------------
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

-- INSERTAR ROLES

INSERT INTO rol (rol_nombre, fe_creacion, fe_actualizacion, estado)
VALUES ('ROLE_ADMIN', NOW(), NOW(), 'A');

INSERT INTO rol (rol_nombre, fe_creacion, fe_actualizacion, estado)
VALUES ('ROLE_CREADOR', NOW(), NOW(), 'A');

INSERT INTO rol (rol_nombre, fe_creacion, fe_actualizacion, estado)
VALUES ('ROLE_CONSUMIDOR', NOW(), NOW(), 'A');


--------------------------USUARIOS---------------------
drop function insertar_usuario()
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

------------ obtener_usuario_por_id
CREATE OR REPLACE FUNCTION obtener_usuario_por_id(p_usuario_id INT) 
RETURNS usuario AS $$
DECLARE
    usuario_encontrado usuario;
BEGIN
    SELECT * INTO usuario_encontrado FROM usuario WHERE id = p_usuario_id;
    RETURN usuario_encontrado;
END;
$$ LANGUAGE plpgsql;
------ Actualizar el usuario con campos de auditoría
CREATE OR REPLACE FUNCTION actualizar_usuario(
    p_usuario_id INT,
    p_nombre VARCHAR,
    p_apellido VARCHAR,
    p_email VARCHAR,
    p_password VARCHAR,
    p_estado VARCHAR
) RETURNS usuario AS $$
DECLARE
    usuario_actualizado usuario;
BEGIN
    UPDATE usuario
    SET
        nombre = p_nombre,
        apellido = p_apellido,
        email = p_email,
        password = p_password,
        estado = p_estado
    WHERE id = p_usuario_id
    RETURNING * INTO usuario_actualizado;

    RETURN usuario_actualizado;
END;
$$ LANGUAGE plpgsql;

-------
CREATE OR REPLACE FUNCTION obtener_todos_los_usuarios() 
RETURNS SETOF usuario AS $$
BEGIN
    RETURN QUERY
    SELECT * FROM usuario WHERE estado = 'A';
END;
$$ LANGUAGE plpgsql;
---
CREATE OR REPLACE FUNCTION eliminar_usuario(p_usuario_id INT)
RETURNS VOID AS $$
BEGIN
    UPDATE usuario
    SET estado = 'I'
    WHERE id = p_usuario_id;
END;
$$ LANGUAGE plpgsql;


------------------------------------CURSOS------------
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
CREATE OR REPLACE FUNCTION actualizarCurso(
    p_curso_id INT, 
    p_nombre VARCHAR, 
    p_estado VARCHAR)
RETURNS TABLE (
    id INT,
    nombre VARCHAR,
    estado VARCHAR
) AS $$
BEGIN
    UPDATE curso 
    SET nombre = p_nombre, estado = p_estado 
    WHERE id = p_curso_id
    RETURNING id, nombre, estado INTO id, nombre, estado;
    
    RETURN NEXT;
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
----------------

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
    VALUES (p_curso_id, p_consumidor_id, false)
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

---- Verifica si el usuario ya está inscrito en el curso
CREATE OR REPLACE FUNCTION existe_inscripcion(curso_id INT, consumidor_id INT)
RETURNS BOOLEAN AS $$
BEGIN
  IF EXISTS (SELECT 1 FROM inscripcion_curso WHERE curso_id = curso_id AND consumidor_id = consumidor_id) THEN
    RETURN TRUE;
  ELSE
    RETURN FALSE;
  END IF;
END;
$$ LANGUAGE plpgsql;
