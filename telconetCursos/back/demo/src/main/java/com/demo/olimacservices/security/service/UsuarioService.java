package com.demo.olimacservices.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.demo.olimacservices.security.entity.Rol;
import com.demo.olimacservices.security.entity.Usuario;
import com.demo.olimacservices.security.repository.RolRepository;
import com.demo.olimacservices.security.repository.UsuarioRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.management.RuntimeErrorException;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RolRepository rolRepository;


    public Optional<Usuario> getByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
 
    /////////////////////////////////
    public Usuario obtenerUsuarioPorId(int usuarioId) {
        Usuario usuario = usuarioRepository.obtenerUsuarioPorId(usuarioId);
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario con el id: " + usuarioId + " no existe");
        }
        return usuario;
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        try {
            
            List<Usuario> usuarios = usuarioRepository.getAllUsuarios();
            if (usuarios.isEmpty()) {
                throw new IllegalArgumentException("No se encontraron usuarios activos");
            }
            return usuarios;
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error " + e.getMessage(), e.getCause());
        }
    }

    public Usuario actualizarUsuario(Integer id, String nombre, String apellido, String email, String password, String estado) {
        try {
            if (!usuarioRepository.existsById(id)) {
                throw new IllegalArgumentException("El id no existe.");
            }
              // password = passwordEncoder.encode(password);
    
            return usuarioRepository.actualizarUsuario(id, nombre, apellido, email, password, estado);
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error " + e.getMessage(), e.getCause());
        }
    }
    

    public Usuario eliminarUsuario(Integer usuarioId) {
        try {
            if(!usuarioRepository.existsById(usuarioId)){
                throw new IllegalArgumentException("El id no existe existe.");
            }
            return usuarioRepository.eliminarUsuario(usuarioId);
        }catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error " + e.getMessage(), e.getCause());
        }
    }

    public Usuario crearUsuario(Usuario nuevoUsuario) {
        try {
            if (usuarioRepository.existsByEmail(nuevoUsuario.getEmail())) {
                throw new IllegalArgumentException("El email que ingresaste ya existe.");
            }
            if (nuevoUsuario.getNombre().isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede ser vacío.");
            }
            if (nuevoUsuario.getApellido().isEmpty()) {
                throw new IllegalArgumentException("El apellido no puede ser vacío.");
            }
            if (nuevoUsuario.getPassword().isEmpty()) {
                throw new IllegalArgumentException("La contraseña no puede ser vacía.");
            }
            if (nuevoUsuario.getEstado().isEmpty()) {
                throw new IllegalArgumentException("El estado no puede ser vacío.");
            }
    
            Usuario usuario = new Usuario(
                nuevoUsuario.getNombre(),
                nuevoUsuario.getApellido(),
                nuevoUsuario.getEmail(),
                nuevoUsuario.getEstado(),
              passwordEncoder.encode(nuevoUsuario.getPassword())
              //  nuevoUsuario.getPassword()
            );
    
            Set<Rol> roles = new HashSet<>();
    
            for (Rol rol : nuevoUsuario.getRoles()) {
                String rolNombre = rol.getRolNombre(); // Obtén el nombre del rol
                Rol rolEnBaseDeDatos = rolRepository.findByRolNombre(rolNombre);
                if (rolEnBaseDeDatos != null) {
                    roles.add(rolEnBaseDeDatos);
                } else {
                    throw new IllegalArgumentException("El rol '" + rolNombre + "' no existe.");
                }
            }
    
            usuario.setRoles(roles);
            System.out.println(roles);
    
            return usuarioRepository.insertarUsuario(
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getEstado()
            );
        } catch (RuntimeErrorException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error " + e.getMessage(), e.getCause());
        }
    }
    

}

