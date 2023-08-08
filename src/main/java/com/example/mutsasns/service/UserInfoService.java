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

    public boolean follows(Long userId){
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
        userFollows1.setFollowStatus("팔로잉");

        userFollows2.setUser(userEntity); // 팔로우를 당한 유저 설정
        userFollows2.setFollower(userFollowEntity.getId()); // 그 유저의 팔로워 ID
        userFollows2.setFollowStatus("맞팔대기");

        userFriends1.setUser(userEntity); // 팔로우 건 유저의 친구관계도 설정
        userFriends1.setToUser(userFollowEntity.getId()); // 팔로우 건 유저가 팔로잉 한 대상 id

        userFriends2.setUser(userFollowEntity); // 팔로우 당한 유저의 친구 관계
        userFriends2.setFromUser(userEntity.getId()); // 팔로우 건 유저 from 유저에 들어감

        userFollowsRepository.save(userFollows1);
        userFriendsRepository.save(userFriends1);

        userFollowsRepository.save(userFollows2);
        userFriendsRepository.save(userFriends2);

        return true;

    }
//
//    public boolean unFollows(Long userId){
//        String username = SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getName();
//
//        // 로그인한 사용자
//        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
//
//        if(optionalUser.isEmpty())
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//
//        UserEntity userEntity = optionalUser.get();
//
//        // 언 팔로우할 유저
//        Optional<UserEntity> optionalUnFollowUser = userRepository.findById(userId);
//
//        if(optionalUnFollowUser.isEmpty())
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//
//        UserEntity userUnFollowEntity = optionalUnFollowUser.get();
//
//        Optional<UserFollowsEntity> optionalUserFollows1 = userFollowsRepository.findById(userUnFollowEntity.getId()); //언팔로우 당하는 사람
//        if(optionalUserFollows1.isEmpty())
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        UserFollowsEntity userUnFollows1 = optionalUserFollows1.get();
//
//        Optional<UserFriendsEntity> optionalUserFriends1 = userFriendsRepository.findById(userUnFollowEntity.getId());
//        if(optionalUserFriends1.isEmpty())
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        UserFriendsEntity userUnFriends1 = optionalUserFriends1.get();
//
//        Optional<UserFollowsEntity> optionalUserFollows2 = userFollowsRepository.findById(userEntity.getId()); //언팔로우 하는 사람
//        if(optionalUserFollows2.isEmpty())
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        UserFollowsEntity userFollows2 = optionalUserFollows2.get();
//
//        Optional<UserFriendsEntity> optionalUserFriends2 = userFriendsRepository.findById(userEntity.getId());
//        if(optionalUserFriends2.isEmpty())
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        UserFriendsEntity userFriends2 = optionalUserFriends2.get();
//
//        userUnFollows1.setFollowStatus(null);
//
//        // 팔로우를 받았던 사람은 status가 요청대기 / 맞팔인 상황
//        if(userFollows2.getFollowStatus().equals("요청대기")){
//            userFollows2.setFollowStatus(null);
//        }
//        else if(userFollows2.getFollowStatus().equals("맞팔")){
//            userFollows2.setFollowStatus(null);
//        }
//
//        userUnFriends1.setToUser(null);
//        userUnFriends1.setFromUser(null);
//
//        userFollowsRepository.save(userUnFollows1);
//        userFriendsRepository.save(userUnFriends1);
//
//        userFollowsRepository.save(userFollows2);
//        userFriendsRepository.save(userFriends2);
//
//        return true;
//
//    }

}