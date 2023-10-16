package com.demo.olimacservices.security.entity;

import javax.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "usuario_roles")
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    // public UsuarioRol(Usuario usuario, Rol rol) {
    //     this.usuario = usuario;
    //     this.rol = rol;
    // }
 
}
