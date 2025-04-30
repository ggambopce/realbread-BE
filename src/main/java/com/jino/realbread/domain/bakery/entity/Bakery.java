package com.jino.realbread.domain.bakery.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bakery")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bakery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private String address;
    private String roadAddress;
    private String link;
    private String mapx;
    private String mapy;
    private int favoriteCount;
    private int commentCount;

}
