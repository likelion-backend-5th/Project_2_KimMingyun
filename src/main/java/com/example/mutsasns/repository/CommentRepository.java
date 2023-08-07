package com.example.mutsasns.repository;

import com.example.mutsasns.entity.ArticleEntity;
import com.example.mutsasns.entity.CommentEntity;
import com.example.mutsasns.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Optional<CommentEntity> findByArticleIdAndIdAndUser(Long articleId, Long commentId, UserEntity user);
}
