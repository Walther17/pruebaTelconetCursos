package com.demo.olimacservices.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.olimacservices.Message.Mensaje;
import com.demo.olimacservices.entidades.InscripcionCurso;
import com.demo.olimacservices.service.InscripcionesCursoService;

@RestController
@RequestMapping("/inscripcion")
@CrossOrigin(origins = "*")
public class InscripcionesCursoController {
    
    @Autowired
    InscripcionesCursoService inscripcionesCursoService;
 
     @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<InscripcionCurso>> obtenerTodasLasInscripcionesDeUnCurso(@PathVariable Integer cursoId) {
        try {
            List<InscripcionCurso> inscripciones = inscripcionesCursoService.getAllInscripcionesDeUnCurso(cursoId);
            return ResponseEntity.ok(inscripciones);
            
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
          return new ResponseEntity<>( HttpStatus.NOT_FOUND);
      }
    }

    @PostMapping("/suscribir")
    public ResponseEntity<?> suscribirUsuarioACurso(@RequestBody InscripcionCurso request) {
        try {
            InscripcionCurso inscripcion = inscripcionesCursoService.suscribirUsuarioACurso(request.getCurso(), request.getConsumidor());
            return ResponseEntity.ok(inscripcion);
            
        }  catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
          return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.NOT_FOUND);
      }
    }
    
    @PutMapping("/cancelar/{inscripcionId}")
    public ResponseEntity<?> cancelarSuscripcion(@PathVariable Integer inscripcionId) {
        try {
            InscripcionCurso inscripcion = inscripcionesCursoService.cancelarSuscripcion(inscripcionId);
            return ResponseEntity.ok(inscripcion);
            
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
          return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.NOT_FOUND);
      }
    }


   /////////
   @GetMapping("/todos")
    public ResponseEntity<?> getAllInscripciones() {
        try {
            List<InscripcionCurso> inscripcionCursos = inscripcionesCursoService.getAllInscripciones();
            return new ResponseEntity<>(inscripcionCursos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Mensaje("No se encontraron inscripciones"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{inscripcionId}")
    public ResponseEntity<?> obtenerTodasLasInscripcionesById(@PathVariable Integer inscripcionId) {
        try {
              InscripcionCurso inscripciones = inscripcionesCursoService.getAllInscripcionesById(inscripcionId);
            return ResponseEntity.ok(inscripciones);
        }  catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
          return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.NOT_FOUND);
      }
    }
}