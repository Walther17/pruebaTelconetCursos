package com.demo.olimacservices.security.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import com.demo.olimacservices.security.enums.RolNombre;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "rol")
@AllArgsConstructor
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RolNombre rolNombre;

    @CreatedDate
    @Column(name = "fe_creacion", nullable = false, updatable = false)
    private LocalDateTime feCreacion;

    @LastModifiedDate
    @Column(name = "fe_actualizacion")
    private LocalDateTime feActualizacion;

    @NotBlank(message = "El campo estado no puede estar vacío")
    @Size(max = 1, message = "El campo estado debe tener un máximo de 1 caracter")
    @Column(name = "estado")
    @Pattern(regexp = "[AI]", message = "El campo estado solo puede tener los valores: A Activo, I Inactivo")
    private String estado;

    public Rol() {
    }

    public Rol(@NotNull RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RolNombre getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }

}

