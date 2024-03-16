package com.sws.sws.controller;

import com.sws.sws.dto.like.LikeDto;
import com.sws.sws.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "게시글 좋아요 (유저 권한 필요)")
    @PostMapping("/like/post/{id}")
    public ResponseEntity<String> likePost(@RequestBody LikeDto likeDto, @PathVariable("id") Long id, HttpServletRequest request) {
        return likeService.postLikeService(id,likeDto,request);
    }

    @Operation(summary = "댓글 좋아요 (유저 권한 필요)")
    @PostMapping("/like/comment/{id}")
    public ResponseEntity<String> likeComment(@RequestBody LikeDto likeDto, @PathVariable("id") Long id, HttpServletRequest request) {
        return likeService.commentLikeService(id,likeDto,request);
    }


}
