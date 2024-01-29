package com.sws.sws.controller;

import com.sws.sws.dto.CreatePostRequestDto;
import com.sws.sws.dto.CreatePostResponseDto;
import com.sws.sws.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
  private final PostService postService;

}
