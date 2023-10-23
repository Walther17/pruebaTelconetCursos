package com.demo.olimacservices.service;


import java.util.List;
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
    
    public InscripcionCurso suscribirUsuarioACurso(Curso curso, Usuario usuario) {
        boolean existeInscripcion = inscripcionCursoRepository.existeInscripcion(curso.getId(), usuario.getId());
    
        if (existeInscripcion) {
            throw new RuntimeException("El usuario ya se encuentra inscrito en este curso.");
        } else {
            // Realiza la inscripción porque no se encontró una inscripción existente.
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