package com.demo.olimacservices.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.olimacservices.security.entity.Rol;
import com.demo.olimacservices.security.entity.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {


    Optional<Usuario> findByEmail(String email);

    /////////////////////////////////////////////////////
    
    boolean existsByEmail(String email);
    boolean existsById(Integer id);

    @Query(value = "SELECT * FROM insertar_usuario(:nombre, :apellido, :email, :password, :estado);", nativeQuery = true)
    public Usuario insertarUsuario(
        @Param("nombre") String nombre, 
        @Param("apellido") String apellido, 
        @Param("email") String email, 
        @Param("password") String password,
            @Param("estado") String estado);
    
    @Query(value = "SELECT * FROM obtener_usuario_por_id(:p_usuario_id)", nativeQuery = true)
    public Usuario obtenerUsuarioPorId(@Param("p_usuario_id") Integer usuarioId);

    @Query(value = "SELECT * FROM obtener_todos_los_usuarios()", nativeQuery = true)
    public List<Usuario> getAllUsuarios();

    @Transactional
    @Query(value = "SELECT * FROM actualizar_usuario(:p_usuario_id, :p_nombre, :p_apellido, :p_email, :p_password, :p_estado)", nativeQuery = true)
    public Usuario actualizarUsuario(
            @Param("p_usuario_id") Integer id,
            @Param("p_nombre") String nombre,
            @Param("p_apellido") String apellido,
            @Param("p_email") String email,
            @Param("p_password") String password,
            @Param("p_estado") String estado
            );

//  @Modifying
    @Transactional
    @Query(value = "SELECT * FROM eliminar_usuario(:p_usuario_id)", nativeQuery = true)
    public Usuario eliminarUsuario(@Param("p_usuario_id") Integer usuarioId);
}