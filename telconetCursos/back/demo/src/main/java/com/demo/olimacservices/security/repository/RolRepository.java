package com.demo.olimacservices.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.olimacservices.security.entity.Rol;
import com.demo.olimacservices.security.enums.RolNombre;


@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);

    @Query(value = "SELECT * FROM obtener_roles();", nativeQuery = true)
    List<Rol> getAllRoles();
}
