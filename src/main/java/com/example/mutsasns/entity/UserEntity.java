package com.example.mutsasns.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "user")
@Where(clause = "deleted = false")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false )// , unique = true
    private String username;

    @Column(nullable = false)
    private String password;

    private String profile_img;

    private String email;

    private String phone;

    private boolean deleted;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private List<CommentEntity> comments  = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private List<ArticleEntity> articles  = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private List<LikeArticleEntity> likeArticles  = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private List<UserFollowsEntity> userFollows  = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private List<UserFriendsEntity> userFriends  = new ArrayList<>();

//    @PreRemove
//    private void preRemove() {
//        // Delete the profile image from the file system if it exists
//        if (profile_img != null && !profile_img.isEmpty()) {
//            String filePath = "profile/" + id + "/" + username + "." + extractExtension(profile_img);
//            try {
//                Files.deleteIfExists(Path.of(filePath));
//            } catch (IOException e) {
//                // Handle the exception or log it as needed
//            }
//        }
//    }
//    private String extractExtension(String filename) {
//        int lastDotIndex = filename.lastIndexOf('.');
//        return lastDotIndex >= 0 ? filename.substring(lastDotIndex + 1) : "";
//    }

}
