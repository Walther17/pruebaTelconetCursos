package com.demo.olimacservices.security.dto;

import lombok.Data;

// JwtDto se encarga de devolver el token cuando se haces el login
@Data
public class JwtDto { // DTO Data Transfer Object
    
    private String token;
   
    public  JwtDto (){}

    public JwtDto(String token) {
        this.token = token;
    }

}
