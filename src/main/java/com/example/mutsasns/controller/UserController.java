package com.example.mutsasns.controller;

import com.example.mutsasns.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mutsasns/user")
public class UserController {

    private final UserService userService;

    @PutMapping(value = "/profileimage",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Map<String, String>> userProfile(
            @RequestParam("image") MultipartFile Image,
            Authentication authentication
    ){
        userService.updateProfile(Image, authentication);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "사진이 업로드 되었습니다.");

        return ResponseEntity.ok(responseBody);

    }
}