--
select * from usuario
select * from rol
select * from curso
select * from inscripcion_curso
SELECT * FROM USUARIO_ROL

---------------------ROLES----------------------
-- funcion para retornar los roles
--
CREATE OR REPLACE FUNCTION get_All_Roles() 
RETURNS SETOF rol AS $$
BEGIN
    RETURN QUERY
    SELECT * FROM rol WHERE estado = 'A';
END;
$$ LANGUAGE plpgsql;

--
-----------ROLES

-- INSERTAR ROLES
 
----
 CREATE TYPE rolnombre AS ENUM (
  'ROLE_ADMIN',
  'ROLE_CONSUMIDOR',
  'ROLE_CREADOR'
);

CREATE OR REPLACE FUNCTION crear_rol(
		p_nombre RolNombre,
		p_estado VARCHAR
	) RETURNS rol AS $$
	DECLARE
		new_rol rol;
	BEGIN
		INSERT INTO rol (rol_nombre, estado, fe_creacion, fe_actualizacion)
		VALUES (p_nombre, p_estado, now(), now())
		RETURNING * INTO new_rol;

		RETURN new_rol;
	END;
	$$ LANGUAGE plpgsql;
	-
SELECT * from crear_rol('ROLE_CONSUMIDOR'::RolNombre, 'A');
-------------
CREATE OR REPLACE FUNCTION actualizar_rol(
    p_rol_id INT,
    p_nombre VARCHAR,
    p_estado VARCHAR
) RETURNS rol AS $$
DECLARE
    rol_actualizado rol;
BEGIN
    UPDATE rol
    SET
        rol_nombre = p_nombre,
        estado = p_estado,
		 fe_actualizacion = NOW() 	
    WHERE id = p_rol_id
    RETURNING * INTO rol_actualizado;

    RETURN rol_actualizado;
END;
$$ LANGUAGE plpgsql;
---
CREATE OR REPLACE FUNCTION eliminar_rol(p_rol_id INT)
RETURNS rol AS $$
DECLARE
    rol_eliminado rol;
BEGIN
    UPDATE rol
    SET estado = 'I'
    WHERE id = p_rol_id

    RETURNING * INTO rol_eliminado;

    RETURN rol_eliminado;
END;
$$ LANGUAGE plpgsql;
--
----------
--------------------------USUARIOS---------------------
--- crear usuario
CREATE OR REPLACE FUNCTION crear_usuario_con_rol(
    p_nombre VARCHAR,
    p_apellido VARCHAR,
    p_email VARCHAR,
    p_password VARCHAR,
    p_estado VARCHAR,
    p_rol_nombre VARCHAR  -- Nombre del rol
) RETURNS usuario AS $$
DECLARE
    new_user usuario;
    rol_id INT;
BEGIN
    -- Verificar si el rol existe
    SELECT id INTO rol_id FROM rol WHERE rol_nombre = p_rol_nombre;

    -- Si el rol existe, crea el usuario
    IF rol_id IS NOT NULL THEN
        -- Crear el usuario
        INSERT INTO usuario (nombre, apellido, email, password, estado, fe_creacion, fe_actualizacion)
        VALUES (p_nombre, p_apellido, p_email, p_password, p_estado, now(), now())
        RETURNING * INTO new_user;

        -- Asignar el rol al usuario en la tabla intermedia
        INSERT INTO usuario_rol (usuario_id, rol_id)
        VALUES (new_user.id, rol_id);

        RETURN new_user;
    ELSE
        -- El rol no existe, lanza una excepción o devuelve un mensaje de error
        RAISE EXCEPTION 'El rol % no existe.', p_rol_nombre;
        RETURN NULL;
    END IF;
END;
$$ LANGUAGE plpgsql;
------
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
------ Actualizar el usuario  
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
        estado = p_estado,
         fe_actualizacion = NOW() 	
    WHERE id = p_usuario_id
    RETURNING * INTO usuario_actualizado;

    RETURN usuario_actualizado;
END;
$$ LANGUAGE plpgsql;

------- Get All
CREATE OR REPLACE FUNCTION obtener_todos_los_usuarios() 
RETURNS SETOF usuario AS $$
BEGIN
    RETURN QUERY
    SELECT * FROM usuario WHERE estado = 'A';
END;
$$ LANGUAGE plpgsql;
--- Eliminar usuario
CREATE OR REPLACE FUNCTION eliminar_usuario(p_usuario_id INT)
RETURNS usuario AS $$
DECLARE
    usuario_eliminado usuario;
BEGIN
    UPDATE usuario
    SET estado = 'I'
    WHERE id = p_usuario_id

     RETURNING * INTO usuario_eliminado;

    RETURN usuario_eliminado;
END;
$$ LANGUAGE plpgsql;


------------------------------------CURSOS------------
----crear
CREATE OR REPLACE FUNCTION crear_curso(
    p_nombre VARCHAR,
    p_creador_id INT,
    p_estado VARCHAR
) RETURNS curso AS $$
DECLARE
    new_curso curso;
BEGIN
    INSERT INTO curso (nombre, creador_id, estado, fe_creacion, fe_actualizacion)
    VALUES (p_nombre, p_creador_id, 'A', now(), now())
    RETURNING * INTO new_curso;

    RETURN new_curso;
END;
$$ LANGUAGE plpgsql;
----
--- get all

CREATE OR REPLACE FUNCTION getAllcursos()
RETURNS SETOF curso AS $$
BEGIN
    RETURN QUERY SELECT * FROM curso WHERE estado = 'A';
END;
$$ LANGUAGE plpgsql;

-- Get curso por id
CREATE OR REPLACE FUNCTION obtener_curso_por_id(p_curso_id INT)
RETURNS curso AS $$
DECLARE
    curso_rec curso;
BEGIN
    SELECT * INTO curso_rec FROM curso WHERE id = p_curso_id;
    RETURN curso_rec;
END;
$$ LANGUAGE plpgsql;
-- Eliminar curso

CREATE OR REPLACE FUNCTION eliminar_curso(p_curso_id INT)
RETURNS curso AS $$
DECLARE
    curso_eliminado curso;
BEGIN
    UPDATE curso
    SET estado = 'I'
    WHERE id = p_curso_id

     RETURNING * INTO curso_eliminado;

    RETURN curso_eliminado;
END;
$$ LANGUAGE plpgsql;
-- NUEVO UPDATE CURSOS
CREATE OR REPLACE FUNCTION update_curso(
    p_curso_id INT,
    p_curso_nombre VARCHAR,
    p_estado VARCHAR)
RETURNS curso AS $$
DECLARE
    curso_actualizado curso;
BEGIN
    UPDATE curso 
    SET nombre = p_curso_nombre, estado = p_estado ,fe_actualizacion = NOW() 	
    WHERE id = p_curso_id

     RETURNING * INTO curso_actualizado;

    RETURN curso_actualizado;
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
    INSERT INTO inscripcion_curso (curso_id, consumidor_id, activo, fe_creacion, fe_actualizacion)
    VALUES (p_curso_id, p_consumidor_id, true, now(), now())
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
---- eliminar 
CREATE OR REPLACE FUNCTION cancelar_inscripcion(p_inscripcion_id INT)
RETURNS inscripcion_curso AS $$
DECLARE
    anular_inscripcion inscripcion_curso;
BEGIN
    UPDATE inscripcion_curso
    SET activo = false, fe_actualizacion = NOW() 	
    WHERE id = p_inscripcion_id
    RETURNING * INTO anular_inscripcion;

    RETURN anular_inscripcion;
END;
$$ LANGUAGE plpgsql;


---- Verifica si el usuario ya está inscrito en el curso
CREATE OR REPLACE FUNCTION existe_inscripcion(p_curso_id INT, p_consumidor_id INT)
RETURNS BOOLEAN AS $$
BEGIN
  IF EXISTS (SELECT 1 FROM inscripcion_curso WHERE curso_id = p_curso_id AND consumidor_id = p_consumidor_id) THEN
    RETURN TRUE;
  ELSE
    RETURN FALSE;
  END IF;
END;
$$ LANGUAGE plpgsql;


--- get All

CREATE OR REPLACE FUNCTION get_all_inscripciones() 
RETURNS SETOF inscripcion_curso AS $$
BEGIN
    RETURN QUERY
    SELECT * FROM inscripcion_curso WHERE activo = true;
END;
$$ LANGUAGE plpgsql;
--

CREATE OR REPLACE FUNCTION suscribirse_nuevamente(
      p_inscripcion_id INT
)
RETURNS inscripcion_curso AS $$
DECLARE
    inscribirse_nuevamente inscripcion_curso;
BEGIN
    UPDATE inscripcion_curso 
    SET activo = true ,fe_actualizacion = NOW() 	
    WHERE id = p_inscripcion_id

     RETURNING * INTO inscribirse_nuevamente;

    RETURN inscribirse_nuevamente;
END;
$$ LANGUAGE plpgsql;


-------------------------------------------------------

-- LOGIN
 {
      
        "email": "dev_@gmail.com",
        "password": "new"
        
}

-- USUARIOS
-- create
{
    "nombre": "Walther",
    "apellido": "Olivo",
    "email": "dev___@gmail.com",
    "password": "xd",
    "estado": "A",
    "roles": [
        {
        "rolNombre": "ROLE_CREADOR"
        }
    ]
}
-- update
{
        "nombre": "Walther",
        "apellido": "Olivo",
        "email": "dev_@gmail.com",
        "password": "new",
        "estado": "A"
}
-- DELETE
http://localhost:8080/usuarios/eliminar/2
-- CURSOS
-- CREATE
{
    "nombre": "Spring Boddot",
    "creador": {
        "id": 1
    },
    "estado": "A"
}
--- UPDATE
{
    "nombre": "curso actualizado sprinGGGGGGGGGGGGGGGG",
    "estado": "A"
}
--  