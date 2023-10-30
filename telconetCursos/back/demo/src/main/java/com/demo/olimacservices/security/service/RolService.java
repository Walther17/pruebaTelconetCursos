package com.demo.olimacservices.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.demo.olimacservices.security.entity.Rol;
import com.demo.olimacservices.security.repository.RolRepository;
import java.util.List;
 
@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Rol getByRolNombre(String rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }

    public List<Rol> getAll(){
        try {
            
            List<Rol> roles = rolRepository.getAllRoles();
            if (roles.isEmpty()) {
                throw new IllegalArgumentException("No se encontraron roles activos");
            }
            return roles;
        } catch (RuntimeException ex) {
             throw new IllegalArgumentException(ex.getMessage().toString(), ex.getCause());
        } catch (Exception e) {
             throw new IllegalArgumentException("Error " + e.getMessage().toString(), e.getCause());
        }
        
    }

     public Rol actualizarRol(Integer id, String nombre, String estado) {
    
        try {
             if(!rolRepository.existsById(id)){
             throw new IllegalArgumentException("El id del rol no existe");
             }
            return rolRepository.actualizarRol(id, nombre, estado);
            
        }catch (RuntimeException ex) {
             throw new IllegalArgumentException(ex.getMessage().toString(), ex.getCause());
        } catch (Exception e) {
             throw new IllegalArgumentException("Error " + e.getMessage().toString(), e.getCause());
        }
    }

    public Rol crearRol(String nombre, String estado) {
    
        try {
            if(rolRepository.existsByRolNombre(nombre)){
             throw new IllegalArgumentException("El nombre del rol ya existe.");
             }
             if (nombre.isEmpty()){
             throw new IllegalArgumentException("El nombre del rol no puede ser vacío.");
            } if (estado.isEmpty()){
             throw new IllegalArgumentException("El estado del rol no puede ser vacío.");
            }
            return rolRepository.crearRol(nombre, estado);
            
        }catch (RuntimeException ex) {
             throw new IllegalArgumentException(ex.getMessage().toString(), ex.getCause());
        } catch (Exception e) {
             throw new IllegalArgumentException("Error", e.getCause());
        }
    }

    public Rol eliminarRol(Integer rolId) {
        try {
              if(!rolRepository.existsById(rolId)){
             throw new IllegalArgumentException("El id del rol no existe");
             }
            return rolRepository.eliminarRol(rolId);
        } catch (RuntimeException ex) {
             throw new IllegalArgumentException(ex.getMessage().toString(), ex.getCause());
        } catch (Exception e) {
             throw new IllegalArgumentException("Error " + e.getMessage().toString(), e.getCause());
        }
    }
}

