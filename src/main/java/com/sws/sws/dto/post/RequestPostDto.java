package com.sws.sws.dto.post;

import com.sws.sws.enums.CategoryName;
import com.sws.sws.enums.TagName;
import lombok.Getter;

import java.util.List;

@Getter
public class RequestPostDto {
    private String title;
    private String content;
    private CategoryName category;
    private List<TagName> tag;
}
