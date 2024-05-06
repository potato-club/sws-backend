package com.sws.sws.dto.library;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LocationListResponse {

    private List<LocationResponseDto> locations;
}
