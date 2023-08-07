package com.example.mutsasns.dto.comment;

import com.example.mutsasns.entity.CommentEntity;
import lombok.Data;


@Data
public class CommentRequestDto {

    private String userName;
    private String content;

    public static CommentRequestDto fromEntity(CommentEntity comment) {
        CommentRequestDto dto = new CommentRequestDto();
        dto.setUserName(comment.getUser().getUsername());
        dto.setContent(comment.getContent());
        return dto;
    }

}
