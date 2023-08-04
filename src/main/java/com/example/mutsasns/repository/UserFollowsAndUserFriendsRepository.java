package com.example.mutsasns.repository;

import com.example.mutsasns.entity.UserFollowsAndUserFriends;
import com.example.mutsasns.entity.UserFollowsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowsAndUserFriendsRepository extends JpaRepository<UserFollowsAndUserFriends, Long> {

}
