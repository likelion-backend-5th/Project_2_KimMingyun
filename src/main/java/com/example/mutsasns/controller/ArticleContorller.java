package com.example.mutsasns.controller;

import com.example.mutsasns.dto.article.ArticlePageDto;
import com.example.mutsasns.dto.article.ArticleReadDto;
import com.example.mutsasns.dto.article.ArticleRequestDto;
import com.example.mutsasns.dto.article.ArticleUpdateDto;
import com.example.mutsasns.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mutsasns/feed")
public class ArticleContorller {

    private final ArticleService articleService;

    @PostMapping("/posting")
    public ResponseEntity<Map<String, String>> create(
            @ModelAttribute("articles") ArticleRequestDto dto
    ){

        articleService.createArticle(dto);

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
        return articleService.readArticlePage(page,limit);
    }

    @GetMapping("/read/{articleId}")
    public ArticleReadDto readArticles(
            @PathVariable("articleId") Long articleId
    ){
        return articleService.readArticle(articleId);
    }

    @PutMapping("/articles/{articleId}")
    public ResponseEntity<Map<String, String>> updateArticle(
            @ModelAttribute("articles") ArticleUpdateDto dto,
            @PathVariable("articleId") Long articleId
    ) {
        articleService.updateArticle(dto, articleId);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "피드를 수정했습니다.");

        return ResponseEntity.ok(responseBody);
    }

    // 2-5-1 이미지 삭제 or title/content 수정 or // 이미지 추가는 postmapping 으로 이미 구현해둠
    @PutMapping("/articles/{articleId}")
    public ResponseEntity<Map<String, String>> updateArticle(
            @PathVariable("articleId")Long articleId,
            @RequestBody ArticleUpdateDto dto)

    {
        articleService.updateArticle(dto, articleId);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "피드를 수정했습니다.");
        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("/{articleId}/images/{imageId}")
    public ResponseEntity<Map<String, String>> deleteImage(
            @PathVariable("articleId") Long articleId,
            @PathVariable("imageId") Long imageId)
    {
        articleService.deleteImage(articleId, imageId);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "피드에 이미지가 삭제 되었습니다.");
        return ResponseEntity.ok(responseBody);
    }



    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity<Map<String, String>> deleteArticle(
            @PathVariable("articleId") Long articleId
    ) {
        if (articleService.deleteArticle(articleId)) {

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "피드를 삭제했습니다.");

            return ResponseEntity.ok(responseBody);
        }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
