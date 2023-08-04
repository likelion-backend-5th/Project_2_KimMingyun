package com.example.mutsasns.repository;

import com.example.mutsasns.entity.CommentEntity;
import com.example.mutsasns.entity.UserFollowsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowsRepository extends JpaRepository<UserFollowsEntity, Long> {

}
