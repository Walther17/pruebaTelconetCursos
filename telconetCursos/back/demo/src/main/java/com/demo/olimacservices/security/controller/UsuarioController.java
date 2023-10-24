package com.demo.olimacservices.security.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.olimacservices.Message.Mensaje;
import com.demo.olimacservices.security.entity.Usuario;
import com.demo.olimacservices.security.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

  @Autowired
  UsuarioService usuarioService;

   @Autowired
    PasswordEncoder passwordEncoder;

  @GetMapping("/listar-usuarios")
  public ResponseEntity<List<Usuario>> list() {
    return ResponseEntity.ok(usuarioService.getAll());
  }

  @GetMapping("/detail/{id}")
  public ResponseEntity<Usuario> getUsuariobyId(@PathVariable Integer id) {
    Usuario usuario = usuarioService.getUsuarioById(id);
    return ResponseEntity.ok(usuario);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/delete/{id}")
  public ResponseEntity<?> setEstadoNull(@Valid @PathVariable Integer id) {
    usuarioService.setEstadoNull(id);
    Map<String, Object> response = new HashMap<>(); // (Map) para construye el objeto JSON de respuesta.

    response.put("success", true);
    response.put("message", "Estado actualizado a null para el usuario con ID: " + id);

    return ResponseEntity.ok(response); // devuelve una respuesta exitosa con el objeto JSON construido.
  }

  // @PreAuthorize("hasRole('ADMIN')")
  // @PutMapping("update/{id}")
  // public ResponseEntity<Usuario> updateUsuario(@Valid @RequestBody Usuario usuario, @PathVariable Integer id) {
  //   return ResponseEntity.ok(usuarioService.updateUsuario(usuario, id));
  // }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("create")
  public ResponseEntity<Usuario> saveUsuario(@Valid @RequestBody Usuario usuario) {
    return ResponseEntity.ok(usuarioService.save2(usuario));
  }

  ////////////////////////////////
  @GetMapping("/{usuarioId}")
  public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Integer usuarioId) {
    try {
      Usuario usuario = usuarioService.obtenerUsuarioPorId(usuarioId);
      return new ResponseEntity<>(usuario, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(new Mensaje("El usuario que buscas no existe"), HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/todos")
    public ResponseEntity<?> obtenerTodosLosUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Mensaje("No se encontraron usuarios activos"), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update/{usuarioId}")
    public ResponseEntity<?> actualizarUsuario(
        @PathVariable Integer usuarioId, @RequestBody Usuario usuario
    ) {
      try {
             usuarioService.actualizarUsuario(usuarioId, usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), passwordEncoder.encode(usuario.getPassword()), usuario.getEstado());
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    
    @PutMapping("/eliminar/{usuarioId}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer usuarioId) {
        try {
            usuarioService.eliminarUsuario(usuarioId);
            return new ResponseEntity<>(new Mensaje("Usuario eliminado exitosamente"), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new Mensaje("Error interno del servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
