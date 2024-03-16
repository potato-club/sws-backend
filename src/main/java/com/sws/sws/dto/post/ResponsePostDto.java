package com.sws.sws.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sws.sws.enums.CategoryName;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostDto {

    private Long id;
    private String title;
    private String content;
    private String userName;
    private CategoryName categoryName;
    private int views;
    private int likeNums;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

}
