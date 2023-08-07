package com.example.mutsasns.service;

import com.example.mutsasns.entity.UserEntity;
import com.example.mutsasns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public void updateProfile(MultipartFile Image){

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        else {
            UserEntity user = optionalUser.get();
            Long userId = user.getId();
            // 2-1. 폴더만 만드는 과정 -> media/profile/{username}/1/
            String profileDir = String.format("media/profile/%s/%d/",username, userId);
            try {
                Files.createDirectories(Path.of(profileDir));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            // 2-2. 확장자를 포함한 이미지 이름 만들기
            String originalFilename = Image.getOriginalFilename();
            String[] fileNameSplit = originalFilename.split("\\.");
            String extension = fileNameSplit[fileNameSplit.length - 1];
            String profileFilename = username + "." + extension;

            // 2-3. 폴더와 파일 경로를 포함한 이름 만들기

            String profilePath = profileDir + profileFilename;
            // profile/1/{username}.{extension}

            // 3. MultipartFile 을 저장하기
            try {
                Image.transferTo(Path.of(profilePath));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            // 4. UserEntity 업데이트 (정적 프로필 이미지를 회수할 수 있는 URL)
            user.setProfile_img(String.format("/static/%s", profileFilename));
            userRepository.save(user);
        }
    }
}