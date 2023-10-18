package com.demo.olimacservices.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.olimacservices.entidades.Curso;
import com.demo.olimacservices.entidades.InscripcionCurso;
import com.demo.olimacservices.repository.InscripcionesCursoRepository;
import com.demo.olimacservices.security.entity.Usuario;

@Service
@Transactional
public class InscripcionesCursoService {
    
    @Autowired
    InscripcionesCursoRepository inscripcionCursoRepository;

    // public InscripcionCurso suscribirUsuarioACurso(Curso curso, Usuario usuario) {
    //     // Verificar si ya existe una inscripción para este usuario y curso
    //     Optional<InscripcionCurso> existingInscripcion = inscripcionCursoRepository.findExistingInscripcion(curso.getId(), usuario.getId());
    
    //     if (existingInscripcion.isPresent()) {
    //         // El usuario ya está suscrito, puedes manejarlo según tus requerimientos, por ejemplo, lanzar una excepción
    //         throw new RuntimeException("El usuario ya se encuentra inscrito en este curso.");
    //     } else {
    //         // El usuario no está suscrito, proceder con la creación de la inscripción
    //         InscripcionCurso inscripcion = inscripcionCursoRepository.crearInscripcionCurso(curso.getId(), usuario.getId(), false);
    //         return inscripcion;
    //     }
    // }
    
    public InscripcionCurso suscribirUsuarioACurso(Curso curso, Usuario usuario) {
        Optional<InscripcionCurso> existingInscripcion = inscripcionCursoRepository.findExistingInscripcion(curso.getId(), usuario.getId());
    
        if (existingInscripcion.isPresent()) {
            throw new RuntimeException("El usuario ya se encuentra inscrito en este curso.");
        } else {
            InscripcionCurso inscripcion = inscripcionCursoRepository.crearInscripcionCurso(curso.getId(), usuario.getId(), false);
            return inscripcion;
        }
    }
    
     public InscripcionCurso cancelarSuscripcion(Integer inscripcionId) {
        InscripcionCurso inscripcion = inscripcionCursoRepository.actualizarEstadoInscripcion(inscripcionId, false);
        return inscripcion;
    }

    public List<InscripcionCurso> obtenerTodasLasInscripcionesDeCurso(Integer cursoId) {
        return inscripcionCursoRepository.getAllInscripcionesDeCurso(cursoId);
    }
}