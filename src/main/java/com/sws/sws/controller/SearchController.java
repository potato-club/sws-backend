package com.sws.sws.controller;

import com.sws.sws.dto.post.PaginationDto;
import com.sws.sws.enums.TagName;
import com.sws.sws.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://localhost:3000", "https://www.sws-back.shop", "http://localhost:3000"})
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "태그별 게시물 조회(8개씩 전달)")
    @GetMapping("/search")
    public PaginationDto findPostByTags(
            @RequestParam List<TagName> tags,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "8") // 8개씩
            int size) {
        PaginationDto allPostByTags = searchService.searchPostByTags(tags, PageRequest.of(page,size));
        return allPostByTags;
    }

}
