package com.sws.sws.controller;

import com.sws.sws.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/library")
    public ResponseEntity<?> fetch() {
        return success(libraryService.fetch().getBody());
    }

}
