package com.demo.olimacservices.security.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginUsuario {

    @NotBlank
    private String email;
    
    @NotBlank
    private String password;

    
}