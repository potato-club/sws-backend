package com.sws.sws.controller;

import com.sws.sws.service.LibraryService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @PostMapping("/library")
    public ResponseEntity<ApiResponse> fetch() {
    }

}
