
 SELECT * from rol;
	SELECT * from usuario;
    SELECT * from usuario_roles;


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
