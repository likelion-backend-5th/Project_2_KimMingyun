package com.example.mutsasns.entity;

import com.example.mutsasns.listener.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass  // 해당 클래스의 필드를 상속받는 엔티티의 컬럼으로 포함시켜줌.
@EntityListeners(value = AuditingEntityListener.class)
public class BaseEntity implements Auditable {
    @CreatedDate
    @Column(columnDefinition = "datetime default current_timestamp", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(columnDefinition = "datetime default null", nullable = false)
    private LocalDateTime updatedAt;
}