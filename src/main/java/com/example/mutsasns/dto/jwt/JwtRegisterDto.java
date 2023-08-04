package com.example.mutsasns.dto.jwt;

import lombok.Data;

@Data
public class JwtRegisterDto {

    // 필수 정보
    private String username;

    private String password;

    private String passwordCheck;


    private String email; // 이메일
    private String phone; // 전화번호
}