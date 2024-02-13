package com.sws.sws.service;

import com.sws.sws.dto.comment.CommentRequestDto;
import com.sws.sws.dto.comment.CommentResponseDto;
import com.sws.sws.entity.CommentEntity;
import com.sws.sws.entity.PostEntity;
import com.sws.sws.entity.UserEntity;
import com.sws.sws.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    public CommentResponseDto createComment(Long postId, CommentRequestDto dto) {
        PostEntity post = postService.getPostId(postId);
        CommentEntity comment = CommentEntity.builder()
                .content(dto.getComment())
                .postEntity(post)
//                .userEntity() // 나중에 유저권한 추가시 추가
                .build();
        CommentEntity save = commentRepository.save(comment);
        return new CommentResponseDto(save);
    }
}
