package com.example.mutsasns.controller;

import com.example.mutsasns.dto.user.UserInfoDto;
import com.example.mutsasns.service.UserInfoService;
import com.example.mutsasns.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

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

    @PutMapping("/{userId}/follow")
    public ResponseEntity<Map<String, String>> followUser(
            @PathVariable("userId") Long userId)
    {
        if (userInfoService.follows(userId)) {

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "팔로우를 했습니다.");

            return ResponseEntity.ok(responseBody);
        }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }

//    @PutMapping("/{userId}/unFollow")
//    public ResponseEntity<Map<String, String>> unFollowUser(
//            @PathVariable("userId") Long userId)
//    {
//        if (userInfoService.unFollows(userId)) {
//
//            Map<String, String> responseBody = new HashMap<>();
//            responseBody.put("message", "언팔로우를 했습니다.");
//
//            return ResponseEntity.ok(responseBody);
//        }
//        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//
//    }


}
