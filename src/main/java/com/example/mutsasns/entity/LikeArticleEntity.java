package com.example.mutsasns.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "like_article")
@Where(clause = "deleted = false")
public class LikeArticleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean deleted;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // 수정 업뎃 하면 one 쪽 엔티티에서도 반영되게
    @ToString.Exclude
    private ArticleEntity article;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // 수정 업뎃 하면 one 쪽 엔티티에서도 반영되게
    @ToString.Exclude
    private UserEntity user;

}
