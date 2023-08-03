package com.example.mutsasns.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_follows")
@Where(clause = "deleted = false")
public class UserFollowsEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long follower;

    private Long following;

    @OneToMany
    @JoinColumn(name = "user_follows_id")
    @ToString.Exclude
    private List<UserFollowsAndUserFriends> userFollowsAndUserFriendsList = new ArrayList<>();

    public void addUserFollowsAndUserFriends(UserFollowsAndUserFriends... userFollowsAndUserFriends) {
        Collections.addAll(this.userFollowsAndUserFriendsList, userFollowsAndUserFriends);
    }
    /*

    UserFollowsAndUserFriends userFollowsAndUserFriends1 = givenUserFollowsAndUserFriends(userFollows1, userFriends1);
    UserFollowsAndUserFriends userFollowsAndUserFriends2 = givenUserFollowsAndUserFriends(userFollows2, userFriends2);
    UserFollowsAndUserFriends userFollowsAndUserFriends2 = givenUserFollowsAndUserFriends(userFollows3, userFriends1);
    UserFollowsAndUserFriends userFollowsAndUserFriends2 = givenUserFollowsAndUserFriends(userFollows3, userFriends2);

    userFollows1.addUserFollowsAndUserFriends(userFollowsAndUserFriends1);
    userFollows2.addUserFollowsAndUserFriends(userFollowsAndUserFriends2);
    userFollows3.addUserFollowsAndUserFriends(userFollowsAndUserFriends3, userFollowsAndUserFriends4);

    userFriends1.addUserFollowsAndUserFriends(userFollowsAndUserFriends1,userFollowsAndUserFriends2);
    userFriends2.addUserFollowsAndUserFriends(userFollowsAndUserFriends3, userFollowsAndUserFriends4);

    userFollowsRepository.saveAll(Lists.newArrayList(userFollows1, userFollows2, userFollows3)
    userFriendsRepository.saveAll(Lists.newArrayList(userFriends1, userFriends2)
     */
}
