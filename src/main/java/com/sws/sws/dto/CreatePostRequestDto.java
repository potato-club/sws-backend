package com.sws.sws.dto;

import lombok.Getter;

@Getter
public class CreatePostRequestDto {
    private String title;
    private String content;
    private Long userId;

}
