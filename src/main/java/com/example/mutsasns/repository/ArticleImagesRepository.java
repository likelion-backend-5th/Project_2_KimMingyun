package com.example.mutsasns.repository;

import com.example.mutsasns.entity.ArticleEntity;
import com.example.mutsasns.entity.ArticleImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleImagesRepository extends JpaRepository<ArticleImagesEntity, Long> {

}
