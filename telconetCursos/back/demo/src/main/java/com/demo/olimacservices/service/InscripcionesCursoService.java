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
            InscripcionCurso inscripcion = inscripcionCursoRepository.crearInscripcionCurso(curso.getId(), usuario.getId());
            return inscripcion;
        }
    }
    
     public InscripcionCurso cancelarSuscripcion(Integer inscripcionId) {
        InscripcionCurso inscripcion = inscripcionCursoRepository.anularSubscripcion(inscripcionId);
        return inscripcion;
    }

    public List<InscripcionCurso> getAllInscripciones() {
        return inscripcionCursoRepository.getAllInscripciones();
    }

    public List<InscripcionCurso> getAllInscripcionesDeUnCurso(Integer cursoId) {
        return inscripcionCursoRepository.getAllInscripcionesDeCurso(cursoId);
    }

     public InscripcionCurso getAllInscripcionesById(Integer inscripcionId) {
        return inscripcionCursoRepository.obtenerInscripcionPorId(inscripcionId);
    }
}