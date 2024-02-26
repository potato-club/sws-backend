package com.sws.sws.dto.comment;

import com.sws.sws.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private String userName;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponseDto(CommentEntity comment) {
        this.userName = comment.getUserEntity().getUserName();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
