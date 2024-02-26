package com.sws.sws.controller;

import com.sws.sws.dto.comment.CommentRequestDto;
import com.sws.sws.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{id}") // 게시물 id값
    public ResponseEntity<String> createComment(@RequestBody CommentRequestDto dto, @PathVariable("id") Long id, HttpServletRequest request) {
        commentService.createComment(id, dto, request);
        return ResponseEntity.ok().body("댓글이 생성되었습니다.");
    }
}
