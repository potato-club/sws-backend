package com.sws.sws.dto.post;

import com.sws.sws.enums.CategoryName;
import lombok.Getter;

@Getter
public class RequestPostDto {
    private String title;
    private String content;
    private CategoryName category;
}
