package com.sws.sws.service;

import com.sws.sws.dto.like.LikeDto;
import com.sws.sws.entity.CommentEntity;
import com.sws.sws.entity.LikeEntity;
import com.sws.sws.entity.PostEntity;
import com.sws.sws.entity.UserEntity;
import com.sws.sws.error.ErrorCode;
import com.sws.sws.error.exception.NotFoundException;
import com.sws.sws.repository.CommentRepository;
import com.sws.sws.repository.LikeRepository;
import com.sws.sws.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    public ResponseEntity<String> postLikeService(Long postId, LikeDto likeDto, HttpServletRequest request) {
        Optional<UserEntity> userEntity = userService.findByUserToken(request);
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RUNTIME_EXCEPTION, "잘못된 요청입니다."));

        LikeEntity likeEntity = likeRepository.findByUserEntityAndPostEntity(userEntity.orElse(null), post);

        if (likeDto.isUnLiked()) {
            if (likeEntity != null) {
                likeRepository.delete(likeEntity);
                post.decreaseLikesNums();
                return ResponseEntity.ok().body("좋아요가 취소되었습니다.");
            }
        } else {
            if (likeEntity == null) {
                likeEntity = LikeEntity.builder()
                        .userEntity(userEntity.orElse(null))
                        .postEntity(post)
                        .isDel(false)
                        .unLike(false)
                        .build();
                likeRepository.save(likeEntity);
                post.increaseLikesNums();
                return ResponseEntity.ok().body("좋아요가 눌렸습니다.");
            }
        }

        return ResponseEntity.ok().body("이미 좋아요 상태입니다.");
    }

    public ResponseEntity<String> commentLikeService(Long commentId, LikeDto likeDto, HttpServletRequest request) {
        Optional<UserEntity> userEntity = userService.findByUserToken(request);
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RUNTIME_EXCEPTION, "잘못된 요청입니다."));

        LikeEntity likeEntity = likeRepository.findByUserEntityAndCommentEntity(userEntity.orElse(null), comment);

        if (likeDto.isUnLiked()) {
            if (likeEntity != null) {
                likeRepository.delete(likeEntity);
                comment.decreaseLikesNums();
                return ResponseEntity.ok().body("좋아요가 취소되었습니다.");
            }
        } else {
            if (likeEntity == null) {
                likeEntity = LikeEntity.builder()
                        .userEntity(userEntity.orElse(null))
                        .commentEntity(comment)
                        .isDel(false)
                        .unLike(false)
                        .build();
                likeRepository.save(likeEntity);
                comment.increaseLikesNums();
                return ResponseEntity.ok().body("좋아요가 눌렸습니다.");
            }
        }

        return ResponseEntity.ok().body("이미 좋아요 상태입니다.");
    }

}