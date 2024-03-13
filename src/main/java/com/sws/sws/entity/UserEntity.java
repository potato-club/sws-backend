package com.sws.sws.entity;

import com.sws.sws.dto.user.InfoUpdateRequestDto;
import com.sws.sws.enums.Level;
import com.sws.sws.enums.TagName;
import com.sws.sws.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
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
    @Column(name = "id")
    private Long userId;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @OneToMany(mappedBy = "userEntity")
    private List<FriendsEntity> friends;

    @OneToMany(mappedBy = "userEntity")
    private List<PostEntity> posts;

    @OneToMany(mappedBy = "userEntity")
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "userEntity")
    private List<LikeEntity> likes;

    //    @OneToMany(mappedBy = "userEntity")
    //    private List<LocationEntity> locations;

    @ElementCollection(targetClass = TagName.class)
    @Enumerated(EnumType.STRING)
    private List<TagName> userTags;

    @Enumerated(EnumType.STRING)
    @Column(name = "userRole", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'USER'")
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'LEV1'")
    private Level level;



    public void update(InfoUpdateRequestDto userDto) {
        this.userName = userDto.getUserName();
        this.nickname = userDto.getNickname();
    }


    public void setIsDel(boolean deleted) {
        this.isDel = deleted;
    }


    //    @Column(name = "is_del", columnDefinition = "TINYINT(1)", nullable = false)
    @Column(name = "is_del", columnDefinition = "TINYINT(1) DEFAULT '0'")
    private Boolean isDel;


}