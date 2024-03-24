package com.sws.sws.entity;

import com.sws.sws.enums.FriendStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FriendsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity userEntity;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String friendEmail;

    private FriendStatus status;

    private boolean isFrom;

    private Long counterpartId;

    @Column(nullable = false, unique = false)
    private String friendUrl;

    public void acceptFriendshipRequest() {
        status = FriendStatus.ACCEPT;
    }
}
