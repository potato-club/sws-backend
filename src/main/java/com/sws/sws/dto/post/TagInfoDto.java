package com.sws.sws.dto.post;

import com.sws.sws.enums.TagName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagInfoDto {
    private Long id;
    private TagName tag;
}
