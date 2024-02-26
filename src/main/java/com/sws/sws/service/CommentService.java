package com.sws.sws.service;

import com.sws.sws.dto.comment.CommentRequestDto;
import com.sws.sws.dto.comment.CommentResponseDto;
import com.sws.sws.entity.CommentEntity;
import com.sws.sws.entity.PostEntity;
import com.sws.sws.entity.UserEntity;
import com.sws.sws.error.ErrorCode;
import com.sws.sws.error.exception.NotFoundException;
import com.sws.sws.error.exception.UnAuthorizedException;
import com.sws.sws.repository.CommentRepository;
import com.sws.sws.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

//    public CommentResponseDto findAllCommentByPost() {
//        List<CommentResponseDto> collect =
//    }

    public CommentResponseDto createComment(Long postId, CommentRequestDto dto, HttpServletRequest request) {

        Optional<UserEntity> user = userService.findByUserToken(request);
        if (user.get().getUserRole() == null) {
            throw new UnAuthorizedException("로그인후 이용해주세요.", ErrorCode.NOT_ALLOW_WRITE_EXCEPTION);
        } else {
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

    public void deleteComment(Long id, HttpServletRequest request) {
        Optional<UserEntity> user = userService.findByUserToken(request);

        if (user.get().getUserRole() == null) {
            throw new UnAuthorizedException("로그인후 이용해주세요.", ErrorCode.NOT_ALLOW_WRITE_EXCEPTION);
        } else {
            CommentEntity commentId = commentRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION, "댓글이 존재하지않습니다."));

            commentRepository.deleteById(commentId.getId());
        }

    }
}
