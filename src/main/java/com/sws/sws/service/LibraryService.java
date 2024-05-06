package com.sws.sws.service;

import com.sws.sws.dto.library.LocationListResponse;
import com.sws.sws.dto.library.LocationResponseDto;
import com.sws.sws.dto.library.OpenApiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LibraryService {

    private final OpenApiManger openApiManger;

    private LocationListResponse makeResponseList(List<OpenApiDto> dtoList) {
        List<LocationResponseDto> result = new ArrayList<>();
        dtoList.forEach(l -> result.add(l.toResponse()));
        return new LocationListResponse(result);
    }


}
