package com.demo.olimacservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.olimacservices.entidades.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

    @Query(value = "SELECT * FROM getAllcursos();", nativeQuery = true)
    List<Curso> getAllCursos();

    @Query(value = "SELECT * FROM crear_curso(:nombre, :creador_id, :estado);", nativeQuery = true)
    public Curso crearCurso(
            @Param("nombre") String nombre,
            @Param("creador_id") Integer creador_id,
            @Param("estado") String estado);

    @Query(value = "SELECT * FROM obtener_curso_por_id(:curso_id);", nativeQuery = true)
    Curso getById(@Param("curso_id") Integer cursoId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "SELECT * FROM actualizarCurso(:curso_id, :nombre, :estado)")
    Curso actualizarCurso(@Param("curso_id") Integer cursoId, @Param("nombre") String nombre, @Param("estado") String estado);
    

    @Query("SELECT COUNT(*) FROM Curso c WHERE c.creador.id = :creadorId AND c.estado = 'A'")
    int countCursosActivosByCreadorId(@Param("creadorId") Integer creadorId);

}