package com.example.mutsasns.dto.article;

import com.example.mutsasns.entity.ArticleEntity;
import com.example.mutsasns.entity.ArticleImagesEntity;
import lombok.Data;

@Data
public class ArticlePageDto {
    private String id;
    private String title;
    private String mainImageUrl;

    public static ArticlePageDto fromEntity(ArticleEntity article){
        ArticlePageDto dto = new ArticlePageDto();
        dto.setTitle(article.getTitle());
        dto.setId(article.getUser().getUsername());
        if(article.getArticleImages().isEmpty())
            dto.setMainImageUrl("basic_image");
        else{
            ArticleImagesEntity firstImg  = article.getArticleImages().get(0);
            dto.setMainImageUrl(firstImg.getImage_url());
        }
        return dto;
    }
}