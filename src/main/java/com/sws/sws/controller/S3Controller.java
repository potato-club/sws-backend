package com.sws.sws.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/s3")
@Tag(name = "AWS S3 Download Controller", description = "S3 다운로드 API")
public class S3Controller {
}
