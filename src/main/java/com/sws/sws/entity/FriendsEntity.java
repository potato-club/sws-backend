package com.sws.sws.entity;

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

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity userEntity;

    @Column(nullable = false, unique = true)
    private Boolean isFriend;

    @Column(nullable = false, unique = false)
    private String friendUrl;
}
