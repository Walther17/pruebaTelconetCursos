package com.demo.olimacservices.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.demo.olimacservices.entidades.Curso;
import com.demo.olimacservices.entidades.InscripcionCurso;
import com.demo.olimacservices.repository.CursoRepository;
import com.demo.olimacservices.repository.InscripcionesCursoRepository;
import com.demo.olimacservices.security.entity.Usuario;
import com.demo.olimacservices.security.repository.UsuarioRepository;

@Service
@Transactional
public class InscripcionesCursoService {
    
    @Autowired
    InscripcionesCursoRepository inscripcionCursoRepository;
 
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public InscripcionCurso suscribirUsuarioACurso(Curso curso, Usuario usuario) {
        try {
            
            boolean existeInscripcion = inscripcionCursoRepository.existeInscripcion(curso.getId(), usuario.getId());
         

             if (!cursoRepository.existsById(curso.getId())) {
                throw new IllegalArgumentException("El ID del curso no existe");
            }if(!usuarioRepository.existsById(usuario.getId())){
                throw new IllegalArgumentException("El ID del usuario no existe.");
            }if (existeInscripcion) {
                throw new RuntimeException("El usuario ya se encuentra inscrito en este curso.");
            }
             else {
                InscripcionCurso inscripcion = inscripcionCursoRepository.crearInscripcionCurso(curso.getId(), usuario.getId());
                return inscripcion;
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage().toString(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage().toString(), e.getCause());
        }
    }
    
    public InscripcionCurso cancelarSuscripcion(Integer inscripcionId) {
        try {
            // Verificar si el ID de inscripción es válido
            if (inscripcionId != null && inscripcionId > 0) {
                InscripcionCurso inscripcion = inscripcionCursoRepository.anularSubscripcion(inscripcionId);
                if (inscripcion != null) {
                    return inscripcion;
                } else {
                    throw new IllegalArgumentException("La inscripción no existe.");
                }
            } else {
                throw new IllegalArgumentException("ID de inscripción no válido.");
            }
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e.getCause());
        }
    }

    public InscripcionCurso suscribirseNuevamente(Integer inscripcionId) {
        try {
            // Verificar si el ID de inscripción es válido
            if (inscripcionId != null && inscripcionId > 0) {
                InscripcionCurso inscripcion = inscripcionCursoRepository.suscribirseDenuevo(inscripcionId);
                if (inscripcion != null) {
                    return inscripcion;
                } else {
                    throw new IllegalArgumentException("La inscripción no existe.");
                }
            } else {
                throw new IllegalArgumentException("ID de inscripción no válido.");
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e.getCause());
        }
    }
    
    public List<InscripcionCurso> getAllInscripciones() {
        try {
            List<InscripcionCurso> inscripcionCursos = inscripcionCursoRepository.getAllInscripciones();
            if(inscripcionCursos.isEmpty()){
                throw new IllegalArgumentException("No hay ninguna subscripcion");
            }
            return inscripcionCursoRepository.getAllInscripciones();
            
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException(ex.getMessage().toString(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage().toString(), e.getCause());
        }
    }

       public List<InscripcionCurso> getAllInscripcionesDeUnCurso(Integer cursoId) {
        try {
            if (!cursoRepository.existsById(cursoId)) {
                throw new IllegalArgumentException("El ID del curso no existe");
            }

            List<InscripcionCurso> inscripciones = inscripcionCursoRepository.getAllInscripcionesDeCurso(cursoId);

            if (inscripciones.isEmpty()) {
                throw new IllegalArgumentException("No hay inscripciones para el curso seleccionado");
            }

            return inscripciones;
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error interno del servidor: " + e.getMessage(), e.getCause());
        }
    }
    

     public InscripcionCurso getInscripcionesById(Integer inscripcionId) {
        try {
            InscripcionCurso inscripcionCurso = inscripcionCursoRepository.obtenerInscripcionPorId(inscripcionId);
            if(inscripcionCurso == null){
                throw new IllegalArgumentException("No existe una isncripción con el id proporcionado.");
            }
            return inscripcionCursoRepository.obtenerInscripcionPorId(inscripcionId);
            
        }  catch (RuntimeException ex) {
            throw new IllegalArgumentException(ex.getMessage().toString(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage().toString(), e.getCause());
        }
    }
}