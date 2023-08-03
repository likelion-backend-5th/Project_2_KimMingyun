package com.example.mutsasns.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "article")
@Where(clause = "deleted = false")
public class ArticleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private boolean draft;

    private boolean deleted;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "article_id")
    private List<ArticleImagesEntity> articleImagesEntities  = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "article_id")
    private List<LikeArticleEntity> likeArticleEntities  = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "article_id")
    private List<CommentEntity> commentEntities  = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private UserEntity userEntity;

}
