package com.sws.sws.entity;


import com.sws.sws.dto.user.MyPageDto;
import com.sws.sws.enums.Level;
import com.sws.sws.enums.TagName;
import com.sws.sws.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private List<FriendsEntity> friends = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity")
    private List<PostEntity> posts;

    @OneToMany(mappedBy = "userEntity")
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "userEntity")
    private List<LikeEntity> likes;
//
//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
//    private List<FileEntity> files = new ArrayList<>();

    //    @OneToMany(mappedBy = "userEntity")
    //    private List<LocationEntity> locations;

    @ManyToMany
    @JoinTable(
            name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagEntity> tags;

    @Enumerated(EnumType.STRING)
    @Column(name = "userRole", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'USER'")
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'LEV1'")
    private Level level;


    public void update(MyPageDto myPageDto) { // 사실 이것도 있으면 안되는거같은데
        this.email = myPageDto.getEmail();
        this.userName = myPageDto.getUserName();
        this.nickname = myPageDto.getNickname();
        this.level = myPageDto.getLevel();
        this.userRole = myPageDto.getUserRole();
    }

    public void checkDeleted() {
        this.isDel = true;
    }


    //    @Column(name = "is_del", columnDefinition = "TINYINT(1)", nullable = false)
    @Column(name = "is_del", columnDefinition = "TINYINT(1) DEFAULT '0'")
    private Boolean isDel;

    public void checkExist() {
        this.isDel = false;
    }

}