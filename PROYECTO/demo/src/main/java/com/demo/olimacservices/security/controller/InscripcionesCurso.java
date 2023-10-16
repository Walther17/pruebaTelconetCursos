package com.demo.olimacservices.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.olimacservices.security.entity.InscripcionCurso;
import com.demo.olimacservices.security.service.InscripcionesCursoService;

@RestController
@RequestMapping("/inscripcion")
@CrossOrigin(origins = "*")
public class InscripcionesCurso {
    
    @Autowired
    InscripcionesCursoService inscripcionesCursoService;

     @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<InscripcionCurso>> obtenerTodasLasInscripcionesDeCurso(@PathVariable Integer cursoId) {
        List<InscripcionCurso> inscripciones = inscripcionesCursoService.obtenerTodasLasInscripcionesDeCurso(cursoId);
        return ResponseEntity.ok(inscripciones);
    }

    @PutMapping("/suscribir/{inscripcionId}")
    public ResponseEntity<InscripcionCurso> suscribirUsuarioACurso(@PathVariable Integer inscripcionId) {
        InscripcionCurso inscripcion = inscripcionesCursoService.suscribirUsuarioACurso(inscripcionId);
        return ResponseEntity.ok(inscripcion);
    }

    @PutMapping("/cancelar/{inscripcionId}")
    public ResponseEntity<InscripcionCurso> cancelarSuscripcion(@PathVariable Integer inscripcionId) {
        InscripcionCurso inscripcion = inscripcionesCursoService.cancelarSuscripcion(inscripcionId);
        return ResponseEntity.ok(inscripcion);
    }

}
