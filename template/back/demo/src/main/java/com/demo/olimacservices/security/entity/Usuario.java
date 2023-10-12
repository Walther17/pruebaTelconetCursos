package com.demo.olimacservices.security.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message="El campo nombre no puede ser nulo")
    @NotBlank(message = "El campo nombre no puede estar vacio")
    private String nombre;

    @NotNull(message="El campo apellido no puede ser nulo")
    @NotBlank(message = "El campo apellido no puede estar vacio")
    @Size(max = 255, message = "El campo apellido debe tener un máximo de 255 caracteres")
    @Column(name = "apellido", nullable = false)
    private String apellido;
    
    @NotNull(message="El campo nombre de usuario no puede ser nulo")
    @Column(unique = true, name = "nombre_usuario")
    private String nombreUsuario;
    
    @NotNull
    @Email(message = "El correo no es válido.")
	@Size(min = 5, max = 255, message = "El correo debe ser mayor a 5 y menor a 255 caracteres.")
	@NotEmpty(message = "El correo es obligatorio.")
    @Column(name = "email")
    private String email;

    @NotNull(message="El campo password no puede ser nulo")
    @NotBlank(message = "El campo password no puede estar vacio")
    @Size(max = 255, message = "El campo password debe tener un máximo de 255 caracteres")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "El campo estado no puede estar vacio")
    @Size(max = 1, message = "El campo estado debe tener un máximo de 1 caracteres")
    @Column(name = "estado")
    @Pattern(regexp = "[AI]", message = "El campo estado solo puede tener los valores: A Activo, I Inactivo")
    private String estado;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    public Usuario(@NotNull String nombre, @NotNull String apellido, @NotNull String nombreUsuario, @NotNull String email, @NotNull String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
    } 
}
