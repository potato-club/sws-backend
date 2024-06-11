package com.sws.sws.controller;

import com.sws.sws.dto.library.LocationListResponse;
import com.sws.sws.service.LibraryService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://localhost:3000", "https://www.sws-back.shop", "http://localhost:3000"})
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/openapi")
    public Mono<LocationListResponse> getLibraryQuery() {
        return libraryService.getLibraryQuery();
    }

//    @GetMapping("get/")

}
