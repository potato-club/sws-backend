package com.sws.sws.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CafeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="location_id")
    private LocationEntity locationEntity;

    @Column(nullable = false, unique = false)
    private String cafeName;

    @Column(nullable = false, unique = false)
    private int rating;

}