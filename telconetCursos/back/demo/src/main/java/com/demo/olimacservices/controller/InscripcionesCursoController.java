package com.demo.olimacservices.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.olimacservices.entidades.InscripcionCurso;
import com.demo.olimacservices.service.InscripcionesCursoService;

@RestController
@RequestMapping("/inscripcion")
@CrossOrigin(origins = "*")
public class InscripcionesCursoController {
    
    @Autowired
    InscripcionesCursoService inscripcionesCursoService;
 
     @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<InscripcionCurso>> obtenerTodasLasInscripcionesDeCurso(@PathVariable Integer cursoId) {
        List<InscripcionCurso> inscripciones = inscripcionesCursoService.obtenerTodasLasInscripcionesDeCurso(cursoId);
        return ResponseEntity.ok(inscripciones);
    }

    @PostMapping("/suscribir")
    public ResponseEntity<InscripcionCurso> suscribirUsuarioACurso(@RequestBody InscripcionCurso request) {
        InscripcionCurso inscripcion = inscripcionesCursoService.suscribirUsuarioACurso(request.getCurso(), request.getConsumidor());
        return ResponseEntity.ok(inscripcion);
    }
    
    @PutMapping("/cancelar/{inscripcionId}")
    public ResponseEntity<InscripcionCurso> cancelarSuscripcion(@PathVariable Integer inscripcionId) {
        InscripcionCurso inscripcion = inscripcionesCursoService.cancelarSuscripcion(inscripcionId);
        return ResponseEntity.ok(inscripcion);
    }

}