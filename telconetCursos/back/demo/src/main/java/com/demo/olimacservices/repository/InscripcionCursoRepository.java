package com.demo.olimacservices.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.olimacservices.entidades.InscripcionCurso;


 
@Repository
public interface InscripcionCursoRepository extends JpaRepository<InscripcionCurso, Integer>{
    
    @Query(value = "SELECT * FROM crear_inscripcion_curso(:cursoId, :consumidorId);", nativeQuery = true)
    InscripcionCurso crearInscripcionCurso(@Param("cursoId") Integer cursoId, @Param("consumidorId") Integer consumidorId);

    @Query(value = "SELECT * FROM obtener_inscripciones_de_curso(:curso_id);", nativeQuery = true)
    List<InscripcionCurso> getAllInscripcionesDeCurso(@Param("curso_id") Integer cursoId);

    @Query(value = "SELECT * FROM obtener_inscripcion_por_id(:inscripcionId);", nativeQuery = true)
    InscripcionCurso obtenerInscripcionPorId(@Param("inscripcionId") Integer inscripcionId);

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM actualizar_estado_inscripcion(:inscripcionId, :activo);", nativeQuery = true)
    InscripcionCurso actualizarEstadoInscripcion(@Param("inscripcionId") Integer inscripcionId, @Param("activo") Boolean activo);

         
}