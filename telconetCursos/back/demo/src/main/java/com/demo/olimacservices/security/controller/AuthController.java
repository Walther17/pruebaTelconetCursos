package com.demo.olimacservices.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.demo.olimacservices.Message.Mensaje;
import com.demo.olimacservices.security.dto.JwtDto;
import com.demo.olimacservices.security.dto.LoginUsuario;
import com.demo.olimacservices.security.entity.Rol;
import com.demo.olimacservices.security.entity.Usuario;
import com.demo.olimacservices.security.enums.RolNombre;
import com.demo.olimacservices.security.jwt.JwtProvider;
import com.demo.olimacservices.security.repository.UsuarioRepository;
import com.demo.olimacservices.security.service.RolService;
import com.demo.olimacservices.security.service.UsuarioService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;


    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody Usuario nuevoUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Mensaje("Campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        }

        // if (usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())) {
        //     return new ResponseEntity<>(new Mensaje("Ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        // }

        if (usuarioService.existsByEmail(nuevoUsuario.getEmail())) {
            return new ResponseEntity<>(new Mensaje("Ese email ya existe"), HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getApellido(),
                 nuevoUsuario.getEmail(), nuevoUsuario.getEstado(),
                passwordEncoder.encode(nuevoUsuario.getPassword()));

        Set<Rol> roles = new HashSet<>();
        if (nuevoUsuario.getRoles().contains("ROLE_ADMIN")) {
            Optional<Rol> rolAdminOptional = rolService.getByRolNombre(RolNombre.ROLE_ADMIN);
            if (rolAdminOptional.isPresent()) {
                roles.add(rolAdminOptional.get());
            } else {
                return new ResponseEntity<>(new Mensaje("El rol de administrador no está configurado"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else if (nuevoUsuario.getRoles().contains("ROLE_CONSUMIDOR")){
            Optional<Rol> rolUserOptional = rolService.getByRolNombre(RolNombre.ROLE_CONSUMIDOR);
            if (rolUserOptional.isPresent()) {
                roles.add(rolUserOptional.get());
            } else {
                return new ResponseEntity<>(new Mensaje("El rol de usuario no está configurado"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
             Optional<Rol> rolUserOptional = rolService.getByRolNombre(RolNombre.ROLE_CREADOR);
            if (rolUserOptional.isPresent()) {
                roles.add(rolUserOptional.get());
            } else {
                return new ResponseEntity<>(new Mensaje("El rol de usuario no está configurado"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        usuario.setRoles(roles);
          usuarioService.save(usuario);
        // usuarioRepository.insertarUsuario(usuario.getNombre(), usuario.getApellido(),
        // usuario.getEmail(), usuario.getPassword(), usuario.getEstado());
        return new ResponseEntity<>( usuario, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<JwtDto>(HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUsuario.getEmail(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);

        JwtDto jwtDto = new JwtDto(jwt);
        return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);
    }

    @GetMapping("/usuarios")
    private List<Usuario> getAllUsers() {
        return usuarioService.getAll();
    }

    @GetMapping("/roles")
    private List<Rol> getAllRoles() {
        return rolService.getAll();
    }
}
