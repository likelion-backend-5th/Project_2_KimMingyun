package com.example.mutsasns.service;

import com.example.mutsasns.dto.article.ArticleRequestDto;
import com.example.mutsasns.dto.comment.CommentRequestDto;
import com.example.mutsasns.entity.ArticleEntity;
import com.example.mutsasns.entity.CommentEntity;
import com.example.mutsasns.entity.LikeArticleEntity;
import com.example.mutsasns.entity.UserEntity;
import com.example.mutsasns.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeArticleRepository likeArticleRepository;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public CommentRequestDto createComment(Long articleId, CommentRequestDto dto){

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        UserEntity userEntity = optionalUser.get();

        Optional<ArticleEntity> optionalArticle = articleRepository.findById(articleId);

        if(optionalArticle.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        ArticleEntity articleEntity = optionalArticle.get();

        CommentEntity comment = new CommentEntity();

        comment.setUser(userEntity);
        comment.setArticle(articleEntity);
        comment.setContent(dto.getContent());

        commentRepository.save(comment);

        return CommentRequestDto.fromEntity(comment);
    }

    public void updateComment(CommentRequestDto dto, Long articleId, Long commentId){
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        UserEntity userEntity = optionalUser.get();

        // 사실 commentId만 넣어도 되지만
        Optional<CommentEntity> optionalComment = commentRepository.findByArticleIdAndIdAndUser(articleId, commentId, userEntity);

        CommentEntity comment = optionalComment.get();

        comment.setContent(dto.getContent());

        commentRepository.save(comment);

    }
    public boolean deleteComment(Long articleId, Long commentId) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        UserEntity userEntity = optionalUser.get();

        Optional<CommentEntity> optionalComment = commentRepository.findByArticleIdAndIdAndUser(articleId, commentId, userEntity);

        if (optionalComment.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        CommentEntity comment = optionalComment.get();
        comment.setDeleted(true); //이렇게 하면 DB에는 값이 존재하지만, 로직 상에는 출력이 되지 않도록 할 수 있음.
        // 엔티티에 @Where(clause = "deleted = false")를 붙여놨기 때문

        comment.setDeletedAt(DATE_TIME_FORMATTER.format(LocalDateTime.now()));

        commentRepository.save(comment);

        return true;

    }

    public int likeArticle(Long articleId){

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        UserEntity userEntity = optionalUser.get();

        Optional<ArticleEntity> optionalArticle = articleRepository.findById(articleId);

        if(optionalArticle.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        ArticleEntity articleEntity = optionalArticle.get();

        if(articleEntity.getUser().getUsername().equals(userEntity.getUsername())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        else{
            if(!likeArticleRepository.existsByUser(userEntity)) {
                LikeArticleEntity likeArticleEntity = new LikeArticleEntity();
                likeArticleEntity.setArticle(articleEntity);
                likeArticleEntity.setUser(userEntity);
                likeArticleRepository.save(likeArticleEntity);
                return 1;
            }
            else{
                likeArticleRepository.delete(likeArticleRepository.findByArticleIdAndUser(articleId, userEntity));
                return 2;
            }
        }
    }

}
