package com.example.mutsasns.security;

import com.example.mutsasns.entity.BaseEntity;
import com.example.mutsasns.entity.UserEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

/*
지금까지는 UserDetails 구현체를 사용중
우리가 다루는 사용자 정보에 추가적인 데이터를 포함하고 싶으면 UserDetails 인터페이스를 구현하는 클레스를 직접 구현
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CustomUserDetails extends BaseEntity implements UserDetails  {
    @Getter
    private Long id;
    private String username; // 아이디
    private String password; // 비밀번호
    @Getter
    private String email; // 이메일
    @Getter
    private String phone; // 전화번호
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static CustomUserDetails fromEntity(UserEntity entity) {
        return CustomUserDetails.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .createdAt(entity.getCreatedAt())
                .createdAt(entity.getUpdatedAt())
                .build();
    }

    public UserEntity newEntity() {
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setEmail(email);
        entity.setPhone(phone);
        entity.setCreatedAt(getCreatedAt());
        entity.setUpdatedAt(getUpdatedAt());
        return entity;
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}