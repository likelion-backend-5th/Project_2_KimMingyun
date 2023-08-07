package com.example.mutsasns.controller;

import com.example.mutsasns.dto.article.ArticleRequestDto;
import com.example.mutsasns.dto.comment.CommentRequestDto;
import com.example.mutsasns.service.ArticleService;
import com.example.mutsasns.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mutsasns/feed")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;
    @PostMapping("/posting/{articleId}/comment")
    public ResponseEntity<Map<String, String>> create(
            @RequestBody CommentRequestDto dto,
            @PathVariable("articleId") Long articleId
    ){

        commentLikeService.createComment(articleId, dto);

        log.info(dto.toString());
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "등록이 완료되었습니다.");

        return ResponseEntity.ok(responseBody);
    }
    @PutMapping("/article/{articleId}/comment/{commentId}")
    public ResponseEntity<Map<String, String>> updateItem(
            @RequestBody CommentRequestDto dto,
            @PathVariable("articleId") Long articleId,
            @PathVariable("commentId") Long commentId
    ) {
        commentLikeService.updateComment(dto, articleId, commentId);

        log.info(dto.toString());
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "댓글이 수정되었습니다.");

        return ResponseEntity.ok(responseBody);
    }
    @DeleteMapping("/article/{articleId}/comment/{commentId}")
    public ResponseEntity<Map<String, String>> deleteArticle(
            @PathVariable("articleId") Long articleId,
            @PathVariable("commentId") Long commentId
    ) {
        if (commentLikeService.deleteComment(articleId, commentId)) {

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "댓글을 삭제했습니다.");

            return ResponseEntity.ok(responseBody);
        }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
