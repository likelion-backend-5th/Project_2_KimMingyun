package com.example.mutsasns.repository;

import com.example.mutsasns.entity.UserFollowsEntity;
import com.example.mutsasns.entity.UserFriendsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFriendsRepository extends JpaRepository<UserFriendsEntity, Long> {

}
