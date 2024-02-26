package com.sws.sws.service;

import com.sws.sws.dto.comment.CommentRequestDto;
import com.sws.sws.dto.comment.CommentResponseDto;
import com.sws.sws.entity.CommentEntity;
import com.sws.sws.entity.PostEntity;
import com.sws.sws.entity.UserEntity;
import com.sws.sws.repository.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    public CommentResponseDto createComment(Long postId, CommentRequestDto dto, HttpServletRequest request) {

        Optional<UserEntity> user = userService.findByUserToken(request);
        PostEntity post = postService.getPostId(postId);

        CommentEntity comment = CommentEntity.builder()
                .content(dto.getComment())
                .postEntity(post)
                .userEntity(user.get())
                .build();
        CommentEntity save = commentRepository.save(comment);
        return new CommentResponseDto(save);
    }
}