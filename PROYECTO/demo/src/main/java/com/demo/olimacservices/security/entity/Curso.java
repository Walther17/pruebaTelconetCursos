package com.demo.olimacservices.security.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "El campo nombre del curso no puede estar vacío")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "creador_id")
    private Usuario creador;

    @NotBlank(message = "El campo estado no puede estar vacío")
    @Size(max = 1, message = "El campo estado debe tener un máximo de 1 caracter")
    @Column(name = "estado")
    @Pattern(regexp = "[AIC]", message = "El campo estado solo puede tener los valores: A Activo, I Inactivo")
    private String estado;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private Set<InscripcionCurso> inscripciones = new HashSet<>();
}
