package com.TP.Simple.Prouduits.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name_category;
    @Column(length = 1000)
    private String description;


}