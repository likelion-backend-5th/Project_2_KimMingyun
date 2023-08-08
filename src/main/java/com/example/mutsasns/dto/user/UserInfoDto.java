package com.example.mutsasns.dto.user;

import com.example.mutsasns.dto.article.ArticleRequestDto;
import com.example.mutsasns.entity.ArticleEntity;
import com.example.mutsasns.entity.ArticleImagesEntity;
import com.example.mutsasns.entity.UserEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserInfoDto {
    private String username;
    private String Profile_img;

    public static UserInfoDto fromEntity(UserEntity user) {
        UserInfoDto dto = new UserInfoDto();
        dto.setUsername(user.getUsername());
        dto.setProfile_img(user.getProfile_img());
        return dto;
    }
}
