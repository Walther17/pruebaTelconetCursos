package com.demo.olimacservices.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.olimacservices.security.entity.Usuario;
import com.demo.olimacservices.security.entity.UsuarioPrincipal;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // UsuarioService se encarga de convertir la clase usuario en la clase principal, dicha clase es la que utiliza SSC para mostrar la info según los roles
    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getByEmail(username).get();
        return UsuarioPrincipal.build(usuario);
    }
}