package com.sws.sws.dto.post;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;

@Data
//@JsonDeserialize(builder = RequestUpdatePostDto.Builder.class)
public class RequestUpdatePostDto {

    private String title;
    private String content;

    private RequestUpdatePostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

//    @JsonPOJOBuilder
//    static class Builder {
//        String title;
//        String content;
//
//        Builder withTitle(String title) {
//            this.title = title;
//            return this;
//        }
//
//        Builder withContent(String content) {
//            this.content = content;
//            return this;
//        }
//
//        public RequestUpdatePostDto build() {
//            return new RequestUpdatePostDto(title,content);
//        }

//    }
}
