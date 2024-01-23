package com.sws.sws.entity;

import com.sws.sws.enums.TagName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name="tag_id")
    private TagEntity tagEntity;

}
