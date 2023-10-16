package com.demo.olimacservices.security.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import com.demo.olimacservices.security.dto.JwtDto;
import com.demo.olimacservices.security.entity.Rol;
import com.demo.olimacservices.security.entity.Usuario;
import com.demo.olimacservices.security.jwt.JwtProvider;
import com.demo.olimacservices.security.repository.UsuarioRepository;

@Service
@Transactional
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    // public Usuario registrarUsuario(Usuario nuevoUsuario, BindingResult
    // bindingResult) {

    // Optional <Usuario> existingUsuario =
    // usuarioRepository.findUserByEmail(nuevoUsuario.getEmail());
    // // if (bindingResult.hasErrors()) {
    // // throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    // // }
    // if (nuevoUsuario.getEmail() == null){
    // throw new IllegalArgumentException("Ingrese un email");
    // }

    // //else if (existingUsuario.isPresent()) {
    // // throw new IllegalArgumentException("Ese email ya existe");
    // // }
    // else if (nuevoUsuario.getPassword() == null){
    // throw new IllegalArgumentException("El campo password no puede ser vacío");
    // } else {

    // Usuario usuario = new Usuario(nuevoUsuario.getNombre(),
    // nuevoUsuario.getApellido(),
    // nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()),
    // nuevoUsuario.getEstado());

    // try {
    // Set<UsuarioRol> usuarioRoles = new HashSet<>();

    // if (nuevoUsuario.getUsuarioRoles().contains("ROLE_ADMIN")) {
    // Optional<Rol> rolAdminOptional =
    // rolService.getByRolNombre(RolNombre.ROLE_ADMIN);
    // if (rolAdminOptional.isPresent()) {
    // usuarioRoles.add(new UsuarioRol(usuario, rolAdminOptional.get()));
    // } else {
    // throw new IllegalArgumentException("El administrador no está configurado");
    // }
    // } else if (nuevoUsuario.getUsuarioRoles().contains("ROLE_CONSUMIDOR")) {
    // Optional<Rol> rolUserOptional =
    // rolService.getByRolNombre("ROLE_ADMIN");
    // if (rolUserOptional.isPresent()) {
    // usuarioRoles.add(new UsuarioRol(usuario, rolUserOptional.get()));
    // } else {
    // throw new IllegalArgumentException("El rol del usuario no está configurado");
    // }
    // } else if (nuevoUsuario.getUsuarioRoles().contains("ROLE_CREADOR")) {
    // Optional<Rol> rolUserOptional =
    // rolService.getByRolNombre(RolNombre.ROLE_CREADOR);
    // if (rolUserOptional.isPresent()) {
    // usuarioRoles.add(new UsuarioRol(usuario, rolUserOptional.get()));
    // } else {
    // throw new IllegalArgumentException("El rol del usuario no está configurado");
    // }
    // }

    // usuario.setUsuarioRoles(usuarioRoles);

    // System.out.println(usuarioRoles);

    // System.out.println(usuario.getUsuarioRoles() + " appellido " +
    // usuario.getApellido()+ usuario.getEmail()+
    // usuario.getPassword()+usuario.getEstado());

    // usuarioRepository.insertarUsuario(usuario.getNombre(), usuario.getApellido(),
    // usuario.getEmail(), usuario.getPassword(), usuario.getEstado());

    // } catch (Exception e) {
    // throw new RuntimeException("Error al guardar el usuario xd");
    // }
    // return nuevoUsuario;

    // }

    // }

    public Usuario registrarUsuario(Usuario nuevoUsuario, BindingResult bindingResult) {

        if (nuevoUsuario.getEmail() == null) {
            throw new IllegalArgumentException("Ingrese un email");
        } else if (nuevoUsuario.getPassword() == null) {
            throw new IllegalArgumentException("El campo password no puede ser vacío");
        }

        Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getApellido(),
                nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()), nuevoUsuario.getEstado());

        try {
            Set<Rol> roles = new HashSet<>();

        
                // Optional<Rol> rolAdminOptional = rolService.getByRolNombre("ROLE_ADMIN");
                // if (rolAdminOptional.isPresent()) {
                //     roles.add(rolAdminOptional.get());
                // } else {
                //     throw new IllegalArgumentException("El administrador no está configurado");
                // }
                //  Optional<Rol> rolConsumidorOptional = rolService.getByRolNombre("ROLE_CONSUMIDOR");
                // if (rolConsumidorOptional.isPresent()) {
                //     roles.add(rolConsumidorOptional.get());

                // } else {
                //     throw new IllegalArgumentException("El rol del usuario no está configurado");
                // }
                //  Optional<Rol> rolCreadorOptional = rolService.getByRolNombre("ROLE_CREADOR");
                // if (rolCreadorOptional.isPresent()) {
                //     roles.add(rolCreadorOptional.get());

                // } else {
                //     throw new IllegalArgumentException("El rol del usuario no está configurado");
                // }
        
           if (nuevoUsuario.getRoles().contains("ROLE_ADMIN")) {
            Optional<Rol> rolAdmminOptional = rolService.getByRolNombre("ROLE_ADMIN");
            if (rolAdmminOptional.isPresent()) {
                roles.add(rolAdmminOptional.get());
            } else {
                    throw new IllegalArgumentException("El rol del usuario no está configurado");
            }
        }else if (nuevoUsuario.getRoles().contains("ROLE_CREADOR")) {
                Optional<Rol> rolAdmminOptional = rolService.getByRolNombre("ROLE_CREADOR");
            if (rolAdmminOptional.isPresent()) {
                roles.add(rolAdmminOptional.get());
            } else {
                    throw new IllegalArgumentException("El rol del usuario no está configurado");
            }
        }
         else {
            Optional<Rol> rolUserOptional = rolService.getByRolNombre("ROLE_CONSUMIDOR");
            if (rolUserOptional.isPresent()) {
                roles.add(rolUserOptional.get());
            } else {
               throw new IllegalArgumentException("El rol del usuario no está configurado");
            }
        }

            usuario.setRoles(roles);

            usuarioRepository.insertarUsuario(usuario.getNombre(), usuario.getApellido(),
                    usuario.getEmail(), usuario.getPassword(), usuario.getEstado());

                    // usuarioRepository.save(usuario);
            System.out.println(roles);
         } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al insertar el usuario en la base de datos", e);
        }
        return usuario;
       
    }
    

    /*
     * public JwtDto login(NuevoUsuario loginUsuario, BindingResult bindingResult) {
     * if (bindingResult.hasErrors()) {
     * throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
     * }
     * Authentication authentication = authenticationManager.authenticate(
     * new UsernamePasswordAuthenticationToken(loginUsuario.getEmail(),
     * loginUsuario.getPassword()));
     * SecurityContextHolder.getContext().setAuthentication(authentication);
     * String jwt = jwtProvider.generateToken(authentication);
     * 
     * JwtDto jwtDto = new JwtDto(jwt);
     * return jwtDto;
     * }
     */
    // public String login(String email, String password) {
    // Authentication authentication = authenticationManager.authenticate(
    // new UsernamePasswordAuthenticationToken(email, password));

    // if (authentication.isAuthenticated()) {
    // SecurityContextHolder.getContext().setAuthentication(authentication);
    // return jwtProvider.generateToken(authentication);
    // } else {
    // return null;
    // }
    // }

    // public Usuario registrarUsuario(Usuario nuevoUsuario, BindingResult
    // bindingResult) {
    // // Validar los datos del nuevo usuario si es necesario
    // if (bindingResult.hasErrors()) {
    // throw new IllegalArgumentException("Los datos del usuario son inválidos");
    // }

    // // Codificar la contraseña antes de guardarla
    // String encodedPassword = passwordEncoder.encode(nuevoUsuario.getPassword());

    // // Crear un nuevo usuario con los datos proporcionados en el JSON
    // Usuario usuario = new Usuario(
    // nuevoUsuario.getNombre(),
    // nuevoUsuario.getApellido(),
    // nuevoUsuario.getEmail(),
    // encodedPassword,
    // nuevoUsuario.getEstado()
    // );

    // try {
    // // Crear una colección para los roles del usuario
    // Set<UsuarioRol> usuarioRoles = new HashSet<>();

    // // Obtener los roles deseados (puedes modificar esto según los roles
    // proporcionados en el JSON)
    // Optional<Rol> rolAdminOptional = rolService.getByRolNombre("ROLE_ADMIN");
    // Optional<Rol> rolConsumidorOptional =
    // rolService.getByRolNombre("ROLE_CONSUMIDOR");
    // Optional<Rol> rolCreadorOptional = rolService.getByRolNombre("ROLE_CREADOR");

    // // Verificar y agregar ROLE_ADMIN si existe
    // if (rolAdminOptional.isPresent()) {
    // usuarioRoles.add(new UsuarioRol(usuario, rolAdminOptional.get()));
    // } else {
    // throw new IllegalArgumentException("El rol del administrador no está
    // configurado");
    // }

    // // Verificar y agregar ROLE_CONSUMIDOR si existe
    // if (rolConsumidorOptional.isPresent()) {
    // usuarioRoles.add(new UsuarioRol(usuario, rolConsumidorOptional.get()));
    // } else {
    // throw new IllegalArgumentException("El rol del consumidor no está
    // configurado");
    // }

    // // Verificar y agregar ROLE_CREADOR si existe
    // if (rolCreadorOptional.isPresent()) {
    // usuarioRoles.add(new UsuarioRol(usuario, rolCreadorOptional.get()));
    // } else {
    // throw new IllegalArgumentException("El rol del creador no está configurado");
    // }

    // // Asignar los roles al usuario
    // usuario.setUsuarioRoles(usuarioRoles);

    // // Guardar el usuario en la base de datos
    // usuarioRepository.insertarUsuario(usuario.getNombre(), usuario.getApellido(),
    // usuario.getEmail(), usuario.getPassword(), usuario.getEstado());
    // // Imprimir información para depuración
    // System.out.println("Roles del usuario:");
    // for (UsuarioRol usuarioRol : usuarioRoles) {
    // System.out.println("Usuario: " + usuarioRol.getUsuario().getNombre() + ",
    // Rol: " + usuarioRol.getRol().getRolNombre());
    // }

    // return usuario;
    // } catch (RuntimeException e) {
    // e.printStackTrace();
    // throw new RuntimeException("Error al insertar el usuario en la base de
    // datos", e);
    // }
    // }

    public JwtDto login(Usuario loginUsuario) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUsuario.getEmail(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);

        return new JwtDto(jwt);
    }
}
