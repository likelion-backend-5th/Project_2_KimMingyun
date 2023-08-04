package com.example.mutsasns.entity;

import com.example.mutsasns.listener.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Auditable {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private String createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private String updatedAt;

    // getters and setters

    @Override
    public LocalDateTime getCreatedAt() {
        return LocalDateTime.parse(createdAt, DATE_TIME_FORMATTER);
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return LocalDateTime.parse(updatedAt, DATE_TIME_FORMATTER);
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt.format(DATE_TIME_FORMATTER);
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt.format(DATE_TIME_FORMATTER);
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now.format(DATE_TIME_FORMATTER);
        this.updatedAt = now.format(DATE_TIME_FORMATTER);
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }
}