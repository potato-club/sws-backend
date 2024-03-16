package com.sws.sws.controller;

import com.sws.sws.dto.post.RequestPostDto;
import com.sws.sws.dto.post.RequestUpdatePostDto;
import com.sws.sws.dto.post.ResponsePostListDto;
import com.sws.sws.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    // 게시물 조회시 어떻게 조회할껀지 물어보고 로직 추가하기 02/27 이후 추가 사항
    @GetMapping
    @Operation(summary = "모든 게시물 반환")
    public ResponsePostListDto findAll() {
        ResponsePostListDto all = postService.findAllPost();
        return all;
    }

    @PostMapping()
    @Operation(summary = "게시물 생성(유저권한 필요)")
    public ResponseEntity<String> createPost(@RequestBody RequestPostDto dto, HttpServletRequest request) {
        postService.createPost(dto, request);
        return ResponseEntity.ok().body("게시물이 생성되었습니다.");
    }

    @PutMapping("/{id}")
    @Operation(summary = "게시물 수정(유저권한 필요)")
    public ResponseEntity<String> updatePost(@RequestBody RequestUpdatePostDto dto, @PathVariable("id") Long id, HttpServletRequest request) {
        postService.updatePost(dto, id, request);
        return ResponseEntity.ok().body("게시물이 업데이트 되었습니다.");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "게시물 삭제(유저권한 필요)")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id, HttpServletRequest request) {
        postService.deletePost(id, request);
        return ResponseEntity.ok().body("게시물이 삭제되었습니다.");
    }


}
