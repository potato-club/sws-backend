package com.sws.sws.entity;

import com.sws.sws.enums.Level;
import com.sws.sws.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;


import java.util.List;
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long userId;

    @OneToMany(mappedBy = "userEntity")
    private List<FriendsEntity> friends;

    @OneToMany(mappedBy = "userEntity")
    private List<PostEntity> posts;

    @OneToMany(mappedBy = "userEntity")
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "userEntity")
    private List<LikeEntity> likes;

    @OneToMany(mappedBy = "userEntity")
    private List<LocationEntity> locations;

    @OneToMany(mappedBy = "userEntity")
    private List<UserTagEntity> userTags;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'LEV1'")
    private Level level;


    @Column(name = "is_del", columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean isDel;


    @Column(name = "refresh_token", columnDefinition = "VARCHAR(255) DEFAULT 'dummy'", nullable = false)
    private String refreshToken;

    public void setRefreshToken(String RT) {
        this.refreshToken = RT;
    }


}
