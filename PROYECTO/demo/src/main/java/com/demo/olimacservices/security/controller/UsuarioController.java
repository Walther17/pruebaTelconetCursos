package com.demo.olimacservices.security.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.olimacservices.security.entity.Usuario;
import com.demo.olimacservices.security.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

  @Autowired
  UsuarioService usuarioService;

  @GetMapping("/listar-usuarios")
  public ResponseEntity<List<Usuario>> list() {
    return ResponseEntity.ok(usuarioService.getAll());
  }

  @GetMapping("/detail/{id}")
  public ResponseEntity<Usuario> getUsuariobyId(@PathVariable Integer id) {
    Usuario usuario = usuarioService.getUsuarioById(id);
    return ResponseEntity.ok(usuario);
  }

  // @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/delete/{id}")
  public ResponseEntity<?> deleteUsuario(@Valid @PathVariable Integer id) {
    usuarioService.deleteById(id);
    Map<String, Object> response = new HashMap<>(); // (Map) para construye el objeto JSON de respuesta.

    response.put("success", true);
    response.put("message", "Estado actualizado a null para el usuario con ID: " + id);

    return ResponseEntity.ok(response); // devuelve una respuesta exitosa con el objeto JSON construido.
  }

  // @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/update/{id}")
  public ResponseEntity<Usuario> updateUsuario(@Valid @RequestBody Usuario usuario, @PathVariable Integer id) {
    return ResponseEntity.ok(usuarioService.updateUsuario(usuario, id));
  }

  // @PreAuthorize("hasRole('ADMIN')")
  // @PostMapping("/create")
  // public ResponseEntity<?> nuevoUsuario(@RequestBody NuevoUsuario nuevoUsuario) {
  //       try {
  //           Usuario usuario = usuarioService.insertarUsuario(
  //               nuevoUsuario.getNombre(),
  //               nuevoUsuario.getApellido(),
  //               nuevoUsuario.getEmail(),
  //               nuevoUsuario.getPassword(),
  //               nuevoUsuario.getEstado()
  //           );
  //           return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
  //       } catch (Exception e) {
  //           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el usuario");
  //       }
  //   }

}
