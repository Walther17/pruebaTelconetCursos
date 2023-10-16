package com.demo.olimacservices.security.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @NotNull(message = "El campo nombre no puede ser nulo")
    @NotBlank(message = "El campo nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "El campo apellido no puede ser nulo")
    @NotBlank(message = "El campo apellido no puede estar vacío")
    @Size(max = 255, message = "El campo apellido debe tener un máximo de 255 caracteres")
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @NotNull(message = "El campo email no puede ser nulo")
    @Email(message = "El correo no es válido.")
    @Size(min = 5, max = 255, message = "El correo debe ser mayor a 5 y menor a 255 caracteres.")
    @NotEmpty(message = "El correo es obligatorio.")
    @Column(name = "email", unique = true)
    private String email;

    @NotNull(message = "El campo password no puede ser nulo")
    @NotBlank(message = "El campo password no puede estar vacío")
    @Size(max = 255, message = "El campo password debe tener un máximo de 255 caracteres")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "El campo estado no puede estar vacío")
    @Size(max = 1, message = "El campo estado debe tener un máximo de 1 caracter")
    @Column(name = "estado")
    @Pattern(regexp = "[AI]", message = "El campo estado solo puede tener los valores: A Activo, I Inactivo")
    private String estado;

    @CreatedDate
    @Column(name = "fe_creacion", nullable = false, updatable = false)
    private LocalDateTime feCreacion;

    @LastModifiedDate
    @Column(name = "fe_actualizacion")
    private LocalDateTime feActualizacion;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"), 
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "creador", cascade = CascadeType.ALL)
    private Set<Curso> cursosCreados = new HashSet<>();

    @OneToMany(mappedBy = "consumidor", cascade = CascadeType.ALL)
    private Set<InscripcionCurso> cursosInscritos = new HashSet<>();

    public Usuario(@NotNull String nombre, @NotNull String apellido,
            @NotNull String email, @NotNull String password, String estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.estado = estado;
    }

}
