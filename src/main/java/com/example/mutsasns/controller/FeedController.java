package com.example.mutsasns.controller;

import com.example.mutsasns.dto.article.ArticlePageDto;
import com.example.mutsasns.dto.article.ArticleReadDto;
import com.example.mutsasns.dto.article.ArticleRequestDto;
import com.example.mutsasns.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mutsasns/feed")
public class FeedController {

    private final FeedService feedService;

    @PostMapping("/posting")
    public ResponseEntity<Map<String, String>> create(
            @ModelAttribute("articles") ArticleRequestDto dto
    ){

        feedService.createArticle(dto);

        log.info(dto.toString());
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "등록이 완료되었습니다.");

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/read")
    public Page<ArticlePageDto> readArticles(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "25") Integer limit
    ){
        return feedService.readArticlePage(page,limit);
    }

    @GetMapping("/read/{articleId}")
    public ArticleReadDto readArticles(
            @PathVariable("articleId") Long articleId
    ){
        return feedService.readArticle(articleId);
    }
}
