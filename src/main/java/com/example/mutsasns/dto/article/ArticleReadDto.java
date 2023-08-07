package com.example.mutsasns.dto.article;

import com.example.mutsasns.dto.comment.CommentDto;
import com.example.mutsasns.entity.ArticleEntity;
import com.example.mutsasns.entity.ArticleImagesEntity;
import com.example.mutsasns.entity.CommentEntity;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleReadDto {

    private String title;
    private String content;
    private List<String> imageUrl;
    private  List<CommentDto> comments;
    private int numLikes;

    public static ArticleReadDto fromEntity(ArticleEntity article) {
        ArticleReadDto dto = new ArticleReadDto();
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());

        List<String> urlList = new ArrayList<>();
        for (ArticleImagesEntity articleImages : article.getArticleImages()) {
            urlList.add(articleImages.getImage_url());
        }
        dto.setImageUrl(urlList);

        List<CommentDto> commentList = new ArrayList<>();

        for (CommentEntity comment : article.getComments()) {
            CommentDto commentDto = new CommentDto();
            commentDto.setId(comment.getId());
            commentDto.setUsername(comment.getUser().getUsername());
            commentDto.setContent(comment.getContent());
            commentDto.setCreatedAt(comment.getCreatedAt());
            commentDto.setUpdatedAt(comment.getUpdatedAt());
            commentList.add(commentDto);
        }

        dto.setComments(commentList);

        dto.setNumLikes(article.getLikeArticle().size());

        return dto;
    }
}