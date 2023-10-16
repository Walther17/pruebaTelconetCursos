package com.demo.olimacservices.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.demo.olimacservices.security.entity.Usuario;
import com.demo.olimacservices.security.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Optional<Usuario> getByEmail(String email) {
        return usuarioRepository.findUserByEmail(email);
    }

    public List<Usuario> getAll() {
        List<Usuario> usuario = usuarioRepository.getAllUsers();
        if (usuario != null) {
            return usuario;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay datos");
        }
    }

    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);

    }

    public Usuario updateUsuario(Usuario usuario, Integer id) {

        Optional<Usuario> existingUsuario = usuarioRepository.findById(id);

        if (existingUsuario != null && existingUsuario.get().getEstado() != null) {
            Usuario userUpdate = existingUsuario.get();

            userUpdate.setNombre(usuario.getNombre());
            userUpdate.setApellido(usuario.getApellido());
            userUpdate.setEmail(usuario.getEmail());
            userUpdate.setEstado(usuario.getEstado());
            userUpdate.setPassword(usuario.getPassword());
            userUpdate.setRoles(usuario.getRoles());

            return usuarioRepository.save(userUpdate);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No se encontró el usuario con el ID especificado: " + id);
        }

    }

    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.getUsuarioById(id);
    }

    // public Usuario insertarUsuario() {
    //     Usuario u = new Usuario();
    //      usuarioRepository.insertarUsuario(u.getNombre(), u.getApellido(), email, password, estado);
    // }

}
