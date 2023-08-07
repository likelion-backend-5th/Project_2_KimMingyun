package com.example.mutsasns.service;

import com.example.mutsasns.dto.article.ArticlePageDto;
import com.example.mutsasns.dto.article.ArticleReadDto;
import com.example.mutsasns.dto.article.ArticleRequestDto;
import com.example.mutsasns.dto.article.ArticleUpdateDto;
import com.example.mutsasns.entity.ArticleEntity;
import com.example.mutsasns.entity.ArticleImagesEntity;
import com.example.mutsasns.entity.UserEntity;
import com.example.mutsasns.repository.ArticleImagesRepository;
import com.example.mutsasns.repository.ArticleRepository;
import com.example.mutsasns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleImagesRepository articleImagesRepository;
    private final UserRepository userRepository;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ArticleRequestDto createArticle(ArticleRequestDto dto){

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        UserEntity userEntity = optionalUser.get();

        List<ArticleImagesEntity> imagesList = new ArrayList<>();

        ArticleEntity article = new ArticleEntity();
        article.setUser(userEntity);
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setArticleImages(imagesList);
        articleRepository.save(article);

        if(!(dto.getImageList() == null)){

            String articleImageDir = String.format("media/article/%s/%d/",username,article.getId());
            try {
                Files.createDirectories(Path.of(articleImageDir));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            int i = 1; // 변수 i를 반복문 밖에 선언하고 초기값을 1로 설정

            for(MultipartFile img : dto.getImageList()) {

                ArticleImagesEntity articleImages = new ArticleImagesEntity();

                // 확장자를 포함한 이미지 이름 만들기
                String originalFilename = img.getOriginalFilename();
                String[] fileNameSplit = originalFilename.split("\\.");
                String extension = fileNameSplit[fileNameSplit.length - 1];
                String articleFilename = username + i + "." + extension;
                i ++; // i를 증가시켜 다음 이미지에 대한 파일 이름 생성

                // 폴더와 파일 경로를 포함한 이름 만들기
                String articleImagePath = articleImageDir + articleFilename;

                // MultipartFile 을 저장하기
                try {
                    img.transferTo(Path.of(articleImagePath));
                } catch (IOException e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                articleImages.setImage_url(String.format("/static/%s", articleFilename));
                articleImages.setArticle(article);
                articleImagesRepository.save(articleImages);
            }

            // 모든 이미지가 저장된 후에 ArticleEntity를 한 번만 저장
            articleRepository.save(article);
        }

        return ArticleRequestDto.fromEntity(article);
    }

    public Page<ArticlePageDto> readArticlePage(Integer pageNumber, Integer pageSize) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        UserEntity userEntity = optionalUser.get();

        Pageable pageable = PageRequest.of(
                pageNumber, pageSize, Sort.by("id").ascending());

        Page<ArticleEntity> ArticlePage
                = articleRepository.findAllByUser(userEntity, pageable);

        Page<ArticlePageDto> ArticleDtoPage
                = ArticlePage.map(ArticlePageDto::fromEntity);

        return ArticleDtoPage;
    }

    public ArticleReadDto readArticle(Long id){

        Optional<ArticleEntity> optionalArticle
                = articleRepository.findById(id);

        if(optionalArticle.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return ArticleReadDto.fromEntity(optionalArticle.get());

    }

    public ArticleUpdateDto updateArticle(ArticleUpdateDto dto, Long articleId){

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        UserEntity userEntity = optionalUser.get();

        Optional<ArticleEntity> optionalArticle = articleRepository.findByIdAndUser(articleId, userEntity);

        if (optionalArticle.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        ArticleEntity article = optionalArticle.get();

        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());


        int lastImageIndex = article.getArticleImages().size();


        List<ArticleImagesEntity> imagesList = new ArrayList<>();
        if (dto.getImageList() != null) {
            String articleImageDir = String.format("article/%s/%d/", username, article.getId());
            for (MultipartFile img : dto.getImageList()) {
                ArticleImagesEntity articleImages = new ArticleImagesEntity();

                // 확장자를 포함한 이미지 이름 만들기
                String originalFilename = img.getOriginalFilename();
                String[] fileNameSplit = originalFilename.split("\\.");
                String extension = fileNameSplit[fileNameSplit.length - 1];
                String articleFilename = username + (++lastImageIndex) + "." + extension;

                // 폴더와 파일 경로를 포함한 이름 만들기
                String articleImagePath = articleImageDir + articleFilename;
                try {
                    img.transferTo(Path.of(articleImagePath));
                } catch (IOException e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                articleImages.setImage_url(String.format("/static/%s", articleFilename));
                articleImages.setArticle(article);
                articleImagesRepository.save(articleImages);
                imagesList.add(articleImages);
            }
        }

        // Add the new images to the article
        article.getArticleImages().addAll(imagesList);
        articleRepository.save(article);

        return ArticleUpdateDto.fromEntity(article);

    }

    public boolean deleteArticle(Long id) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        UserEntity userEntity = optionalUser.get();

        Optional<ArticleEntity> optionalArticle = articleRepository.findByIdAndUser(id, userEntity);

        if (optionalArticle.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        ArticleEntity article = optionalArticle.get();
        article.setDeleted(true); //이렇게 하면 DB에는 값이 존재하지만, 로직 상에는 출력이 되지 않도록 할 수 있음.
                                  // 엔티티에 @Where(clause = "deleted = false")를 붙여놨기 때문

        article.setDeletedAt(DATE_TIME_FORMATTER.format(LocalDateTime.now()));

        articleRepository.save(article);

        articleRepository.findAll().forEach(System.out::println); // 로직상에 출력이 되는지 안되는지 test용
        return true;

    }
}
