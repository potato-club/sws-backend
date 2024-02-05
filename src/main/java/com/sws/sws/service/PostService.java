package com.sws.sws.service;

import com.sws.sws.dto.CreatePostRequestDto;
import com.sws.sws.dto.CreatePostResponseDto;
import com.sws.sws.entity.PostEntity;
import com.sws.sws.entity.UserEntity;
import com.sws.sws.repository.PostRepository;
import com.sws.sws.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CreatePostResponseDto createPost(CreatePostRequestDto requestDto) {
        UserEntity user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        PostEntity post = PostEntity.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .userEntity(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        PostEntity savedPost = postRepository.save(post);
        return new CreatePostResponseDto(savedPost);
    }


}
