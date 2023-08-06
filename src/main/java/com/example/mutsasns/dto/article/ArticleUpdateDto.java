package com.example.mutsasns.dto.article;

import com.example.mutsasns.entity.ArticleEntity;
import com.example.mutsasns.entity.ArticleImagesEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@Data
public class ArticleUpdateDto {

    private String title;
    private String content;
    private List<MultipartFile> imageList;
    private List<String> imageUrl;

    public static ArticleUpdateDto fromEntity(ArticleEntity article) {
        ArticleUpdateDto dto = new ArticleUpdateDto();
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        List<String> urlList = new ArrayList<>();
        for (ArticleImagesEntity articleImages : article.getArticleImages()) {
            urlList.add(articleImages.getImage_url());
        }
        dto.setImageUrl(urlList);
        return dto;
    }
}
