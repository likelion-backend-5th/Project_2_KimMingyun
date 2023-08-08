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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // 수정 업뎃 하면 one 쪽 엔티티에서도 반영되게
    @ToString.Exclude
    private UserEntity user;

}
