package com.demo.olimacservices.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.demo.olimacservices.entidades.Curso;
import com.demo.olimacservices.repository.CursoRepository;
import com.demo.olimacservices.security.entity.Usuario;

@Service
@Transactional
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;

    public Curso crearCurso(String nombre, Usuario creador) {
        if (creador == null) {
            throw new IllegalArgumentException("El usuario creador no puede ser nulo.");
        }    
    
        int cursosActivos  = cursoRepository.countCursosActivosByCreadorId(creador.getId());
    
        if (cursosActivos >= 2) {
            throw new IllegalArgumentException("El usuario creador ha alcanzado el límite de cursos activos.");
        }
    
        return cursoRepository.crearCurso(nombre, creador.getId(), "A");
    }
    

    public List<Curso> getAllCursos() {
        List<Curso> curso = cursoRepository.getAllCursos();
        if (curso != null) {
            return curso;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay datos");
        }
    }

    public Curso actualizarCurso(Integer cursoId, String nombre, String estado) {
        Curso cursoExistente = cursoRepository.actualizarCurso(cursoId, nombre, estado);

        if (cursoExistente != null) {
            return cursoExistente;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado");
        }
    }

    public Curso getCursoById(Integer id) {
        return cursoRepository.getById(id);
    }

}