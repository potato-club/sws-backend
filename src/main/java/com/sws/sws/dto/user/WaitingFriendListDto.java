package com.sws.sws.dto.user;

import com.sws.sws.enums.FriendStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaitingFriendListDto {
    private Long friendshipId;
    private String friendEmail;
    private String friendName;
    private FriendStatus status;
    private String imageUrl;
}
