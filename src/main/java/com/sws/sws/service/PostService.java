package com.sws.sws.service;

import com.sws.sws.dto.post.RequestPostDto;
import com.sws.sws.dto.post.RequestUpdatePostDto;
import com.sws.sws.dto.post.ResponsePostDto;
import com.sws.sws.dto.post.ResponsePostListDto;
import com.sws.sws.entity.CategoryEntity;
import com.sws.sws.entity.PostEntity;
import com.sws.sws.entity.UserEntity;
import com.sws.sws.error.ErrorCode;
import com.sws.sws.error.exception.CategoryNotFoundException;
import com.sws.sws.error.exception.PostNotFoundException;
import com.sws.sws.error.exception.UnAuthorizedException;
import com.sws.sws.repository.CategoryRepository;
import com.sws.sws.repository.PostRepository;
import com.sws.sws.utils.ResponseValue;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public ResponsePostListDto findAllPost() {

        List<PostEntity> all = postRepository.findTop5ByOrderByCreatedAtDesc();

        if (all.isEmpty()) {
            return ResponsePostListDto.builder()
                    .size(0)
                    .posts(Collections.emptyList())
                    .build();
        } else {

            List<ResponsePostDto> collect = all.stream()
                    .filter(Objects::nonNull)
                    .map(ResponseValue::getAllBuild)
                    .collect(Collectors.toList());

            return ResponsePostListDto.builder()
                    .size(all.size())
                    .posts(collect)
                    .build();
        }
    }

    public Long createPost(RequestPostDto requestDto, HttpServletRequest request) {

        Optional<UserEntity> user = userService.findByUserToken(request);
        if(user.get().getUserRole() == null) {
            throw new UnAuthorizedException("로그인후 이용해주세요.", ErrorCode.NOT_ALLOW_WRITE_EXCEPTION);
        } else {
            CategoryEntity category = categoryRepository.findByName(requestDto.getCategory())
                    .orElseThrow(() -> new CategoryNotFoundException("카테고리가 존재하지 않습니다.", ErrorCode.CATEGORY_NOT_FOUND_EXCEPTION));

            PostEntity post = PostEntity.builder()
                    .title(requestDto.getTitle())
                    .userEntity(user.get())
                    .content(requestDto.getContent())
                    .category(category)
                    .build();

            PostEntity savedPost = postRepository.save(post); // 게시물 저장하고

            return savedPost.getId(); // 게시물 id값 반환
        }
    }

    public Long updatePost(RequestUpdatePostDto updatePostDto, Long id, HttpServletRequest request) {

        Optional<UserEntity> user = userService.findByUserToken(request);
        if(user.get().getUserRole() == null) {
            throw new UnAuthorizedException("로그인후 이용해주세요.", ErrorCode.NOT_ALLOW_WRITE_EXCEPTION);
        } else {

            PostEntity originPost = postRepository.findById(id).
                    orElseThrow(() -> new PostNotFoundException("게시물이 존재하지 않습니다.", ErrorCode.POST_NOT_FOUND_EXCEPTION));

            String updatedTitle = updatePostDto.getTitle();
            String updatedContent = updatePostDto.getContent();

            originPost.updatePost(updatedTitle, updatedContent);
            return postRepository.save(originPost).getId();
        }
    }

    public void deletePost(Long id, HttpServletRequest request) {

        Optional<UserEntity> user = userService.findByUserToken(request);
        if(user.get().getUserRole() == null) {
            throw new UnAuthorizedException("로그인후 이용해주세요.", ErrorCode.NOT_ALLOW_WRITE_EXCEPTION);
        } else {

            PostEntity originPost = postRepository.findById(id).
                    orElseThrow(() -> new PostNotFoundException("게시물이 존재하지 않습니다.", ErrorCode.POST_NOT_FOUND_EXCEPTION));

            postRepository.deleteById(originPost.getId());
        }
    }

    public PostEntity getPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("게시물이 존재하지 않습니다.", ErrorCode.POST_NOT_FOUND_EXCEPTION));
    }


}
