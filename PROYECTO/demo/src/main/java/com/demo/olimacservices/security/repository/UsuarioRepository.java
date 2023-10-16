package com.demo.olimacservices.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.demo.olimacservices.security.entity.Usuario;
import java.util.List;
import java.util.Optional;
 
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    
    @Query(value = "SELECT * FROM find_user_by_email(:email_to_search)", nativeQuery = true)
    Optional<Usuario> findUserByEmail(@Param("email_to_search") String email);


    @Query(value = "call find_user_by_id(user_id)", nativeQuery = true)
    public Usuario getUsuarioById(Integer id);

    @Query(value = "SELECT * FROM obtener_usuarios();", nativeQuery = true)
    public List<Usuario> getAllUsers();

    @Modifying
    @Query(value = "call delete_user_by_id(:user_id)", nativeQuery = true)
    public void deleteById(@Param("user_id") Integer id);


    @Query(value = "SELECT * FROM insertar_usuario(:nombre, :apellido, :email, :password, :estado);", nativeQuery = true)
    public Usuario insertarUsuario(
            @Param("nombre") String nombre, 
            @Param("apellido") String apellido, 
            @Param("email") String email, 
            @Param("password") String password,
            @Param("estado") String estado);
    
    @Modifying
    @Query(value = "call update_usuario(:id, :nombre, :apellido, :email, :password)", nativeQuery = true)
    public Usuario updateUsuario(
        @Param("id") Integer id,
        @Param("nombre") String nombre,
        @Param("apellido") String apellido,
        @Param("email") String email,
        @Param("password") String password
    );

}