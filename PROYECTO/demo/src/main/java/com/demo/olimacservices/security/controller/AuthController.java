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
import com.demo.olimacservices.security.entity.Rol;
import com.demo.olimacservices.security.entity.Usuario;
import com.demo.olimacservices.security.jwt.JwtProvider;
import com.demo.olimacservices.security.repository.UsuarioRepository;
import com.demo.olimacservices.security.service.AuthService;
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
    RolService rolService;

    @Autowired
    AuthService authService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    JwtProvider jwtProvider;


    // @PostMapping("/nuevo")
    // public ResponseEntity<?> nuevo(@Valid @RequestBody Usuario nuevoUsuario,
    // BindingResult bindingResult) {
    // try {
    // authService.registrarUsuario(nuevoUsuario, bindingResult);
    // return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Usuario
    // guardado"));
    // } catch (IllegalArgumentException e) {
    // return ResponseEntity.badRequest().body(new Mensaje(e.getMessage()));
    // } catch (RuntimeException e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
    // Mensaje("Error al guardar el usuario"));
    // }
    // }

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody Usuario nuevoUsuario, BindingResult bindingResult) {
        try {
            Usuario usuarioCreado = authService.registrarUsuario(nuevoUsuario, bindingResult);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado); // Retorna el usuario creado
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Mensaje(e.getMessage()));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Mensaje("Error al guardar el usuario......"));
        }
    }
    

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody Usuario loginUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JwtDto jwtDto = authService.login(loginUsuario);

        return new ResponseEntity<>(jwtDto, HttpStatus.OK);
    }
    @PostMapping("/loginn")
    public ResponseEntity<JwtDto> loginn(@Valid @RequestBody Usuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getEmail(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
    
        JwtDto jwtDto = new JwtDto(jwt);
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }



    @GetMapping("/roles")
    private List<Rol> getAllRoles() {
        return rolService.getAll();
    }

    @GetMapping("/roles_")
    private List<Rol> getAllRoles_() {
        return rolService.getAllRoles();
    }

    /////////////

     @PostMapping("/nuevo_")
    public ResponseEntity<?> nuevoo(@Valid @RequestBody Usuario nuevoUsuario, BindingResult bindingResult) {
        
        Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getApellido(),
        nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()), nuevoUsuario.getEstado());
        
        Set<Rol> roles = new HashSet<>();
        Optional<Rol> rolAdmmin = rolService.getByRolNombre("ROLE_ADMIN");
        roles.add(rolAdmmin.get());
        // if (nuevoUsuario.getRoles().contains("ROLE_ADMIN")) {
        //     Optional<Rol> rolAdmminOptional = rolService.getByRolNombre("ROLE_ADMIN");
        //     if (rolAdmminOptional.isPresent()) {
        //         roles.add(rolAdmminOptional.get());
        //     } else {
        //             throw new IllegalArgumentException("El rol del usuario no está configurado");
        //     }
        // }else if (nuevoUsuario.getRoles().contains("ROLE_CREADOR")) {
        //         Optional<Rol> rolAdmminOptional = rolService.getByRolNombre("ROLE_CREADOR");
        //     if (rolAdmminOptional.isPresent()) {
        //         roles.add(rolAdmminOptional.get());
        //     } else {
        //             throw new IllegalArgumentException("El rol del usuario no está configurado");
        //     }
        // }
        //  else {
        //     Optional<Rol> rolUserOptional = rolService.getByRolNombre("ROLE_CONSUMIDOR");
        //     if (rolUserOptional.isPresent()) {
        //         roles.add(rolUserOptional.get());
        //     } else {
        //        throw new IllegalArgumentException("El rol del usuario no está configurado");
        //     }
        // }
        usuario.setRoles(roles);
        usuarioRepository.insertarUsuario(usuario.getNombre(), usuario.getApellido(),
        usuario.getEmail(), usuario.getPassword(), usuario.getEstado());
        
        return new ResponseEntity(new Mensaje("Usuario guardado"), HttpStatus.CREATED);
    }

}
