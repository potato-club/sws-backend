package com.sws.sws.service;

import com.sws.sws.dto.comment.CommentRequestDto;
import com.sws.sws.dto.comment.CommentResponseDto;
import com.sws.sws.entity.CommentEntity;
import com.sws.sws.entity.PostEntity;
import com.sws.sws.entity.UserEntity;
import com.sws.sws.error.ErrorCode;
import com.sws.sws.error.exception.BadRequestException;
import com.sws.sws.error.exception.NotFoundException;
import com.sws.sws.error.exception.UnAuthorizedException;
import com.sws.sws.repository.CommentRepository;
import com.sws.sws.repository.PostRepository;
import com.sws.sws.utils.ResponseValue;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    public List<CommentResponseDto> findAllCommentByPost(Long lastCommentId, int size) {

//        PostEntity postId = postService.getPostId(id);

        PageRequest pageRequest = PageRequest.of(0, size);
        Page<CommentEntity> entityPage = commentRepository.findByIdLessThanOrderByIdDesc(lastCommentId, pageRequest);
        List<CommentEntity> commentEntityList = entityPage.getContent();

        return commentEntityList.stream()
                .map(ResponseValue::getAllBuild)
                .collect(Collectors.toList());
    }

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

    public Long updateComment(Long commentId, CommentRequestDto dto, HttpServletRequest request) {

        Optional<UserEntity> user = userService.findByUserToken(request);
        if(user.get().getUserRole() == null) {
            throw new UnAuthorizedException("로그인후 이용해주세요.", ErrorCode.NOT_ALLOW_WRITE_EXCEPTION);
        } else {
            CommentEntity comment = commentRepository.findById(commentId).
                    orElseThrow(() -> new BadRequestException("댓글이 존재하지 않습니다.", ErrorCode.NOT_FOUND_EXCEPTION));

            String updateContent = dto.getComment();
            comment.updateComment(updateContent);

            return commentRepository.save(comment).getId();
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
