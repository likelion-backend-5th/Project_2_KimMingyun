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
    private List<MultipartFile> updateImageList;
    private List<String> imageUrl;

}
