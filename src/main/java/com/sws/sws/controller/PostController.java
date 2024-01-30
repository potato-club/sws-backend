package com.sws.sws.controller;

import com.sws.sws.dto.post.RequestPostDto;
import com.sws.sws.dto.post.ResponsePostListDto;
import com.sws.sws.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponsePostListDto findAll() {
        ResponsePostListDto all = postService.findAllPost();
        return all;
    }

    @PostMapping()
    public ResponseEntity<String> createPost(@RequestBody RequestPostDto dto) {
        Long save = postService.createPost(dto);

        return ResponseEntity.ok().body("{\"postId\": " + save + "}");
    }


}
