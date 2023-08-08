package com.example.mutsasns.service;

import com.example.mutsasns.dto.user.UserInfoDto;
import com.example.mutsasns.entity.*;
import com.example.mutsasns.repository.UserFollowsRepository;
import com.example.mutsasns.repository.UserFriendsRepository;
import com.example.mutsasns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserRepository userRepository;
    private final UserFollowsRepository userFollowsRepository;
    private final UserFriendsRepository userFriendsRepository;
    public UserInfoDto readUser(Long userId){
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        UserEntity user = userEntity.get();

        return UserInfoDto.fromEntity(user);
    }

    public void follows(Long userId){
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        // 로그인한 사용자
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        UserEntity userEntity = optionalUser.get();

        // 팔로우할 유저
        Optional<UserEntity> optionalFollowUser = userRepository.findById(userId);

        if(optionalFollowUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        UserEntity userFollowEntity = optionalFollowUser.get();

        UserFollowsEntity userFollows1 = new UserFollowsEntity(); //팔로우 당하는 사람
        UserFriendsEntity userFriends1 = new UserFriendsEntity();

        UserFollowsEntity userFollows2 = new UserFollowsEntity(); //팔로우 하는 사람
        UserFriendsEntity userFriends2 = new UserFriendsEntity();

        userFollows1.setUser(userFollowEntity); // 팔로우 할 유저 설정
        userFollows1.setFollowing(userFollowEntity.getId());
        userFollows1.setFollowStatus("요청");

        userFollows2.setUser(userEntity); // 팔로우를 당한 유저 설정
        userFollows2.setFollower(userFollowEntity.getId()); // 그 유저의 팔로워 ID
        userFollows2.setFollowStatus("요청대기");

        userFriends1.setUser(userEntity); // 팔로우 건 유저의 친구관계도 설정
        userFriends1.setToUser(userFollowEntity.getId()); // 팔로우 건 유저가 팔로잉 한 대상 id

        userFriends2.setUser(userFollowEntity); // 팔로우 당한 유저의 친구 관계
        userFriends2.setFromUser(userEntity.getId()); // 팔로우 건 유저 from 유저에 들어감

        userFollowsRepository.save(userFollows1);
        userFriendsRepository.save(userFriends1);

        userFollowsRepository.save(userFollows2);
        userFriendsRepository.save(userFriends2);


    }
}