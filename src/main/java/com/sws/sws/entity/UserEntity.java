package com.sws.sws.entity;

import com.sws.sws.enums.Level;
import com.sws.sws.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @OneToMany(mappedBy = "userEntity")
    private List<FriendsEntity> friends;

    @OneToMany(mappedBy = "user")
    private List<PostEntity> posts;

    @OneToMany(mappedBy = "userEntity")
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "userEntity")
    private List<LikeEntity> likes;

    @OneToMany(mappedBy = "userEntity")
    private List<LocationEntity> locations;

    @OneToMany(mappedBy = "userEntity")
    private List<UserTagEntity> userTags;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @Column(nullable = false, unique = false)
    private Boolean isDel;




}
