package com.demo.olimacservices.security.controller;

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

import com.demo.olimacservices.security.entity.Curso;
import com.demo.olimacservices.security.service.CursoService;

@RestController
@RequestMapping("/cursos")
@CrossOrigin(origins = "*")
public class CursoController {
    

    @Autowired
    CursoService cursoService;

     @PostMapping("/crear")
    public ResponseEntity<Curso> crearCurso(@RequestBody Curso cursoDTO) {
        Curso curso = cursoService.crearCurso(cursoDTO.getNombre(), cursoDTO.getCreador());
        return ResponseEntity.status(HttpStatus.CREATED).body(curso);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Curso>> listarCursos() {
        List<Curso> cursos = cursoService.getAllCursos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerCursoPorId(@PathVariable Integer id) {
        Curso curso = cursoService.getCursoById(id);
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarCurso(@RequestBody Curso cursoDTO, @PathVariable Integer id) {
         cursoService.actualizarCurso(id, cursoDTO.getNombre(), cursoDTO.getEstado());
        return ResponseEntity.ok().build();
    }

}
