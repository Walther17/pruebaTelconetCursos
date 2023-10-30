package com.demo.olimacservices.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import javax.validation.Valid;
import com.demo.olimacservices.Message.Mensaje;
import com.demo.olimacservices.security.entity.Rol;
import com.demo.olimacservices.security.service.RolService;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    RolService rolService;

    @GetMapping("all")
    public ResponseEntity<?> getAllRoles() {
        try {
            List<Rol> list = rolService.getAll();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Mensaje("No se encontraron roles activos"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create")
    public ResponseEntity<?> crear(@Valid @RequestBody Rol rol) {
        try {
            Rol rolCreado = rolService.crearRol(rol.getRolNombre(), rol.getEstado());
            return new ResponseEntity<>(rolCreado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new Mensaje("Error interno del servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> actualizarRol(@PathVariable Integer id, @RequestBody Rol rol) {
        try {
            rolService.actualizarRol(id, rol.getRolNombre(), rol.getEstado());
            return new ResponseEntity<>(rol, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> eliminarRol(@PathVariable Integer id) {
        try {
            Rol rolEliminado = rolService.eliminarRol(id);
            return new ResponseEntity<>(rolEliminado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
