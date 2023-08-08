package com.example.mutsasns.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;

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

    private String followStatus; // 디폴트: 요청, 상대한텐 요청대기 수락/거절로 바뀔 수 있음

    private Long follower; // 팔로우를 건 userId

    private Long following; // 팔로우를 한 useId

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private UserEntity user;

}
