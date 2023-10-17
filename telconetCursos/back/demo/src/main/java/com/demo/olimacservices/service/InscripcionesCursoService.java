package com.demo.olimacservices.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.olimacservices.entidades.InscripcionCurso;
import com.demo.olimacservices.repository.InscripcionCursoRepository;

@Service
@Transactional
public class InscripcionesCursoService {
    
    @Autowired
    InscripcionCursoRepository inscripcionCursoRepository;

     public InscripcionCurso suscribirUsuarioACurso(Integer inscripcionId) {
        InscripcionCurso inscripcion = inscripcionCursoRepository.actualizarEstadoInscripcion(inscripcionId, true);
        return inscripcion;
    }

     public InscripcionCurso cancelarSuscripcion(Integer inscripcionId) {
        InscripcionCurso inscripcion = inscripcionCursoRepository.actualizarEstadoInscripcion(inscripcionId, false);
        return inscripcion;
    }

    public List<InscripcionCurso> obtenerTodasLasInscripcionesDeCurso(Integer cursoId) {
        return inscripcionCursoRepository.getAllInscripcionesDeCurso(cursoId);
    }
}