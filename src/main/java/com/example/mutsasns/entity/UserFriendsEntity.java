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
@Table(name = "user_friends")
@Where(clause = "deleted = false")
public class UserFriendsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fromUser;

    private Long toUser;

    @OneToMany
    @JoinColumn(name = "user_friends_id")
    @ToString.Exclude
    private List<UserFollowsAndUserFriends> userFollowsAndUserFriendsList = new ArrayList<>();

    public void addUserFollowsAndUserFriends(UserFollowsAndUserFriends... userFollowsAndUserFriends) {
        Collections.addAll(this.userFollowsAndUserFriendsList, userFollowsAndUserFriends);
    }
}
