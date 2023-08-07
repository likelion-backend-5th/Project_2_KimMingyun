package com.example.mutsasns.repository;

import com.example.mutsasns.entity.ArticleEntity;
import com.example.mutsasns.entity.ArticleImagesEntity;
import com.example.mutsasns.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArticleImagesRepository extends JpaRepository<ArticleImagesEntity, Long> {

    @Query(value = "SELECT ai FROM ArticleImagesEntity ai JOIN ai.article a WHERE a.id = :articleId AND a.user = :user")
    Optional<ArticleImagesEntity> findByArticleIdAndUser(Long articleId, UserEntity user);
}
