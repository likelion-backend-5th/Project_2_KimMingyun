package com.example.mutsasns.dto.jwt;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username;
    private String password;
}