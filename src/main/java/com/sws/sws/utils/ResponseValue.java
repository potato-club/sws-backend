package com.sws.sws.utils;

import com.sws.sws.dto.comment.CommentResponseDto;
import com.sws.sws.dto.post.ResponsePostDto;
import com.sws.sws.entity.CommentEntity;
import com.sws.sws.entity.PostEntity;
import com.sws.sws.error.ErrorCode;
import com.sws.sws.error.exception.PostNotFoundException;

public class ResponseValue {

    public static ResponsePostDto getAllBuild(PostEntity postEntity) {
        if (postEntity != null) {
            return ResponsePostDto.builder()
                    .id(postEntity.getId())
                    .title(postEntity.getTitle())
                    .content(postEntity.getContent())
                    .userName(postEntity.getUserEntity().getUserName())
                    .createdAt(postEntity.getCreatedAt())
                    .updatedAt(postEntity.getUpdatedAt())
                    .build();
        } else {
            throw new PostNotFoundException("존재하는 게시물이 없습니다!", ErrorCode.POST_NOT_FOUND_EXCEPTION);
        }
    }

    public static CommentResponseDto getAllBuild(CommentEntity commentEntity) {
        if(commentEntity.getPostEntity().getId() != null) {
            return CommentResponseDto.builder()
                    .id(commentEntity.getId())
                    .content(commentEntity.getContent())
                    .postId(commentEntity.getPostEntity().getId())
                    .createdAt(commentEntity.getCreatedAt())
                    .userName(commentEntity.getUserEntity().getUserName())
                    .build();
        } else {
            throw new PostNotFoundException("존재하는 게시물이 없습니다!", ErrorCode.POST_NOT_FOUND_EXCEPTION);
        }
    }
}
