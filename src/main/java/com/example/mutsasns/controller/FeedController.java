package com.example.mutsasns.controller;

import com.example.mutsasns.service.FeedService;
import com.example.mutsasns.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mutsasns/Feed")
public class FeedController {

    private final FeedService feedService;
}
