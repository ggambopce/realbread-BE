package com.jino.realbread.domain.view;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Immutable
@Entity(name = "bakery_list_view")
@Table(name = "bakery_list_view")
public class BakeryListViewEntity {

    @Id
    @Column(name = "bakery_list_view_id") // ← 새로 만든 row 번호
    private Integer id;

    // Bakery 정보
    @Column(name = "bakery_id")
    private Integer bakeryNumber;

    @Column(name = "bakery_title")
    private String title;

    @Column(name = "bakery_road_address")
    private String roadAddress;

    @Column(name = "bakery_link")
    private String link;

    @Column(name = "bakery_mapx")
    private String mapx;

    @Column(name = "bakery_mapy")
    private String mapy;

    @Column(name = "bakery_favorite_count")
    private int favoriteCount;

    @Column(name = "bakery_comment_count")
    private int commentCount;

    // Menu 정보
    @Column(name = "menu_id")
    private Integer menuNumber;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private String price;

    @Column(name = "menu_description", length = 2000)
    private String description;

    @Column(name = "menu_image_url")
    private String imageUrl;

}
