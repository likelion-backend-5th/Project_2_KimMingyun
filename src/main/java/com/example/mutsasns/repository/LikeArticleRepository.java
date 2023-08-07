package com.example.mutsasns.repository;

import com.example.mutsasns.entity.ArticleEntity;
import com.example.mutsasns.entity.LikeArticleEntity;
import com.example.mutsasns.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeArticleRepository extends JpaRepository<LikeArticleEntity, Long> {

    boolean existsByUser(UserEntity user);

    LikeArticleEntity findByArticleIdAndUser(Long articleId, UserEntity user);
}
