package com.example.mutsasns.security;


import com.example.mutsasns.entity.UserEntity;
import com.example.mutsasns.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;


/*
기본적으로 Spring Security가 사용자 정보를 확인하기 위해 사용하는 인터페이스 : UserDetailsService
UserDetailsService 를 상속받은 인터페이스 -> UserDetailsManager

둘중 무엇을 사용하는지는 동작에 영향 x
핵심은 loadUserByUsername이(메소드) 정상 동작을 해야함. -> 사용자 인증 과정에서 활용하는 메소드이다.

 */

@Slf4j
@Service
public class JpaUserDetailsManager implements UserDetailsManager {
    private final UserRepository userRepository;
    public JpaUserDetailsManager(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        createUser(CustomUserDetails.builder()
                .username("user")
                .password(passwordEncoder.encode("asdf"))
                .email("user@gmail.com")
                .build());
        createUser(CustomUserDetails.builder()
                .username("user1")
                .password(passwordEncoder.encode("1234"))
                .email("user@gmail.com")
                .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(username);
        return CustomUserDetails.fromEntity(optionalUser.get());
    }
    @Override
    public void createUser(UserDetails user) {
        log.info("try create user: {}", user.getUsername());
        // 사용자가 (이미) 있으면 생성할 수 없다.
        if (this.userExists(user.getUsername())) {
            return;
            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        try {
            // Set the created_at and updated_at fields before saving the user entity
            CustomUserDetails userDetails = (CustomUserDetails) user;
            LocalDateTime now = LocalDateTime.now();
            userDetails.setCreatedAt(now);
            userDetails.setUpdatedAt(now);

            userRepository.save(userDetails.newEntity());
        } catch (ClassCastException e) {
            log.error("failed to cast to {}", CustomUserDetails.class);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    // 계정이름을 가진 사용자가 존재하는지 확인하는 메소드 (선택)
    public boolean userExists(String username) {
        log.info("check if user: {} exists", username);
        return this.userRepository.existsByUsername(username);
    }
    // 얘네는 나중에 해보세요...
    @Override
    public void updateUser(UserDetails user) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
        // 미구현 상태임을 알리는 예외 발생
    }
    @Override
    public void deleteUser(String username) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
        // 미구현 상태임을 알리는 예외 발생
    }
    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
        // 미구현 상태임을 알리는 예외 발생
    }

}