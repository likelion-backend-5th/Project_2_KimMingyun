package com.example.mutsasns.controller;

import com.example.mutsasns.dto.user.UserInfoDto;
import com.example.mutsasns.service.UserInfoService;
import com.example.mutsasns.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mutsasns/userInfo")
public class UserInfoController {

    private final UserInfoService userInfoService;
    @GetMapping("/{userId}/read")
    public UserInfoDto readUser(
            @PathVariable("userId") Long userId)
    {
        return userInfoService.readUser(userId);

    }

}
