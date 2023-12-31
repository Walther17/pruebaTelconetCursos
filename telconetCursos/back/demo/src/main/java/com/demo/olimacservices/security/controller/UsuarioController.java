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
import org.springframework.web.bind.annotation.DeleteMapping;
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
 

   
  // @PreAuthorize("hasRole('ADMIN')")
   

  // @PreAuthorize("hasRole('ADMIN')")
   

  ////////////////////////////////
  @GetMapping("/{usuarioId}")
  public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Integer usuarioId) {
    try {
      Usuario usuario = usuarioService.obtenerUsuarioPorId(usuarioId);
      return new ResponseEntity<>(usuario, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(new Mensaje("El usuario que buscas no existe"), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("todos")
  public ResponseEntity<?> obtenerTodosLosUsuarios() {
    try {
      List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
      return new ResponseEntity<>(usuarios, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(new Mensaje("No se encontraron usuarios activos"), HttpStatus.NOT_FOUND);
    }catch (Exception e) {
      return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("create")
  public ResponseEntity<?> insertarUsuario(@Valid @RequestBody Usuario usuario) {
    try {
      Usuario newUsuario = usuarioService.crearUsuarioConRol(usuario); // Pasa el objeto Usuario completo
      return new ResponseEntity<>(newUsuario, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>(new Mensaje("Error al crear el usuario"), HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("eliminar/{usuarioId}")
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

  @PutMapping("actualizar/{id}")
  public ResponseEntity<?> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
    try {
      usuarioService.actualizarUsuario(id, usuario.getNombre(), usuario.getApellido(), usuario.getEmail(),
           usuario.getPassword(), usuario.getEstado());
      return new ResponseEntity<>(usuario, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

}
