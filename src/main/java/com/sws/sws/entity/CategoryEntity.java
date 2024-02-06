package com.sws.sws.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @ManyToOne
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CategoryEntity category;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<PostEntity> posts = new ArrayList<>();


}
