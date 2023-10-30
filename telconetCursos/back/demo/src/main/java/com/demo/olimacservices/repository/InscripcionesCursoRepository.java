package com.demo.olimacservices.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.demo.olimacservices.entidades.InscripcionCurso;


 
@Repository
public interface InscripcionesCursoRepository extends JpaRepository<InscripcionCurso, Integer>{
    
    @Query(value = "SELECT * FROM crear_inscripcion_curso(:p_curso_id, :p_consumidor_id);", nativeQuery = true)
    InscripcionCurso crearInscripcionCurso(
        @Param("p_curso_id") Integer cursoId,
        @Param("p_consumidor_id") Integer consumidorId
    );

    @Query(value = "SELECT * FROM obtener_inscripciones_de_curso(:p_curso_id);", nativeQuery = true)
    List<InscripcionCurso> getAllInscripcionesDeCurso(@Param("p_curso_id") Integer cursoId);

    @Query(value = "SELECT * FROM obtener_inscripcion_por_id(:p_inscripcion_id);", nativeQuery = true)
    InscripcionCurso obtenerInscripcionPorId(@Param("p_inscripcion_id") Integer inscripcionId);

    @Transactional
    @Query(value = "SELECT * FROM cancelar_inscripcion(:p_inscripcion_id);", nativeQuery = true)
    InscripcionCurso anularSubscripcion(@Param("p_inscripcion_id") Integer inscripcionId);

     @Transactional
    @Query(value = "SELECT * FROM suscribirse_nuevamente(:p_inscripcion_id);", nativeQuery = true)
    InscripcionCurso suscribirseDenuevo(@Param("p_inscripcion_id") Integer inscripcionId);

         
    @Query(value = "SELECT existe_inscripcion(:cursoId, :consumidorId)", nativeQuery = true)
    boolean existeInscripcion(@Param("cursoId") Integer cursoId, @Param("consumidorId") Integer consumidorId);

    ///
    @Query(value = "SELECT * FROM get_all_inscripciones();", nativeQuery = true)
    List<InscripcionCurso> getAllInscripciones();

}