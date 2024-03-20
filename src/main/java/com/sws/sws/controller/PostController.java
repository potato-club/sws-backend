package com.sws.sws.controller;

import com.sws.sws.dto.post.*;
import com.sws.sws.entity.TagEntity;
import com.sws.sws.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Tag(name = "Post Controller", description = "Post API")
public class PostController {

    private final PostService postService;
    // 게시물 조회시 어떻게 조회할껀지 물어보고 로직 추가하기 02/27 이후 추가 사항
    @GetMapping
    @Operation(summary = "모든 게시물 반환")
    public ResponsePostListDto findAll() {
        ResponsePostListDto all = postService.findAllPost();
        return all;
    }

    @Operation(summary = "한개의 게시물 조회")
    @GetMapping("/find/{id}") // 게시물하나
    public ResponsePostDto findOnePost(@PathVariable("id") Long id) {
        ResponsePostDto one = postService.findOnePost(id);
        return one;
    }

    @PostMapping()
    @Operation(summary = "게시물 생성(유저권한 필요)")
    public ResponseEntity<String> createPost(@RequestBody RequestPostDto dto, HttpServletRequest request) {
        postService.createPost(dto, request);
        return ResponseEntity.ok().body("게시물이 생성되었습니다.");
    }

    @Operation(summary = "카테고리별 게시물 조회")
    @GetMapping("/{category}") // 카테고리별 게시물 조회 //
    public PaginationDto findPostByCategory(
            @PathVariable Long category,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "8") // 8개씩
            int size) {
        PaginationDto allPostByParentCategory = postService.findPostByCategoryId(category, PageRequest.of(page, size));
        return allPostByParentCategory;
    }

    @Operation(summary = "Hot한 게시물 조회(좋아요순)")
    @GetMapping("/like")
    public List<ResponsePostDto> getPostLowerThanId(@RequestParam Long lastPostId, @RequestParam int size) {
        return postService.findAllPostByLogic(lastPostId, size);
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

    @GetMapping("/tags")
    @Operation(summary = "모든 태그 조회")
    public List<TagInfoDto> getAllTags() {
        return postService.getAllTagsId();
    }


}
