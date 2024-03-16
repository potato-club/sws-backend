package com.sws.sws.controller;

import com.sws.sws.dto.comment.CommentRequestDto;
import com.sws.sws.dto.comment.CommentResponseDto;
import com.sws.sws.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Tag(name = "Comment Controller", description = "Comment API")
public class CommentController {

    private final CommentService commentService;

    @GetMapping()
    @Operation(summary = "모든 댓글 조회")
    public List<CommentResponseDto> getCommentLowerThanId(@RequestParam Long lastCommentId, @RequestParam int size) {
        return commentService.findAllCommentByPost(lastCommentId, size);
    }

    @PostMapping("/{id}") // 게시물 id값
    @Operation(summary = "댓글 생성(유저권한 필요)")
    public ResponseEntity<String> createComment(@RequestBody CommentRequestDto dto, @PathVariable("id") Long id, HttpServletRequest request) {
        commentService.createComment(id, dto, request);
        return ResponseEntity.ok().body("댓글이 생성되었습니다.");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "댓글 삭제(유저권한 필요)")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long id, HttpServletRequest request) {
        commentService.deleteComment(id, request);
        return ResponseEntity.ok().body("댓글이 삭제되었습니다.");
    }
}
