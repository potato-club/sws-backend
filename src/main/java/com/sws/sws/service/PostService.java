package com.sws.sws.service;

import com.sws.sws.dto.post.RequestPostDto;
import com.sws.sws.dto.post.ResponsePostDto;
import com.sws.sws.dto.post.ResponsePostListDto;
import com.sws.sws.entity.PostEntity;
import com.sws.sws.repository.PostRepository;
import com.sws.sws.utils.ResponseValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public ResponsePostListDto findAllPost() {

        List<PostEntity> all = postRepository.findTop5ByOrderByCreatedAtDesc();

        if(all.isEmpty()){
            return ResponsePostListDto.builder()
                    .size(0)
                    .posts(Collections.emptyList())
                    .build();
        } else {

            List<ResponsePostDto> collect = all.stream()
                    .filter(Objects::isNull)
                    .map(ResponseValue::getAllBuild)
                    .collect(Collectors.toList());

            return ResponsePostListDto.builder()
                    .size(all.size())
                    .posts(collect)
                    .build();
        }
    }

    public Long createPost(RequestPostDto requestDto) {
//        UserEntity user = userRepository.findById(requestDto.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")); // 이건 나중에 memberService에서 저리해야할 부분입니다.

        PostEntity post = PostEntity.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();
        PostEntity savedPost = postRepository.save(post); // 게시물 저장하고

        return savedPost.getId(); // 게시물 id값 반환
    }


}
