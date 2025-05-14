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
    @Column(name = "id")
    private Integer bakeryId;

    private String title;
    private String category;
    private String address;
    private String roadAddress;
    private String link;
    private String mapx;
    private String mapy;
    private int favoriteCount;
    private int commentCount;

    public void increaseFavoriteCount() {
        this.favoriteCount++;
    }

    public void decreaseFavoriteCount() {
        this.favoriteCount--;
    }

    public void increaseCommentCount() {
        this.commentCount++;
    }
}
