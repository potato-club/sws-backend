package com.sws.sws.controller;

import com.sws.sws.dto.comment.CommentRequestDto;
import com.sws.sws.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<String> createComment(@RequestBody CommentRequestDto dto, @PathVariable("postId") Long postId) {
        commentService.createComment(postId, dto);
        return ResponseEntity.ok().body("댓글이 생성되었습니다.");
    }
}
